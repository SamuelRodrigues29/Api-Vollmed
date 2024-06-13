package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


// interface que faz solitação ao banco de dados.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    UserDetails findByLogin(String login);
}
