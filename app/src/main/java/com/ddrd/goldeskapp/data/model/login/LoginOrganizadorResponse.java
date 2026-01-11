package com.ddrd.goldeskapp.data.model.login;

import com.ddrd.goldeskapp.data.model.organizador.Organizador;

public class LoginOrganizadorResponse {

    private String token;
    private Organizador perfil;

    public String getToken() {
        return token;
    }

    public Organizador getPerfil() {
        return perfil;
    }
}
