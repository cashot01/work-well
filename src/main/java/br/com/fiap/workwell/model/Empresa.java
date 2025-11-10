package br.com.fiap.workwell.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresas_workwell")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "O CNPJ não pode estar em branco.")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "Formato de CNPJ inválido.")
    @Column(name = "cnpj", nullable = false, unique = true, length = 18)
    private String cnpj;

    @NotBlank(message = "O setor não pode estar em branco.")
    @Size(max = 100, message = "O setor deve ter no máximo 100 caracteres.")
    @Column(name = "setor", nullable = false, length = 100)
    private String setor;

    @NotNull(message = "A data de cadastro não pode ser nula.")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;
}
