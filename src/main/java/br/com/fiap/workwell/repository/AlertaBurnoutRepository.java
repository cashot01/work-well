package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.AlertaBurnout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AlertaBurnoutRepository extends JpaRepository<AlertaBurnout, Long> {
    Long countByNivelRiscoIn(List<String> niveisRisco);
    Long countByNivelRisco(String nivelRisco);

    // Métudo alternativo se o anterior não funcionar
    @Query("SELECT COUNT(a) FROM AlertaBurnout a WHERE a.nivelRisco IN :niveisRisco")
    Long countByNivelRiscoIn(@Param("niveisRisco") String[] niveisRisco);
}
