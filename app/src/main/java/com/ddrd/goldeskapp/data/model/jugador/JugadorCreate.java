package com.ddrd.goldeskapp.data.model.jugador;

public class JugadorCreate {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private Integer idJugador;
    private Integer idTorneoEquipoJugador;
    private String urlFoto;
    private Boolean esDelegado;

    public JugadorCreate(){
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

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public void setIdTorneoEquipoJugador(Integer idTorneoEquipoJugador) {
        this.idTorneoEquipoJugador = idTorneoEquipoJugador;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public void setEsDelegado(Boolean esDelegado) {
        this.esDelegado = esDelegado;
    }
}
