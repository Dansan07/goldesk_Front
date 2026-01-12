package com.ddrd.goldeskapp.data.model.organizador;

public class OrganizadorResponse {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String passHashOrg;
    private String codigoInvitado;
    private String rol;
    private boolean activo;
    private String urlLogoOrg;

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getPassHashOrg() {
        return passHashOrg;
    }

    public String getCodigoInvitado() {
        return codigoInvitado;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getUrlLogoOrg() {
        return urlLogoOrg;
    }
}
