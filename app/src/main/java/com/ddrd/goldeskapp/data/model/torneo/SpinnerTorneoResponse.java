package com.ddrd.goldeskapp.data.model.torneo;

import com.google.gson.annotations.SerializedName;

public class SpinnerTorneoResponse {

    @SerializedName("idTorneo")
    private Integer idTorneo;
    @SerializedName("nombreTorneo")
    private String nombreTorneo;

    public SpinnerTorneoResponse(Integer idTorneo, String nombreTorneo) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
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
}
