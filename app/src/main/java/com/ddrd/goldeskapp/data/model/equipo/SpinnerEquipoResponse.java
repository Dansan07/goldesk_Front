package com.ddrd.goldeskapp.data.model.equipo;

import androidx.annotation.NonNull;

public class SpinnerEquipoResponse {

    private Integer idEquipo;
    private String nombreEquipo;

    public SpinnerEquipoResponse(Integer idEquipo, String nombreEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }

    @NonNull
    @Override
    public String toString() {
        return nombreEquipo;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }
}
