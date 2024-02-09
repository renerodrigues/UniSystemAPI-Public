package com.uniSystemAPI.controller.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.Infra.TokenService;
import com.uniSystemAPI.domain.Login.AuthenticationDTO;
import com.uniSystemAPI.domain.Login.LoginResponseDTO;
import com.uniSystemAPI.domain.Login.TokenValidoDTO;
import com.uniSystemAPI.domain.usuario.Usuario;
import com.uniSystemAPI.domain.usuario.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    TokenService tokenService;

    private final String nomeCacheLogin = "login_cache";

    @PostMapping("/login")
     @CacheEvict(value = nomeCacheLogin, allEntries = true)
   public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {

        System.out.println("data.email()" + data.email());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        var user = repository.findByLoginIgnoreCase(data.email());
        var userId = user.getId();
        var estabId = user.getEstabelecimentoId();
        System.out.println(userId);

        return ResponseEntity.ok(new LoginResponseDTO(token, userId, estabId));
    }

    @GetMapping("/validaToken/{token}")
    @Cacheable(nomeCacheLogin)
    public ResponseEntity<TokenValidoDTO> validaToken(@PathVariable String token) {

        var usuarioEmail = tokenService.getUserSubjectFromToken(token);
        if (usuarioEmail != null) {

            var userId = repository.findByLoginIgnoreCase(usuarioEmail);
            return ResponseEntity.ok(new TokenValidoDTO(userId));
        }
        return ResponseEntity.badRequest().build();
    }
}
