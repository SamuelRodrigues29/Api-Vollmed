package med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

        //classe de filtro de segurança servlet.
@Component
public class SecurityFilter extends OncePerRequestFilter {

        // atributo para pegar o token validado e usar no filter
        @Autowired // o spring faz a injeção de dependência
        private TokenService tokenService;

        @Autowired
        private UsuarioRepository repository;


        // método para pegar o token e validar.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            var tokenJWT = recuperarToken(request); // pega o token na requisição.

            if (tokenJWT != null) {
                var subject = tokenService.getSubject(tokenJWT); // objeto que pega o token validado na classe tokenservice.
                var usuario = repository.findByLogin(subject); // recupera o usuário logado com token validado a partir do repository.
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // cria DTO que representa o usuário
                SecurityContextHolder.getContext().setAuthentication(authentication); // força autenticação.

            }

            filterChain.doFilter(request,response); // continua a cadeia de chamada de filtro e passa o request e response.


    }

            private String recuperarToken(HttpServletRequest request) {
                var authorizationHeader = request.getHeader("Authorization"); // recupera o token vindo no header da requisiçãop http
                if(authorizationHeader != null) {
                    return authorizationHeader.replace("Bearer", "");
                }
                return null;
            }
        }
