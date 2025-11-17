package br.com.fiap.workwell.service;

import br.com.fiap.workwell.model.MetricaSaude;
import br.com.fiap.workwell.repository.MetricaSaudeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricaSaudeService {

    private final MetricaSaudeRepository metricaSaudeRepository;

    public MetricaSaudeService(MetricaSaudeRepository metricaSaudeRepository) {
        this.metricaSaudeRepository = metricaSaudeRepository;
    }

    public List<MetricaSaude> listarTodas() {
        return metricaSaudeRepository.findAll();
    }

    public Page<MetricaSaude> listarMetricasPaginadas(Pageable pageable) {
        return metricaSaudeRepository.findAll(pageable);
    }

    public MetricaSaude buscarPorId(Long id) {
        return metricaSaudeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Métrica de saúde não encontrada"));
    }

    public void salvar(MetricaSaude metricaSaude) {
        metricaSaudeRepository.save(metricaSaude);
    }

    public void excluir(Long id) {
        metricaSaudeRepository.deleteById(id);
    }
}
