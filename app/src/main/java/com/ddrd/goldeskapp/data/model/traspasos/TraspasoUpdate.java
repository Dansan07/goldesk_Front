package com.ddrd.goldeskapp.data.model.traspasos;

public class TraspasoUpdate {

    private Integer idTraspaso;
    private String estado;
    private String observaciones;

    public TraspasoUpdate() {
    }

    public TraspasoUpdate(Integer idTraspaso, String estado, String observaciones) {
        this.idTraspaso = idTraspaso;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Integer getIdTraspaso() {
        return idTraspaso;
    }

    public String getEstado() {
        return estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setIdTraspaso(Integer idTraspaso) {
        this.idTraspaso = idTraspaso;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
