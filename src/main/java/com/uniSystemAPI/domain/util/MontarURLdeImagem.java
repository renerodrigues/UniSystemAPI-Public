package com.uniSystemAPI.domain.util;

public class MontarURLdeImagem {
    public static String montaURLImagemUsuario(String nomeFoto) {
        return ServerProperties.getServerAddress() + "/usuarios/imagem/" + nomeFoto;
      }
       public static String montaURLImagemEstabelecimento(String nomeFoto) {
        return ServerProperties.getServerAddress() + "/estabelecimento/imagem/" + nomeFoto;
      }
       public static String montaURLImagemProduto(String nomeFoto) {

        
        return ServerProperties.getServerAddress() + "/produtos/imagem/" + nomeFoto;
      }
}
