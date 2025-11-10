package br.com.fiap.workwell.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metricas_saude_workwell")
public class MetricaSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O usuário não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_metricas_usuario"))
    private Usuario usuario;

    @NotNull(message = "A data não pode ser nula.")
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull(message = "A qualidade do sono não pode ser nula.")
    @Column(name = "qualidade_sono", nullable = false)
    private Integer qualidadeSono;

    @NotNull(message = "A atividade física não pode ser nula.")
    @Column(name = "atividade_fisica", nullable = false)
    private Integer atividadeFisica;

    @NotNull(message = "O isolamento social não pode ser nulo.")
    @Column(name = "isolamento_social", nullable = false)
    private Integer isolamentoSocial;

    @NotNull(message = "O consumo de água não pode ser nulo.")
    @Column(name = "consumo_agua", nullable = false)
    private Integer consumoAgua;
}
