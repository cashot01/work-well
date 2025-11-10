package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
