package br.com.fiap.workwell.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios_workwell")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "Formato de email inválido.")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres.")
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @NotNull(message = "A role não pode ser nula.")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private RoleUsuario role = RoleUsuario.USER;

    @NotNull(message = "A empresa não pode ser nula.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_empresa"))
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", foreignKey = @ForeignKey(name = "fk_usuarios_departamento"))
    private Departamento departamento;

    @NotNull(message = "A data de cadastro não pode ser nula.")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    public enum RoleUsuario {
        ADMIN, USER
    }
}
