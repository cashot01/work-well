package br.com.fiap.workwell.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alertas_burnout_workwell")
public class AlertaBurnout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O usuário não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_alertas_usuario"))
    private Usuario usuario;

    @NotNull(message = "A data não pode ser nula.")
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull(message = "O nível de risco não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_risco", nullable = false, length = 20)
    private NivelRisco nivelRisco;

    @NotNull(message = "A pontuação total não pode ser nula.")
    @Column(name = "pontuacao_total", nullable = false)
    private Integer pontuacaoTotal;

    @NotBlank(message = "As recomendações não podem estar em branco.")
    @Size(max = 1000, message = "As recomendações devem ter no máximo 1000 caracteres.")
    @Column(name = "recomendacoes", nullable = false, length = 1000)
    private String recomendacoes;

}