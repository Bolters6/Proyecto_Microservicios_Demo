package curso.microservicios.usuarios.repositories;

import curso.microservicios.libreria_usuarios_commons.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "usuarios", path = "usuarios")
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    @RestResource(path = "/getuser")
    Optional<Usuario> findByUsername(@Param("username") String username);

}
