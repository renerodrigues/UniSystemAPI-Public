package com.uniSystemAPI.controller.seguranca;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.ValidacaoEmail.DadosValidaEmail;
import com.uniSystemAPI.domain.ValidacaoEmail.EnviaEmail;
import com.uniSystemAPI.domain.ValidacaoEmail.ValidaEmail;
import com.uniSystemAPI.domain.ValidacaoEmail.ValidaEmailRepository;
import com.uniSystemAPI.domain.usuario.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("valida")
public class ValidaEmailController {

    @Autowired
    private ValidaEmailRepository repository;

    @Autowired
    private UsuarioRepository userRepository;

    @PostMapping("/sendMailCodeNewUser")
    public ResponseEntity<String> enviaCodigoEmailNewUser(@RequestBody @Valid DadosValidaEmail dados,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Erro de validação: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(". Código não enviado.");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        try {

            if (this.userRepository.findByLoginIgnoreCase(dados.emailDestinatario()) != null)
                return ResponseEntity.badRequest().body("Esse E-mail já está sendo utilizado em outro cadastro");

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Callable<String> asyncTask = () -> {
                var codigo = EnviaEmail.envia(dados.emailDestinatario());

                ValidaEmail validaEmail = new ValidaEmail(dados);

                String encryptedCode = new BCryptPasswordEncoder().encode(codigo);

                validaEmail.setCodigo(encryptedCode);

                repository.save(validaEmail);
                return "Tarefa concluída";
            };

            // Envie a tarefa para o pool de threads e obtenha um Future
            // Future<String> future = executorService.submit(asyncTask);
            executorService.submit(asyncTask);
            return ResponseEntity.ok("Código enviado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new String("Erro ao envar o codigo\n\n" + e.getMessage()));
        }
    }
    @PostMapping("/sendMailCodeRecuperaSenha")
    public ResponseEntity<String> enviaCodigoEmailRecuperaSenha(@RequestBody @Valid DadosValidaEmail dados,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Erro de validação: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(". Código não enviado.");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        try {

            if (this.userRepository.findByLoginIgnoreCase(dados.emailDestinatario()) == null)
                return ResponseEntity.badRequest().body("E-mail não localizado na base de dados");

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Callable<String> asyncTask = () -> {
                var codigo = EnviaEmail.envia(dados.emailDestinatario());

                ValidaEmail validaEmail = new ValidaEmail(dados);

                String encryptedCode = new BCryptPasswordEncoder().encode(codigo);

                validaEmail.setCodigo(encryptedCode);

                repository.save(validaEmail);
                return "Tarefa concluída";
            };

            // Envie a tarefa para o pool de threads e obtenha um Future
            // Future<String> future = executorService.submit(asyncTask);
            executorService.submit(asyncTask);
            return ResponseEntity.ok("Código enviado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new String("Erro ao envar o codigo\n\n" + e.getMessage()));
        }
    }

    @PostMapping("/validaCodigo")
    public ResponseEntity<String> validaCodigoRecebido(@RequestBody @Valid DadosValidaEmail dados)
            throws InterruptedException {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        var codigoBancoDeDados = repository.findByEmail(dados.emailDestinatario());

        for (ValidaEmail item : codigoBancoDeDados) {
            if (encoder.matches(dados.codigo(), item.getCodigo())) {
                System.out.println("achou o codigo no banco de dados");
                return ResponseEntity.ok("Código válido");
            }
        }
        System.out.println("Código inválido");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Código inválido"));
    }

    @PostMapping("/emailjacadastrado")
    public ResponseEntity<String> verificaEmailJaCadastrado(@RequestBody @Valid DadosValidaEmail dados) {

        if (this.userRepository.findByLoginIgnoreCase(dados.emailDestinatario()) != null)
            return ResponseEntity.badRequest().body("Esse E-mail já está sendo utilizado em outro cadastro");

        return ResponseEntity.ok().build();
    }
}
