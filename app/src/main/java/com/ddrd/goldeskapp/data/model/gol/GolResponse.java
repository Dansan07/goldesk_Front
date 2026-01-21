package com.ddrd.goldeskapp.data.model.gol;

public class GolResponse {
    private Integer idGol;
    private String periodoPartido;
    private String tiempoEvento;

    public GolResponse(Integer idGol, String periodoPartido, String tiempoEvento) {
        this.idGol = idGol;
        this.periodoPartido = periodoPartido;
        this.tiempoEvento = tiempoEvento;
    }

    public Integer getIdGol() {
        return idGol;
    }

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }
}
