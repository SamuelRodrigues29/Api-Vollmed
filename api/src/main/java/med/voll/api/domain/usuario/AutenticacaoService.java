package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// classe que fará autenticação do usuário  e verificará se o mesmo existe no banco de dados.

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired // faz insejção de dependência
    private UsuarioRepository repository;

    // Esse é o método que fará a consulta no banco de dados na tabela usuário.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
