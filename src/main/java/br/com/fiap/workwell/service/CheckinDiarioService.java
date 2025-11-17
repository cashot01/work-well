package br.com.fiap.workwell.service;

import br.com.fiap.workwell.model.CheckinDiario;
import br.com.fiap.workwell.repository.CheckinDiarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinDiarioService {

    private final CheckinDiarioRepository checkinDiarioRepository;

    public CheckinDiarioService(CheckinDiarioRepository checkinDiarioRepository) {
        this.checkinDiarioRepository = checkinDiarioRepository;
    }

    public List<CheckinDiario> listarTodos() {
        return checkinDiarioRepository.findAll();
    }

    public Page<CheckinDiario> listarCheckinsPaginados(Pageable pageable) {
        return checkinDiarioRepository.findAll(pageable);
    }

    public CheckinDiario buscarPorId(Long id) {
        return checkinDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Check-in diário não encontrado"));
    }

    public void salvar(CheckinDiario checkinDiario) {
        checkinDiarioRepository.save(checkinDiario);
    }

    public void excluir(Long id) {
        checkinDiarioRepository.deleteById(id);
    }
}
