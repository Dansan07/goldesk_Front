package com.ddrd.goldeskapp.data.model.gol;

public class GolCreate {

    private Integer idParticipacion;
    private String periodoPartido;
    private String tiempoEvento;

    public GolCreate(Integer idParticipacion, String periodoPartido, String tiempoEvento) {
        this.idParticipacion = idParticipacion;
        this.periodoPartido = periodoPartido;
        this.tiempoEvento = tiempoEvento;
    }

    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }

    public void setIdParticipacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public void setPeriodoPartido(String periodoPartido) {
        this.periodoPartido = periodoPartido;
    }

    public void setTiempoEvento(String tiempoEvento) {
        this.tiempoEvento = tiempoEvento;
    }
}
