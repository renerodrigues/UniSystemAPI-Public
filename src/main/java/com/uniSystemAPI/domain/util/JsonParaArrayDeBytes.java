package com.uniSystemAPI.domain.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParaArrayDeBytes {

    public byte[] converteJsonParaArrayDeBytes( String json) {
        byte[] byteArray = null;
        // Exemplo de JSON de array de bytes
     //   = "[72, 101, 108, 108, 111, 44, 32, 87, 111, 114, 108, 100]";

        try {
            // Inicializar o ObjectMapper do Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            // Desserializar o JSON em um array de bytes
            byteArray = objectMapper.readValue(json, byte[].class);

            // Imprimir o array de bytes
            for (byte b : byteArray) {
                System.out.print(b + " ");
            }

            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}