package com.uniSystemAPI.controller.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.PrimeiroAcesso.DadosCadastroPrimeiroAcesso;
import com.uniSystemAPI.domain.estabelecimento.DetalhamentoEstabelecimento;
import com.uniSystemAPI.domain.estabelecimento.Estabelecimento;
import com.uniSystemAPI.domain.estabelecimento.EstabelecimentoRepository;
import com.uniSystemAPI.domain.usuario.Usuario;
import com.uniSystemAPI.domain.usuario.UsuarioRepository;
import com.uniSystemAPI.domain.util.SalvaMidiaNoDisco;

import jakarta.validation.Valid;

@RestController
@RequestMapping("primeiroacesso")
public class PrimeiroAcessoController {
    private final String subDiretorioEstabelecimentoImagens = "EstabelecimetoImagens";
    private final String subDiretorioImagensUsuario = "UserImages";

    @Autowired
    private UsuarioRepository repositoryUser;

    @Autowired
    private EstabelecimentoRepository repositoryEstab;

    @PostMapping("novo")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroPrimeiroAcesso cadastroPrimeiroAcesso) {

        try {
            if (this.repositoryEstab.findByCpfCnpj(cadastroPrimeiroAcesso.estabCpfCnpj()) != null)
                return ResponseEntity.badRequest().body("CPF/CNPJ já cadastrado");

            if (this.repositoryUser.findByLoginIgnoreCase(cadastroPrimeiroAcesso.userEmail()) != null)
                return ResponseEntity.badRequest().body("O Email já existe");

            Estabelecimento estabelecimento = new Estabelecimento(cadastroPrimeiroAcesso);

            estabelecimento
                    .setLogoMarca(
                            SalvaMidiaNoDisco.salvaImagemBase64NoDisco(cadastroPrimeiroAcesso.estabLogoMarcaBase64(),
                                    subDiretorioEstabelecimentoImagens, cadastroPrimeiroAcesso.estabNomeLogoMarca()));

            repositoryEstab.save(estabelecimento);

            String idEstabelecimento = new DetalhamentoEstabelecimento(
                    repositoryEstab.findByCpfCnpj(
                            cadastroPrimeiroAcesso.estabCpfCnpj()))
                    .id();

            // cadastro de usuario com o id do estabelecimento
            // ------------------------------------------------------------------------------------

            Usuario usuario = new Usuario(cadastroPrimeiroAcesso);

            usuario.setEstabelecimentoId(idEstabelecimento);

            usuario.getPessoa()
                    .setNomeImagem(SalvaMidiaNoDisco.salvaImagemBase64NoDisco(cadastroPrimeiroAcesso.userImagemBase64(),
                            subDiretorioImagensUsuario, cadastroPrimeiroAcesso.userNomeImagem()));

            String encryptedPassword = new BCryptPasswordEncoder().encode(cadastroPrimeiroAcesso.userSenha());
            usuario.setSenha(encryptedPassword);

            repositoryUser.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // return ResponseEntity.ok("{\"id\":\"" + idEstabelecimento + "\"}");
        return ResponseEntity.ok().build();
    }

}
