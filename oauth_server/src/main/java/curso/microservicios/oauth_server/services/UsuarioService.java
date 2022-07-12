package curso.microservicios.oauth_server.services;

import curso.microservicios.libreria_usuarios_commons.models.Usuario;

public interface UsuarioService {
    Usuario getUserByUsername(String username);
}
