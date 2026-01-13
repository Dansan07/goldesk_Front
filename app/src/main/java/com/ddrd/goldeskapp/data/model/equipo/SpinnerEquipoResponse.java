package com.ddrd.goldeskapp.data.model.equipo;

import androidx.annotation.NonNull;

public class SpinnerEquipoResponse {

    private Integer idTorneoEquipo;
    private String nombreEquipo;

    public SpinnerEquipoResponse(Integer idTorneoEquipo, String nombreEquipo) {
        this.idTorneoEquipo = idTorneoEquipo;
        this.nombreEquipo = nombreEquipo;
    }

    @NonNull
    @Override
    public String toString() {
        return nombreEquipo;
    }

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }
}
