package com.uniSystemAPI.controller.usuarios;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.ValidacaoEmail.ValidaEmail;
import com.uniSystemAPI.domain.ValidacaoEmail.ValidaEmailRepository;
import com.uniSystemAPI.domain.estabelecimento.DetalhamentoEstabelecimento;
import com.uniSystemAPI.domain.estabelecimento.EstabelecimentoRepository;
import com.uniSystemAPI.domain.usuario.DadosAlteraSenha;
import com.uniSystemAPI.domain.usuario.DadosAtualizacaoDadosPessoaisUsuario;
import com.uniSystemAPI.domain.usuario.DadosAtualizacaoEndereco;
import com.uniSystemAPI.domain.usuario.DadosAtualizacaoImagemUsuario;
import com.uniSystemAPI.domain.usuario.DadosCadastroUsuario;
import com.uniSystemAPI.domain.usuario.DadosListagemUsuario;
import com.uniSystemAPI.domain.usuario.DetalhamentoUsuario;
import com.uniSystemAPI.domain.usuario.Usuario;
import com.uniSystemAPI.domain.usuario.UsuarioRepository;
import com.uniSystemAPI.domain.util.SalvaMidiaNoDisco;

import jakarta.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final String subDiretorioImagensUsuario = "UserImages";
    private final String nomeCacheUsuarios = "usuario_cache";

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ValidaEmailRepository repositoryEmailCode;

    @Autowired
    ValidaEmailRepository repositoryValidaEmail;

    @Autowired
    EstabelecimentoRepository repositoryEstab;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("novo")
    @CacheEvict(value = nomeCacheUsuarios, allEntries = true)
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroUsuario cadastroUsuario) {
        try {
            if (this.repository.findByLoginIgnoreCase(cadastroUsuario.login()) != null)
                return ResponseEntity.badRequest().body("O Email já existe");
            Usuario usuario = new Usuario(cadastroUsuario);

            usuario.getPessoa().setNomeImagem(SalvaMidiaNoDisco.salvaImagemBase64NoDisco(cadastroUsuario.imagemBase64(),
                    subDiretorioImagensUsuario, cadastroUsuario.pessoa().nomeImagem()));

            String encryptedPassword = new BCryptPasswordEncoder().encode(cadastroUsuario.senha());
            usuario.setSenha(encryptedPassword);

            repository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao Salvar: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("{idestab}")
    @Cacheable(nomeCacheUsuarios)
    public List<DadosListagemUsuario> listar(@PathVariable String idestab) {
        return repository.findAllByEstabelecimentoIdOrderByPessoaNomeAsc(idestab).stream()
                .map(DadosListagemUsuario::new).toList();
    }

    @PutMapping("dadosPessoais")
    @Transactional
    @CacheEvict(value = nomeCacheUsuarios, allEntries = true)
    public ResponseEntity<String> atualizarDadosPessoaisUsuario(
            @RequestBody @Valid DadosAtualizacaoDadosPessoaisUsuario dados) {
        // System.out.println(dados);
        // var usuario = repository.getReferenceById(dados.id());
        // usuario.atualizarDadosPessoaisUsuario(dados);
        // var estabelecimento =
        // repositoryEstab.getReferenceById(usuario.getEstabelecimentoId());

        // return ResponseEntity
        // .ok(new DetalhamentoUsuario(usuario, new
        // DetalhamentoEstabelecimento(estabelecimento)));
        try {
            var usuario = repository.getReferenceById(dados.id());

            if (usuario != null) {
                usuario.atualizarDadosPessoaisUsuario(dados);
                // var estabelecimento =
                // repositoryEstab.getReferenceById(usuario.getEstabelecimentoId());
                return ResponseEntity
                        .ok().body("Dados alterados com sucesso!");
            } else {
                return ResponseEntity
                        .badRequest().body("Erro! Não foi possivel localizar o usuário");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest().body("Erro ao atualizar os dados -> " + e.getMessage());
        }
    }

    @PutMapping("endereco")
    @Transactional
    @CacheEvict(value = nomeCacheUsuarios, allEntries = true)
    public ResponseEntity<String> atualizarDadosEnderecoUsuario(
            @RequestBody @Valid DadosAtualizacaoEndereco dados) {
        // System.out.println(dados);
        try {
            var usuario = repository.getReferenceById(dados.idUsuario());

            if (usuario != null) {
                usuario.atualizarEnderecoUsuario(dados);
                // var estabelecimento =
                // repositoryEstab.getReferenceById(usuario.getEstabelecimentoId());
                return ResponseEntity
                        .ok().body("Endereço alterado com sucesso!");
            } else {
                return ResponseEntity
                        .badRequest().body("Erro! Não foi possivel localizar o usuário");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest().body("Erro ao atualizar o endereço -> " + e.getMessage());
        }
    }

    @GetMapping("/detalhar/{id}")
    @Cacheable(value = nomeCacheUsuarios)
    public ResponseEntity<DetalhamentoUsuario> dealharUsuario(@PathVariable String id) {
        var usuario = repository.getReferenceById(id);
        var estabelecimento = repositoryEstab.getReferenceById(usuario.getEstabelecimentoId());

        return ResponseEntity
                .ok(new DetalhamentoUsuario(usuario, new DetalhamentoEstabelecimento(estabelecimento)));
    }

    @GetMapping("/imagem/{nomeImagem}")
    public ResponseEntity<Resource> obterImagem(@PathVariable String nomeImagem) {
        Resource resource = null;
        try {
            resource = new FileSystemResource(
                    SalvaMidiaNoDisco.obtemCaminhoImagem(subDiretorioImagensUsuario, nomeImagem));

            if (!resource.exists() | resource == null) {

                var userImage = repository.findByPessoaNomeImagem(nomeImagem);

                if (userImage != null & userImage.getPessoa().getFotoBase64() != null) {
                    try {
                        SalvaMidiaNoDisco.salvaImagemBase64NoDisco(
                                new String(userImage.getPessoa().getFotoBase64(), StandardCharsets.UTF_8),
                                subDiretorioImagensUsuario, userImage.getPessoa().getNomeImagem());

                        resource = new FileSystemResource(
                                SalvaMidiaNoDisco.obtemCaminhoImagem(subDiretorioImagensUsuario, nomeImagem));
                    } catch (IOException e) {

                    }
                } else {
                    resource = new FileSystemResource(
                            SalvaMidiaNoDisco.caminhoImagemNaoEncontrada());
                }
            }

        } catch (Exception e) {
            resource = new FileSystemResource(
                    SalvaMidiaNoDisco.caminhoImagemNaoEncontrada());
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .contentType(MediaType.IMAGE_GIF)
                .body(resource);
    }

    @PutMapping("imagem")
    @Transactional
    @CacheEvict(value = nomeCacheUsuarios, allEntries = true)
    public ResponseEntity<?> atualizarImagem(
            @RequestBody DadosAtualizacaoImagemUsuario imagemUsuario) {
        try {

            var usuario = repository.getReferenceById(imagemUsuario.id());

            if (SalvaMidiaNoDisco.apagarImagemDoDisco(subDiretorioImagensUsuario,
                    usuario.getPessoa().getNomeImagem())) {
                usuario.getPessoa()
                        .setNomeImagem(SalvaMidiaNoDisco.salvaImagemBase64NoDisco(imagemUsuario.imagemBase64(),
                                subDiretorioImagensUsuario, imagemUsuario.nomeImagem()));

                usuario.atualizarImagemUsuario(imagemUsuario);

                // var estabelecimento =
                // repositoryEstab.getReferenceById(usuario.getEstabelecimentoId());
                // // var uri =
                // //
                // uriBilder.path(pathImagem).buildAndExpand(usuario.getPessoa().getFoto()).toUri();

                // return ResponseEntity
                // .ok(new DetalhamentoUsuario(usuario, new
                // DetalhamentoEstabelecimento(estabelecimento)));

                return ResponseEntity.ok().body("Imagem alterada com sucesso!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar a requisição: " + e.getMessage());
        }

        return ResponseEntity.badRequest().body("Erro desconhecido ao processar a requisição.");
    }

    @PostMapping("alterar-senha")
    @Transactional
    @CacheEvict(value = nomeCacheUsuarios, allEntries = true)
    public ResponseEntity<String> alterarSenha(@RequestBody DadosAlteraSenha alterarSenha) {
        // Autenticar o usuário com a senha atual
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            alterarSenha.email(),
                            alterarSenha.senhaAnterior()));

            System.out.println("Aqui");

            // Se a autenticação for bem-sucedida, a senha atual está correta
            if (authentication.isAuthenticated()) {
                if (alterarSenha.novaSenha().equals(alterarSenha.repeteSenha())) {

                    // Se a nova senha autenticar significa que é igual a anterior. retornar erro se
                    // verdade
                    if (alterarSenha.novaSenha().equals(alterarSenha.senhaAnterior())) {
                        return ResponseEntity.badRequest()
                                .body("A nova senha não pode ser igual a anterior");
                    }

                    String encryptedPassword = new BCryptPasswordEncoder().encode(alterarSenha.novaSenha());
                    // usuario.setSenha(encryptedPassword);

                    var usuario = repository.getReferenceById(alterarSenha.id());
                    usuario.setSenha(encryptedPassword); // aqui a senha é alterada no banco de dados
                    // usuario.alterarSenha(usuario.getSenha());
                    SecurityContextHolder.clearContext();

                    return ResponseEntity.ok("Senha alterada!");

                } else {
                    return ResponseEntity.badRequest().body("As Senhas não conferem");
                }
            } else {
                return ResponseEntity.badRequest().body("Senha atual incorreta ou usuário inexistente");
            }
            // return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (Exception e) {
            System.out.println("Senha atual incorreta " + e.getMessage());
            return ResponseEntity.badRequest().body("Senha atual incorreta ou usuário inexistente");
        }
    }

    @PostMapping("/altera-senha-por-codigo")
    @Transactional
    public ResponseEntity<String> validaCodigoRecebidoParaAlterarSenha(@RequestBody @Valid DadosAlteraSenha dados) {
        try {

            System.out.println(
                    "dados.novaSenha() " + dados.novaSenha() + " - dados.repeteSenha() " + dados.repeteSenha()
                            + " - dados.codigo() " + dados.codigo());
            if (dados.novaSenha().equals(dados.repeteSenha())) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                var codigoBancoDeDados = repositoryEmailCode.findByEmail(dados.email());

                for (ValidaEmail item : codigoBancoDeDados) {
                    if (encoder.matches(dados.codigo(), item.getCodigo())) {

                        String encryptedPassword = new BCryptPasswordEncoder().encode(dados.novaSenha());
                        var usuario = repository.findByLoginIgnoreCase(dados.email());

                        if (!encoder.matches(dados.novaSenha(), usuario.getSenha())) {
                            usuario.setSenha(encryptedPassword); // aqui a senha é alterada no banco de dados

                            SecurityContextHolder.clearContext();

                            repositoryValidaEmail.deleteByEmail(dados.email());

                            return ResponseEntity.ok("Senha alterada!");
                        } else {
                            return ResponseEntity.badRequest()
                                    .body(new String("A nova senha deve ser diferente da senha anterior"));
                        }

                    }
                }
                System.out.println("Código inválido");
                return ResponseEntity.badRequest().body(new String("Código inválido"));

            } else {
                return ResponseEntity.badRequest().body(new String("As senhas não conferem"));
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new String("Erro ao alterar senha -> " + e.getMessage()));
        }
    }
}
