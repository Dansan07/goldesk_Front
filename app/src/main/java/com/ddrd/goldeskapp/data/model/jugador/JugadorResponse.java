package com.ddrd.goldeskapp.data.model.jugador;

public class JugadorResponse {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private Integer idJugador;
    private Integer idTorneoEquipoJugador;
    private String urlFoto;
    private Boolean esDelegado;

    public JugadorResponse() {
    }

    public JugadorResponse(String cedula, String nombre, String apellidos, String telefono, String email, Integer idJugador, Integer idTorneoEquipoJugador, String urlFoto, Boolean esDelegado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.idJugador = idJugador;
        this.idTorneoEquipoJugador = idTorneoEquipoJugador;
        this.urlFoto = urlFoto;
        this.esDelegado = esDelegado;
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

    public Integer getIdJugador() {
        return idJugador;
    }

    public Integer getIdTorneoEquipoJugador() {
        return idTorneoEquipoJugador;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Boolean getEsDelegado() {
        return esDelegado;
    }
}
