package br.com.fiap.workwell.service;

import br.com.fiap.workwell.model.Empresa;
import br.com.fiap.workwell.repository.EmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
    
    public Page<Empresa> listarEmpresasPaginadas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    public void salvar(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public void excluir(Long id) {
        empresaRepository.deleteById(id);
    }
}
