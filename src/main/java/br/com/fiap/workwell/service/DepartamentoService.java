package br.com.fiap.workwell.service;

import br.com.fiap.workwell.model.Departamento;
import br.com.fiap.workwell.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
    }

    public void salvar(Departamento departamento) {
        departamentoRepository.save(departamento);
    }

    public void excluir(Long id) {
        departamentoRepository.deleteById(id);
    }
}
