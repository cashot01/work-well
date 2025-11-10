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
@Table(name = "checkins_diarios_workwell")
public class CheckinDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O usuário não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_checkins_usuario"))
    private Usuario usuario;

    @NotNull(message = "A data não pode ser nula.")
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull(message = "O nível de stress não pode ser nulo.")
    @Column(name = "nivel_stress", nullable = false)
    private Integer nivelStress;

    @NotNull(message = "As horas trabalhadas não podem ser nulas.")
    @Column(name = "horas_trabalhadas", nullable = false)
    private Double horasTrabalhadas;

    @NotBlank(message = "Os sentimentos não podem estar em branco.")
    @Size(max = 500, message = "Os sentimentos devem ter no máximo 500 caracteres.")
    @Column(name = "sentimentos", nullable = false, length = 500)
    private String sentimentos;

    @NotBlank(message = "As observações não podem estar em branco.")
    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    @Column(name = "observacoes", nullable = false, length = 1000)
    private String observacoes;
}