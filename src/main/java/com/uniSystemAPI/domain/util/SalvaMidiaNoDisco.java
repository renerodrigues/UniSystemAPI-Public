package com.uniSystemAPI.domain.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class SalvaMidiaNoDisco {

    private static String nomeAplicacao = "uniSystemAPI";

    // @Override
    // public void setEnvironment(Environment environment) {
    // nomeAplicacao = environment.getProperty("spring.application.name");
    // System.out.println("nomeAplicacao:-> "+nomeAplicacao);
    // }

    public static String salvaImagemBase64NoDisco(String imagemBase64, String subDiretorio, String nomeImagem)
            throws IOException {

        Path pathArquivos = Paths.get(
                System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + nomeAplicacao
                        + System.getProperty("file.separator")
                        + subDiretorio);

        if (!Files.exists(pathArquivos)) {
            Files.createDirectories(pathArquivos);
        }

        byte[] imageBytes = Base64.getDecoder().decode(imagemBase64);

        // if (nomeImagem.contains(".jpg")) {
        // nomeImagem = nomeImagem.replace(".jpg", "");
        // }

        File file = new File(pathArquivos.toAbsolutePath().toString(), nomeImagem);
        // File file = new File(pathArquivos.toAbsolutePath().toString(), nomeImagem +
        // ".jpg");

        Path tempFile = Files.createFile(file.toPath());

        Files.write(tempFile, imageBytes, StandardOpenOption.CREATE);

        return tempFile.getFileName().toString();
    }

    public static String obtemCaminhoImagem(String subDiretorio, String nomeImagem) {
        try {
            String caminhoImagem = Path.of(
                    System.getProperty("user.home")
                            + System.getProperty("file.separator")
                            + nomeAplicacao
                            + System.getProperty("file.separator")
                            + subDiretorio
                            + System.getProperty("file.separator")
                            + nomeImagem)
                    .toString();

            return caminhoImagem;
        } catch (Exception e) {
            return null;
        }
    }

    public static String caminhoImagemNaoEncontrada() {
        // Path path = null;
        // final ResourceLoader resourceLoader = new ServletContextResourceLoader(null);
        // Resource resource = resourceLoader.getResource("classpath:" +
        // "image-not-found.jpg");
        // try {
        // path = Paths.get(resource.getURI());
        // } catch (IOException e) {

        // }
        String diretorio = "ImgNotFound";
        String nomeImagem = "ImgNotFound.jpg";
        try {

            Path pathArquivos = Paths.get(
                    System.getProperty("user.home")
                            + System.getProperty("file.separator")
                            + nomeAplicacao
                            + System.getProperty("file.separator")
                            + diretorio
                            + System.getProperty("file.separator")
                            + nomeImagem);

            if (!Files.exists(pathArquivos)) {
                salvaImagemBase64NoDisco(ImagemNotFoundBase64.imagemNotFoundBase64(), diretorio,
                        nomeImagem);
            }
             } catch (Exception e) {
            // TODO: handle exception
        }
        return obtemCaminhoImagem(diretorio, nomeImagem );
    }
    // image-not-found.jpg

    public static boolean apagarImagemDoDisco(String subDiretorio, String nomeImagem) {
        try {
            Path pathArquivos = Path.of(
                    System.getProperty("user.home")
                            + System.getProperty("file.separator")
                            + nomeAplicacao
                            + System.getProperty("file.separator")
                            + subDiretorio
                            + System.getProperty("file.separator")
                            + nomeImagem);

            if (Files.exists(pathArquivos))
                Files.delete(pathArquivos);

        } catch (IOException e) {
            System.out.println("Erro");
        }
        return true;
    }

}
