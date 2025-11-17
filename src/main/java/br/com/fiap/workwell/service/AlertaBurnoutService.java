package br.com.fiap.workwell.service;

import br.com.fiap.workwell.model.AlertaBurnout;
import br.com.fiap.workwell.repository.AlertaBurnoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaBurnoutService {

    private final AlertaBurnoutRepository alertaBurnoutRepository;

    public AlertaBurnoutService(AlertaBurnoutRepository alertaBurnoutRepository) {
        this.alertaBurnoutRepository = alertaBurnoutRepository;
    }

    public List<AlertaBurnout> listarTodos() {
        return alertaBurnoutRepository.findAll();
    }

    public Page<AlertaBurnout> listarAlertasPaginados(Pageable pageable) {
        return alertaBurnoutRepository.findAll(pageable);
    }

    public AlertaBurnout buscarPorId(Long id) {
        return alertaBurnoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta de burnout não encontrado"));
    }

    public void salvar(AlertaBurnout alertaBurnout) {
        alertaBurnoutRepository.save(alertaBurnout);
    }

    public void excluir(Long id) {
        alertaBurnoutRepository.deleteById(id);
    }
}
