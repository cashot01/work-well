package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.CheckinDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CheckinDiarioRepository extends JpaRepository<CheckinDiario, Long> {

    @Query("SELECT COUNT(c) FROM CheckinDiario c WHERE c.data = CURRENT_DATE")
    Long countCheckinsHoje();
}
