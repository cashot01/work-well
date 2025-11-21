-- =============================================
-- FUNÇÃO 1: CONVERSÃO PARA JSON 
-- =============================================
CREATE OR REPLACE FUNCTION fn_usuario_json_manual(
    p_usuario_id BIGINT
) RETURNS JSONB AS $$
DECLARE
    r RECORD;
    v_ultimo_stress SMALLINT;
    v_media_horas_trabalhadas NUMERIC;
    v_ultima_pontuacao_risco INTEGER;
    v_ultimo_nivel_risco TEXT;
    v_prioridade TEXT;
    v_acoes JSONB;
    v_cursos JSONB;
BEGIN
    -- VALIDAÇÃO: Parâmetro obrigatório
    IF p_usuario_id IS NULL THEN
        RETURN jsonb_build_object('erro', 'ID do usuário não pode ser nulo');
    END IF;

    -- OBTER DADOS PRINCIPAIS DO USUÁRIO
    SELECT u.nome, u.email, u.role, e.nome AS empresa_nome, d.nome AS departamento_nome
    INTO r
    FROM usuarios_workwell u
    LEFT JOIN empresas_workwell e ON u.empresa_id = e.id
    LEFT JOIN departamentos_workwell d ON u.departamento_id = d.id
    WHERE u.id = p_usuario_id;

    IF NOT FOUND THEN
        RETURN jsonb_build_object(
            'erro', 'Usuário não encontrado',
            'id', p_usuario_id
        );
    END IF;

    -- VALIDAÇÃO mínima: nome e email
    IF r.nome IS NULL OR r.email IS NULL THEN
        RETURN jsonb_build_object(
            'erro', 'Dados insuficientes para gerar perfil',
            'id', p_usuario_id
        );
    END IF;

    -- Último nível de stress
    SELECT nivel_stress INTO v_ultimo_stress
    FROM checkins_diarios_workwell
    WHERE usuario_id = p_usuario_id
    ORDER BY data DESC
    LIMIT 1;

    -- Média de horas trabalhadas (0 se não houver)
    SELECT COALESCE(AVG(horas_trabalhadas), 0) INTO v_media_horas_trabalhadas
    FROM checkins_diarios_workwell
    WHERE usuario_id = p_usuario_id;

    -- Último alerta de burnout
    SELECT pontuacao_total, nivel_risco
    INTO v_ultima_pontuacao_risco, v_ultimo_nivel_risco
    FROM alertas_burnout_workwell
    WHERE usuario_id = p_usuario_id
    ORDER BY data DESC
    LIMIT 1;

    IF NOT FOUND THEN
        v_ultima_pontuacao_risco := 0;
        v_ultimo_nivel_risco := 'SEM_DADOS';
    END IF;

    -- Recomendações baseadas no stress
    IF v_ultimo_stress IS NULL OR v_ultimo_stress > 7 THEN
        v_prioridade := 'ALTA';
        v_acoes := jsonb_build_array('Consultar psicólogo', 'Reduzir horas extras', 'Praticar mindfulness');
        v_cursos := jsonb_build_array('Gestão do Stress', 'Produtividade Saudável', 'Equilíbrio Vida-Trabalho');
    ELSIF v_ultimo_stress BETWEEN 4 AND 7 THEN
        v_prioridade := 'MEDIA';
        v_acoes := jsonb_build_array('Pausas regulares', 'Atividade física', 'Socialização');
        v_cursos := jsonb_build_array('Autogestão', 'Comunicação Assertiva', 'Planejamento de Carreira');
    ELSE
        v_prioridade := 'BAIXA';
        v_acoes := jsonb_build_array('Manter rotina', 'Networking', 'Desenvolvimento contínuo');
        v_cursos := jsonb_build_array('Liderança', 'Inovação', 'Habilidades do Futuro');
    END IF;

    -- ✅ CONSTRUÇÃO SEGURA DO JSONB
    RETURN jsonb_build_object(
        'perfil_profissional', jsonb_build_object(
            'id', p_usuario_id,
            'nome', r.nome,
            'email', r.email,
            'cargo', r.role,
            'empresa', COALESCE(r.empresa_nome, 'Não informada'),
            'departamento', COALESCE(r.departamento_nome, 'Não informado')
        ),
        'metricas_bem_estar', jsonb_build_object(
            'ultimo_nivel_stress', v_ultimo_stress,
            'media_horas_trabalhadas', ROUND(v_media_horas_trabalhadas, 2),
            'risco_burnout', jsonb_build_object(
                'pontuacao', COALESCE(v_ultima_pontuacao_risco, 0),
                'nivel', COALESCE(v_ultimo_nivel_risco, 'SEM_DADOS')
            )
        ),
        'recomendacoes_carreira', jsonb_build_object(
            'prioridade', v_prioridade,
            'acoes_imediata', v_acoes,
            'cursos_recomendados', v_cursos
        )
    );

EXCEPTION
    WHEN OTHERS THEN
        RETURN jsonb_build_object(
            'erro', 'Erro interno na geração do JSON',
            'detalhes', SQLERRM
        );
END;
$$ LANGUAGE plpgsql;

-- =============================================
-- FUNÇÃO 2: CÁLCULO DE COMPATIBILIDADE 
-- =============================================
CREATE OR REPLACE FUNCTION fn_calcular_compatibilidade_vaga(
    p_usuario_id BIGINT,
    p_vaga_titulo TEXT,
    p_vaga_competencias TEXT,
    p_vaga_nivel_stress_max INTEGER DEFAULT 6
) RETURNS JSONB AS $$
DECLARE
    v_nome TEXT;
    v_email TEXT;
    v_ultimo_stress SMALLINT;
    v_media_horas_trabalhadas NUMERIC;
    v_ultimo_risco TEXT;
    v_competencias_usuario TEXT;
    v_role TEXT;

    v_total_competencias INTEGER;
    v_competencias_match INTEGER := 0;
    v_pontuacao_compatibilidade NUMERIC := 0;
    v_pontuacao_stress NUMERIC := 0;
    v_pontuacao_horas NUMERIC := 0;
    v_compatibilidade_percent INTEGER;
    v_recomendacao TEXT;
    v_status_adequacao TEXT;
    v_sugestao TEXT;
BEGIN
    -- VALIDAÇÃO: Parâmetros obrigatórios
    IF p_usuario_id IS NULL 
        OR p_vaga_titulo IS NULL 
        OR p_vaga_competencias IS NULL 
        OR TRIM(p_vaga_competencias) = '' 
    THEN
        RETURN jsonb_build_object(
            'erro', 'Parâmetros obrigatórios não fornecidos',
            'detalhes', 'usuario_id, vaga_titulo e vaga_competencias são obrigatórios'
        );
    END IF;

    -- Obter dados do usuário
    SELECT nome, email, role INTO v_nome, v_email, v_role
    FROM usuarios_workwell
    WHERE id = p_usuario_id;

    IF NOT FOUND THEN
        RETURN jsonb_build_object('erro', 'Usuário não encontrado', 'id', p_usuario_id);
    END IF;

    -- Validar email (regex)
    IF v_email !~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$' THEN
        RETURN jsonb_build_object(
            'erro', 'Email corporativo inválido',
            'email', v_email,
            'sugestao', 'Formato deve ser usuario@empresa.com'
        );
    END IF;

    -- Último stress
    SELECT nivel_stress INTO v_ultimo_stress
    FROM checkins_diarios_workwell
    WHERE usuario_id = p_usuario_id
    ORDER BY data DESC
    LIMIT 1;
    IF NOT FOUND THEN v_ultimo_stress := 5; END IF;

    -- Média de horas
    SELECT COALESCE(AVG(horas_trabalhadas), 8) INTO v_media_horas_trabalhadas
    FROM checkins_diarios_workwell
    WHERE usuario_id = p_usuario_id;

    -- Último risco
    SELECT nivel_risco INTO v_ultimo_risco
    FROM alertas_burnout_workwell
    WHERE usuario_id = p_usuario_id
    ORDER BY data DESC
    LIMIT 1;
    IF NOT FOUND THEN v_ultimo_risco := 'BAIXO'; END IF;

    -- Definir competências do usuário
    IF v_ultimo_stress <= 4 THEN
        v_competencias_usuario := 'resiliencia,adaptabilidade,foco';
    ELSIF v_ultimo_stress BETWEEN 5 AND 7 THEN
        v_competencias_usuario := 'trabalho_equipe,comunicacao,gestao_tempo';
    ELSE
        v_competencias_usuario := 'resolucao_problemas,criatividade_sob_pressao';
    END IF;

    IF v_role = 'ADMIN' THEN
        v_competencias_usuario := v_competencias_usuario || ',lideranca,gestao,estrategia';
    ELSE
        v_competencias_usuario := v_competencias_usuario || ',colaboracao,aprendizado_continuo';
    END IF;

    -- Contar match de competências
    v_total_competencias := array_length(string_to_array(p_vaga_competencias, ','), 1);
    SELECT COUNT(*)
    INTO v_competencias_match
    FROM unnest(string_to_array(p_vaga_competencias, ',')) AS req
    WHERE v_competencias_usuario ILIKE '%' || TRIM(req) || '%';

    -- Pontuações
    v_pontuacao_compatibilidade := COALESCE((v_competencias_match::NUMERIC / NULLIF(v_total_competencias, 0)) * 40, 0);

    -- Stress (0–30)
    IF v_ultimo_stress <= p_vaga_nivel_stress_max THEN
        v_pontuacao_stress := 30;
    ELSIF v_ultimo_stress <= p_vaga_nivel_stress_max + 2 THEN
        v_pontuacao_stress := 20;
    ELSIF v_ultimo_stress <= p_vaga_nivel_stress_max + 4 THEN
        v_pontuacao_stress := 10;
    ELSE
        v_pontuacao_stress := 0;
    END IF;

    -- Horas (0–30)
    v_pontuacao_horas := CASE
        WHEN v_media_horas_trabalhadas BETWEEN 6 AND 9 THEN 30
        WHEN v_media_horas_trabalhadas BETWEEN 4 AND 10 THEN 20
        WHEN v_media_horas_trabalhadas > 10 THEN 10
        ELSE 5
    END;

    v_pontuacao_compatibilidade := v_pontuacao_compatibilidade + v_pontuacao_stress + v_pontuacao_horas;
    v_compatibilidade_percent := LEAST(100, GREATEST(0, ROUND(v_pontuacao_compatibilidade)::INTEGER));

    -- Recomendação
    v_recomendacao := CASE
        WHEN v_compatibilidade_percent >= 80 THEN 'ALTA_COMPATIBILIDADE'
        WHEN v_compatibilidade_percent >= 60 THEN 'MEDIA_COMPATIBILIDADE'
        WHEN v_compatibilidade_percent >= 40 THEN 'BAIXA_COMPATIBILIDADE'
        ELSE 'INCOMPATIVEL'
    END;

    v_status_adequacao := CASE
        WHEN v_compatibilidade_percent >= 70 AND v_ultimo_stress <= p_vaga_nivel_stress_max THEN 'RECOMENDADO'
        WHEN v_compatibilidade_percent >= 50 THEN 'ANALISAR_CONDICOES'
        ELSE 'NAO_RECOMENDADO'
    END;

    v_sugestao := CASE
        WHEN v_competencias_match < v_total_competencias THEN 'Capacitacao em competencias tecnicas'
        WHEN v_ultimo_stress > p_vaga_nivel_stress_max THEN 'Programa de gestao do stress'
        ELSE 'Mentoria de carreira'
    END;

    -- ✅ Retorno seguro com JSONB
    RETURN jsonb_build_object(
        'analise_compatibilidade', jsonb_build_object(
            'usuario', jsonb_build_object(
                'id', p_usuario_id,
                'nome', v_nome
            ),
            'vaga', jsonb_build_object(
                'titulo', p_vaga_titulo,
                'competencias_requeridas', p_vaga_competencias
            ),
            'metricas_avaliadas', jsonb_build_object(
                'compatibilidade_competencias', 
                    ROUND(COALESCE(v_competencias_match::NUMERIC / NULLIF(v_total_competencias, 0), 0) * 100, 2),
                'nivel_stress_atual', v_ultimo_stress,
                'media_horas_trabalhadas', ROUND(v_media_horas_trabalhadas, 2),
                'risco_burnout', v_ultimo_risco
            ),
            'resultado_final', jsonb_build_object(
                'pontuacao_total', ROUND(v_pontuacao_compatibilidade, 1),
                'percentual_compatibilidade', v_compatibilidade_percent,
                'recomendacao', v_recomendacao,
                'status_adequacao', v_status_adequacao
            ),
            'sugestoes_desenvolvimento', jsonb_build_array(v_sugestao)
        )
    );

EXCEPTION
    WHEN OTHERS THEN
        RETURN jsonb_build_object(
            'erro', 'Erro interno no cálculo de compatibilidade',
            'detalhes', SQLERRM
        );
END;
$$ LANGUAGE plpgsql;
