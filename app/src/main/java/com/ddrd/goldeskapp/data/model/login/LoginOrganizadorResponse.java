package com.ddrd.goldeskapp.data.model.login;

import com.ddrd.goldeskapp.data.model.organizador.OrganizadorResponse;

public class LoginOrganizadorResponse {

    private String token;
    private OrganizadorResponse perfil;

    public String getToken() {
        return token;
    }

    public OrganizadorResponse getPerfil() {
        return perfil;
    }
}
