package med.voll.api.domain.usuario;


// dados que serão recebidos no processo de autenticação
public record DadosAutenticacao(String login, String senha) {
}
