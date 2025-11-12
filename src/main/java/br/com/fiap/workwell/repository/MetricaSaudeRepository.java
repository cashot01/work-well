package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.MetricaSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MetricaSaudeRepository extends JpaRepository<MetricaSaude, Long> {

    @Query("SELECT AVG(m.qualidadeSono) FROM MetricaSaude m")
    Double findAverageQualidadeSono();

    @Query("SELECT AVG(m.atividadeFisica) FROM MetricaSaude m")
    Double findAverageAtividadeFisica();

    @Query("SELECT COUNT(m) FROM MetricaSaude m WHERE m.data = CURRENT_DATE")
    Long countMetricasHoje();
}
