package curso.microservicios.usuarios.repositories;

import curso.microservicios.libreria_usuarios_commons.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles", exported = false)
public interface RoleRepo extends JpaRepository<Role, Long> {
}
