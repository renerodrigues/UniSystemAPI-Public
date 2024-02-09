package com.uniSystemAPI.controller.estabelecimento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.estabelecimento.DadosCadastroEstabelecimento;
import com.uniSystemAPI.domain.estabelecimento.DadosListagemEstabelecimento;
import com.uniSystemAPI.domain.estabelecimento.DetalhamentoEstabelecimento;
import com.uniSystemAPI.domain.estabelecimento.Estabelecimento;
import com.uniSystemAPI.domain.estabelecimento.EstabelecimentoRepository;
import com.uniSystemAPI.domain.util.SalvaMidiaNoDisco;

@RestController
@RequestMapping("estabelecimento")
public class EstabelecimentoController {
    private final String subDiretorioEstabelecimentoImagens = "EstabelecimetoImagens";

    @Autowired
    private EstabelecimentoRepository repository;

    @PostMapping("novo")
    // public void cadastrar(@RequestBody @Valid DadosCadastroUsuario
    public ResponseEntity<String> cadastrar(@RequestBody DadosCadastroEstabelecimento cadastroEstabelecimento) {

        try {
            if (this.repository.findByCpfCnpj(cadastroEstabelecimento.cpfCnpj()) != null)
                return ResponseEntity.badRequest().body("CPF/CNPJ já cadastrado");
            // System.out.println(cadastroUsuario);
            Estabelecimento estabelecimento = new Estabelecimento(cadastroEstabelecimento);

            estabelecimento
                    .setLogoMarca(SalvaMidiaNoDisco.salvaImagemBase64NoDisco(cadastroEstabelecimento.logoMarcaBase64(),
                            subDiretorioEstabelecimentoImagens, cadastroEstabelecimento.nomeLogoMarca()));

            // String encryptedPassword = new
            // BCryptPasswordEncoder().encode(cadastroEstabelecimento.senha());
            // estabelecimento.setSenha(encryptedPassword);

            repository.save(estabelecimento);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String idEstabelecimento = new DetalhamentoEstabelecimento(
                repository.findByCpfCnpj(
                        cadastroEstabelecimento.cpfCnpj()))
                .id();

        return ResponseEntity.ok("{\"id\":\"" + idEstabelecimento + "\"}");
    }

    @GetMapping
    // @Scheduled(fixedRate = 5000)
    @Cacheable("dadosListagemEstabelecimento")
    public List<DadosListagemEstabelecimento> listar() {

        return repository.findAll().stream().map(DadosListagemEstabelecimento::new).toList(); // stream.map transforma
        // DadosListagemUsuario em
        // Usuario a partir do
        // construtor da classe dto
        // DadosListagemUsuario
    }

    @GetMapping("/imagem/{nomeImagem}")
    @Cacheable("cacheimagemestabelecimento")
    public ResponseEntity<Resource> obterImagem(@PathVariable String nomeImagem) {
        // System.out.println("aqui");
        Resource resource = new FileSystemResource(
                SalvaMidiaNoDisco.obtemCaminhoImagem(subDiretorioEstabelecimentoImagens, nomeImagem));

        if (!resource.exists()) {
            System.out.println("não existe");

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/detalhar/{id}")

    @Cacheable("detalhamentoestabelecimento")
    public ResponseEntity<DetalhamentoEstabelecimento> dealharUsuario(@PathVariable String id) {
        var estabelecimento = repository.getReferenceById(id);
        if (estabelecimento == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(new DetalhamentoEstabelecimento(estabelecimento));
    }

}
