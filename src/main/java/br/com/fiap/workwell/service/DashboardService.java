package br.com.fiap.workwell.service;

import br.com.fiap.workwell.model.NivelRisco;
import br.com.fiap.workwell.repository.AlertaBurnoutRepository;
import br.com.fiap.workwell.repository.CheckinDiarioRepository;
import br.com.fiap.workwell.repository.MetricaSaudeRepository;
import br.com.fiap.workwell.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final AlertaBurnoutRepository alertaBurnoutRepository;
    private final CheckinDiarioRepository checkinDiarioRepository;
    private final MetricaSaudeRepository metricaSaudeRepository;

    public DashboardService(UsuarioRepository usuarioRepository,
                            AlertaBurnoutRepository alertaBurnoutRepository,
                            CheckinDiarioRepository checkinDiarioRepository,
                            MetricaSaudeRepository metricaSaudeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.alertaBurnoutRepository = alertaBurnoutRepository;
        this.checkinDiarioRepository = checkinDiarioRepository;
        this.metricaSaudeRepository = metricaSaudeRepository;
    }

    public Long getTotalUsuarios() {
        return usuarioRepository.count();
    }

    public Long getTotalAlertasAtivos() {
        return alertaBurnoutRepository.countByNivelRiscoIn(List.of(NivelRisco.ALTO, NivelRisco.CRITICO));
    }

    public Double getSaudeGeral() {
        Double mediaQualidadeSono = metricaSaudeRepository.findAverageQualidadeSono();
        Double mediaAtividadeFisica = metricaSaudeRepository.findAverageAtividadeFisica();

        if (mediaQualidadeSono == null || mediaAtividadeFisica == null) {
            return 0.0;
        }

        double score = (mediaQualidadeSono * 10) + (mediaAtividadeFisica / 6.0);
        return Math.min(100.0, Math.max(0.0, score));
    }

    public Long getTotalCasosCriticos() {
        return alertaBurnoutRepository.countByNivelRisco(NivelRisco.CRITICO);
    }

    public Long getTotalCheckinsHoje() {
        return checkinDiarioRepository.countCheckinsHoje();
    }

    public Long getTotalMetricasHoje() {
        return metricaSaudeRepository.countMetricasHoje();
    }
}