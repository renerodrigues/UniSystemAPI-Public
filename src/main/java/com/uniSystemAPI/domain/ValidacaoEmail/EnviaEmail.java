package com.uniSystemAPI.domain.ValidacaoEmail;

import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Transport;

public class EnviaEmail {
    // senha app gmail -> lzwf ropk ctfe lggy

    public static String envia(String destinatario) {

        // Configurações do servidor de email
        String host = "smtp.gmail.com"; // Substitua pelo seu servidor SMTP
        String port = "587"; // Porta SMTP para envio seguro (587 é comum)
        String username = "renerodriguessantana@gmail.com"; // Seu endereço de email
        String password = "lzwf ropk ctfe lggy"; // Sua senha de email

        // Propriedades para configuração
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Crie uma sessão de email com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String codigo = gerarCodigoAleatorio(8);
        try {

            // Crie uma mensagem de email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de Verificação");
            message.setText("Seu código de validação é: " + codigo);

            // Envie o email
            Transport.send(message);

            System.out.println("Email enviado com sucesso!");

        } catch (MessagingException e) {
            System.out.println("Falha ao enviar o email.");
            return "";
        }
        return codigo;
    }

    public static String gerarCodigoAleatorio(int comprimento) {
        StringBuilder codigoAleatorio = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < comprimento; i++) {
            // valor ASCII aleatório dentro do intervalo (letras maiúsculas e números)
            int valorAscii = random.nextInt(36) + 48; // 48 a 83 em ASCII (0-9, A-Z)
            if (valorAscii > 57) {
                valorAscii += 7; // Para pular os valores não numéricos (58-64)
            }
            char caractere = (char) valorAscii;
            codigoAleatorio.append(caractere);
        }

        return codigoAleatorio.toString();
    }

}
