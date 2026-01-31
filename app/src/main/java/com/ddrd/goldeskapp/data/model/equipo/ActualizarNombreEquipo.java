package com.ddrd.goldeskapp.data.model.equipo;

public class ActualizarNombreEquipo {

    //esta clase actualiza el nombre personalizado del equipo,
    // según se participación en un determinado Torneo

    private Integer idTorneoEquipo;
    private String nombrePersonalizado;

    public ActualizarNombreEquipo(Integer idTorneoEquipo, String nombrePersonalizado) {
        this.idTorneoEquipo = idTorneoEquipo;
        this.nombrePersonalizado = nombrePersonalizado;
    }

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }
}
