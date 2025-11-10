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
@Table(name = "usuarios_workwell", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_email", columnNames = {"email"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "Formato de e-mail inválido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(max = 100, message = "A senha deve ter no máximo 100 caracteres.")
    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    @NotBlank(message = "O perfil não pode estar em branco.")
    @Size(max = 50, message = "O perfil deve ter no máximo 50 caracteres.")
    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(name = "fk_usuarios_empresa"))
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", foreignKey = @ForeignKey(name = "fk_usuarios_departamento"))
    private Departamento departamento;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;
}
