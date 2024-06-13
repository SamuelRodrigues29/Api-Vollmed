package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping // C do acrônimo CRUD
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder uriBuilder){
       var medico =new Medico(dados) ;
        repository.save(medico); // salva os dados no banco de dados.

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    // retorno dos dados da api vindos como páginas. o front faz sua parte também.
    @GetMapping // R do acrônimo CRUD
    public ResponseEntity <Page<DadosListagemMedico>> listar(Pageable paginacao) {
       var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico:: new);
       return ResponseEntity.ok(page);
    }

    @PutMapping // U do acrônimo CRUD
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados ) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    // D do acrônimo CRUD
    @DeleteMapping("/{id}") // o parãmetro id entre chaves é dinâmico, vai de acordo com a escolha.
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}") // o parãmetro id entre chaves é dinâmico, vai de acordo com a escolha.
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }
}


