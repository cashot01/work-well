package br.com.fiap.workwell.repository;

import br.com.fiap.workwell.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query(value = "SELECT fn_usuario_json_manual(:usuarioId)", nativeQuery = true)
    String executarFnUsuarioJson(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT fn_calcular_compatibilidade_vaga(:usuarioId, :vagaTitulo, :vagaCompetencias, :nivelStressMax)", nativeQuery = true)
    String executarFnCompatibilidadeVaga(
            @Param("usuarioId") Long usuarioId,
            @Param("vagaTitulo") String vagaTitulo,
            @Param("vagaCompetencias") String vagaCompetencias,
            @Param("nivelStressMax") Integer nivelStressMax);
}

