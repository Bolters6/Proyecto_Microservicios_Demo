package curso.microservicios.oauth_server.services;

import curso.microservicios.libreria_usuarios_commons.models.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final RestTemplate restTemplate;

    @Override
    public Usuario getUserByUsername(String username) throws HttpClientErrorException{
        String url = "http://microservicio-usuarios/usuarios/search/getuser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("username", username);
        return restTemplate.getForObject(builder.build().toUri(), Usuario.class);
        // No necesito poner un Optional y verificar si esta presente el valor o no porque rest template si no encuentra el valor ya lanza una excepcion de not found 404 HttpClientErrorException
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Usuario usuario = getUserByUsername(username);
            Set<SimpleGrantedAuthority> authorities = usuario.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
            log.info("Usuario {} Authenticado", username);
            return new User(username,
                    usuario.getPassword(),
                    usuario.isEnabled(),
                    true,
                    true,
                    true,
                    authorities);
        }catch(HttpClientErrorException exception){
            log.error("Usuario No Existe", exception);
            throw new UsernameNotFoundException("Usuario No Encontrado");
        }
    }
}
