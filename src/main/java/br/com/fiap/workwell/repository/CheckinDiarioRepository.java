package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.CheckinDiario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinDiarioRepository extends JpaRepository<CheckinDiario, Long> {
}
