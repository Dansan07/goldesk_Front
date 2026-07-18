package com.ddrd.goldeskapp.data.model.tarjeta;

public class TarjetaCreate {

    private Integer idParticipacion;
    private Double valorTarjeta;
    private String tipoTarjeta;
    private String motivoTarjeta;
    private String periodoPartido;
    private String tiempoEvento;

    public TarjetaCreate(Integer idParticipacion, Double valorTarjeta, String tipoTarjeta, String motivoTarjeta, String periodoPartido, String tiempoEvento) {
        this.idParticipacion = idParticipacion;
        this.valorTarjeta = valorTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.motivoTarjeta = motivoTarjeta;
        this.periodoPartido = periodoPartido;
        this.tiempoEvento = tiempoEvento;
    }

    @Override
    public String toString() {
        return "TarjetaCreate{" +
                "idParticipacion=" + idParticipacion +
                ", valorTarjeta=" + valorTarjeta +
                ", tipoTarjeta='" + tipoTarjeta + '\'' +
                ", motivoTarjeta='" + motivoTarjeta + '\'' +
                ", periodoPartido='" + periodoPartido + '\'' +
                ", tiempoEvento='" + tiempoEvento + '\'' +
                '}';
    }

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public void setValorTarjeta(Double valorTarjeta) {
        this.valorTarjeta = valorTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getMotivoTarjeta() {
        return motivoTarjeta;
    }

    public void setMotivoTarjeta(String motivoTarjeta) {
        this.motivoTarjeta = motivoTarjeta;
    }

    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public void setPeriodoPartido(String periodoPartido) {
        this.periodoPartido = periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }

    public void setTiempoEvento(String tiempoEvento) {
        this.tiempoEvento = tiempoEvento;
    }
}
