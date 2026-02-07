package com.ddrd.goldeskapp.data.model.organizador;

public class OrganizadorResponse {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String codigoInvitado;
    private String rol;
    private boolean activo;
    private String urlLogoOrg;

    public OrganizadorResponse() {
    }

    public OrganizadorResponse(String cedula, String nombre, String apellidos, String telefono, String email, String codigoInvitado, String rol, boolean activo, String urlLogoOrg) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.codigoInvitado = codigoInvitado;
        this.rol = rol;
        this.activo = activo;
        this.urlLogoOrg = urlLogoOrg;
    }
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

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCodigoInvitado(String codigoInvitado) {
        this.codigoInvitado = codigoInvitado;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setUrlLogoOrg(String urlLogoOrg) {
        this.urlLogoOrg = urlLogoOrg;
    }
}
