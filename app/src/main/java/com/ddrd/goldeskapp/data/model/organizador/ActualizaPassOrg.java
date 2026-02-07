package com.ddrd.goldeskapp.data.model.organizador;

public class ActualizaPassOrg {

    private String email;
    private String pass;

    public ActualizaPassOrg(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
