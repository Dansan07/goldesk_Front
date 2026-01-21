package com.ddrd.goldeskapp.data.model.tarjeta;

public class TarjetasResponse {

    private Integer idTarjeta;
    private String tipoTarjeta;
    private String nombreJugador;
    private Double valorTarjeta;
    private String motivo;
    private String periodoPartido;
    private String tiempoEvento;

    public TarjetasResponse(Integer idTarjeta, String tipoTarjeta, String nombreJugador, Double valorTarjeta, String motivo, String periodoPartido, String tiempoEvento) {
        this.idTarjeta = idTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.nombreJugador = nombreJugador;
        this.valorTarjeta = valorTarjeta;
        this.motivo = motivo;
        this.periodoPartido = periodoPartido;
        this.tiempoEvento = tiempoEvento;
    }

    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }
}
