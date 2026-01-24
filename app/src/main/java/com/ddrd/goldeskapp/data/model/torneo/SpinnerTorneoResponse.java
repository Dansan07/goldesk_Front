package com.ddrd.goldeskapp.data.model.torneo;

import com.google.gson.annotations.SerializedName;

public class SpinnerTorneoResponse {

    @SerializedName("idTorneo")
    private Integer idTorneo;
    @SerializedName("nombreTorneo")
    private String nombreTorneo;
    @SerializedName("partidosInicial")
    private Integer cantPartidos;

    public SpinnerTorneoResponse(Integer idTorneo, String nombreTorneo, Integer cantPartidos) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.cantPartidos = cantPartidos;
    }

    @Override
    public String toString() {
        return nombreTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public Integer getCantPartidos() {
        return cantPartidos;
    }
}
