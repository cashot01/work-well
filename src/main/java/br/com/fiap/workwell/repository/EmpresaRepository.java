package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
