-- Inserir 10 empresas
INSERT INTO empresas_workwell (nome, cnpj, setor, data_cadastro) VALUES
             ('TechSolutions Ltda', '12.345.678/0001-90', 'Tecnologia', '2024-01-15'),
             ('InovaDigital SA', '98.765.432/0001-10', 'Consultoria TI', '2024-01-16'),
             ('HealthCare Corp', '11.223.344/0001-55', 'Saúde', '2024-01-17'),
             ('EduTech Brasil', '55.667.788/0001-22', 'Educação', '2024-01-18'),
             ('FinTech Solutions', '33.444.555/0001-77', 'Financeiro', '2024-01-19'),
             ('GreenEnergy Ltda', '66.777.888/0001-33', 'Energia', '2024-01-20'),
             ('RetailConnect SA', '99.000.111/0001-44', 'Varejo', '2024-01-21'),
             ('LogiFast Brasil', '22.333.444/0001-88', 'Logística', '2024-01-22'),
             ('MediaPlus Group', '77.888.999/0001-11', 'Mídia', '2024-01-23'),
             ('AutoTech Motors', '44.555.666/0001-99', 'Automotivo', '2024-01-24');

-- Inserir 10 departamentos
INSERT INTO departamentos_workwell (nome, empresa_id, data_cadastro) VALUES
             ('Desenvolvimento', 1, '2024-01-25'),
             ('RH', 1, '2024-01-25'),
             ('Consultoria', 2, '2024-01-26'),
             ('Vendas', 2, '2024-01-26'),
             ('Atendimento', 3, '2024-01-27'),
             ('Administrativo', 3, '2024-01-27'),
             ('Ensino', 4, '2024-01-28'),
             ('Tecnologia', 4, '2024-01-28'),
             ('Financeiro', 5, '2024-01-29'),
             ('TI', 5, '2024-01-29');

-- Inserir 10 usuários
INSERT INTO usuarios_workwell (nome, email, senha, role, empresa_id, departamento_id, data_cadastro) VALUES
             ('Ana Silva', 'ana.silva@techsolutions.com', 'admin1234', 'ADMIN', 1, 1, '2024-01-30'),
             ('Carlos Santos', 'carlos.santos@inovadigital.com', 'admin9876', 'ADMIN', 2, 3, '2024-01-30'),
             ('Mariana Oliveira', 'mariana.oliveira@techsolutions.com', 'senha123', 'USER', 1, 1, '2024-01-31'),
             ('Pedro Costa', 'pedro.costa@techsolutions.com', 'senha123', 'USER', 1, 1, '2024-01-31'),
             ('Juliana Lima', 'juliana.lima@techsolutions.com', 'senha123', 'USER', 1, 2, '2024-02-01'),
             ('Ricardo Almeida', 'ricardo.almeida@inovadigital.com', 'senha123', 'USER', 2, 3, '2024-02-01'),
             ('Fernanda Souza', 'fernanda.souza@inovadigital.com', 'senha123', 'USER', 2, 4, '2024-02-02'),
             ('Roberto Ferreira', 'roberto.ferreira@healthcare.com', 'senha123', 'USER', 3, 5, '2024-02-02'),
             ('Patricia Mendes', 'patricia.mendes@healthcare.com', 'senha123', 'USER', 3, 6, '2024-02-03'),
             ('Lucas Rodrigues', 'lucas.rodrigues@edutech.com', 'senha123', 'USER', 4, 7, '2024-02-03');

UPDATE usuarios_workwell
SET senha = '$2a$10$emLsRiGTp49V5yNryc4KheAYJ45gaXx6cZjapdybzdoFQ0EGWoFDy'
WHERE email = 'ana.silva@techsolutions.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$2IeyvsSHWb5LVNXuxCEUjuEglWRC2arrmJ2Yaj9Cf6bGE./HgOAby'
WHERE email = 'carlos.santos@inovadigital.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$wOknq/1g2K88LR8oIW2Ml.gbU/HQK950n5DbisbtnAZfKMRlXTVKC'
WHERE email = 'mariana.oliveira@techsolutions.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$iGief1M65Yae2OHceFEfsOyoyboR2SNxPwa914PMn1tFJu1rqenP.'
WHERE email = 'pedro.costa@techsolutions.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$p251SpT9b7aLZ0yGHRTTNe0nrC/xizfHGlkMEO9rMSq0dBaiInt3G'
WHERE email = 'juliana.lima@techsolutions.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$ZlizGKiU54bZd2JcOPJZsO36GzH3167IaZGruEqzUvUMbr19QUn4W'
WHERE email = 'ricardo.almeida@inovadigital.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$uLGjjS/GAlZwAH4D0gnRDuZZLUwHP7sXwP2Yk/vBjrIjShKNdRkQa'
WHERE email = 'fernanda.souza@inovadigital.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$zNp6fnoVtKbF2BIbzp1iC.DnScoexFPeYdx6mFkYNhql/4m.YWDA6'
WHERE email = 'roberto.ferreira@healthcare.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$F0Y50QhB.OqHq6eZrKiZmui5yEpKeVt0LDwRE0hTgwOP3lKnTCnz6'
WHERE email = 'patricia.mendes@healthcare.com';

UPDATE usuarios_workwell
SET senha = '$2a$10$9bQeKXtMLgZ1ucq1v7MW3ug0/SWWmQ45U.KvgbRDLCxiPVU.X9cqW'
WHERE email = 'lucas.rodrigues@edutech.com';




-- Inserir 10 checkins
INSERT INTO checkins_diarios_workwell (usuario_id, data, nivel_stress, horas_trabalhadas, sentimentos, observacoes) VALUES
            (3, '2024-02-04', 8, 10.5, 'ansioso,sobrecarregado', 'Deadline do projeto novo, equipe reduzida'),
            (3, '2024-02-05', 7, 9.0, 'preocupado,cansado', 'Reuniões consecutivas, pouco tempo para codar'),
            (3, '2024-02-06', 9, 11.0, 'exausto,estressado', 'Bug crítico em produção, trabalhei até tarde'),
            (4, '2024-02-04', 4, 8.0, 'calmo,produtivo', 'Dia tranquilo, consegui focar nas tasks'),
            (4, '2024-02-05', 5, 8.5, 'concentrado,motivado', 'Projeto interessante, aprendi coisas novas'),
            (5, '2024-02-04', 6, 9.0, 'ocupada,util', 'Muitos processos seletivos, gestão de conflitos'),
            (5, '2024-02-05', 7, 9.5, 'preocupada,empática', 'Funcionário com sinais de burnout, preciso ajudar'),
            (6, '2024-02-04', 5, 8.0, 'confiante,satisfeito', 'Cliente satisfeito com entrega'),
            (6, '2024-02-05', 7, 10.0, 'pressão,desafiado', 'Proposta complexa, preciso estudar mais'),
            (7, '2024-02-04', 6, 9.5, 'motivada,competitiva', 'Bateu meta do mês, mas próximo trimestre será difícil');

-- Inserir 10 métricas de saúde
INSERT INTO metricas_saude_workwell (usuario_id, data, qualidade_sono, atividade_fisica, isolamento_social, consumo_agua) VALUES
              (3, '2024-02-04', 3, 0, 8, 2),
              (3, '2024-02-05', 4, 15, 7, 3),
              (4, '2024-02-04', 7, 45, 4, 6),
              (4, '2024-02-05', 8, 30, 3, 8),
              (5, '2024-02-04', 6, 20, 5, 5),
              (5, '2024-02-05', 5, 25, 6, 4),
              (6, '2024-02-04', 7, 35, 4, 7),
              (7, '2024-02-04', 6, 40, 5, 6),
              (8, '2024-02-04', 5, 25, 6, 5),
              (9, '2024-02-04', 8, 60, 3, 8);

-- Inserir 10 alertas de burnout
INSERT INTO alertas_burnout_workwell (usuario_id, data, nivel_risco, pontuacao_total, recomendacoes) VALUES
             (3, '2024-02-04', 'ALTO', 78, 'Recomendado: reduzir horas extras, consultar psicólogo, praticar exercícios, aumentar consumo de água'),
             (3, '2024-02-05', 'CRITICO', 85, 'URGENTE: conversar com gestor sobre carga, tirar 2 dias de folga, procurar apoio profissional'),
             (5, '2024-02-04', 'MEDIO', 65, 'Sugerido: melhorar qualidade do sono, pausas regulares, atividades sociais'),
             (4, '2024-02-04', 'BAIXO', 35, 'Manter bons hábitos: sono regular, exercícios, hidratação'),
             (6, '2024-02-04', 'BAIXO', 42, 'Continuar com rotina equilibrada, atenção ao isolamento social'),
             (7, '2024-02-04', 'BAIXO', 38, 'Bom equilíbrio, manter prática de exercícios'),
             (8, '2024-02-04', 'MEDIO', 58, 'Melhorar qualidade do sono, reduzir isolamento'),
             (9, '2024-02-04', 'BAIXO', 32, 'Excelentes hábitos, continuar assim'),
             (10, '2024-02-04', 'BAIXO', 45, 'Rotina saudável, atenção ao estresse ocasional'),
             (2, '2024-02-04', 'MEDIO', 62, 'Gestor: atenção ao equilíbrio vida-trabalho, delegar mais');