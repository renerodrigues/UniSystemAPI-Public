package com.uniSystemAPI.domain.usuario;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMINISTRADOR_DE_LOJA("adinistrador_de_loja"),
    CONSUMIDOR("consumidor"),
    ENTREGADOR("entragador"),
    VENDEDOR("vendedor");

    private String role;

    UserRole(String role){
        this.role = role;
    }
}
