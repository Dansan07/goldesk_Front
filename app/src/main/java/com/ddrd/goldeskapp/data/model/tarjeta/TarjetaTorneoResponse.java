package com.ddrd.goldeskapp.data.model.tarjeta;

import com.google.gson.annotations.SerializedName;
public class TarjetaTorneoResponse {

    @SerializedName("idTarjeta")
    private Integer idTarjeta;

    @SerializedName("tipoTarjeta")
    private String tipoTarjeta;

    @SerializedName("valorTarjeta")
    private Double valorTarjeta;

    @SerializedName("motivoTarjeta")
    private String motivoTarjeta;

    @SerializedName("nombreJugador")
    private String nombreJugador;

    @SerializedName("apellidosJugador")
    private String apellidosJugador;

    @SerializedName("nombreEquipo")
    private String nombreEquipo;

    @SerializedName("montoAbonado")
    private Double montoAbonado;

    @SerializedName("saldoPendiente")
    private Double saldoPendiente;

    @SerializedName("estadoPago")
    private String estadoPago;

    @SerializedName("nombreCompletoJugador")
    private String nombreCompletoJugador;

    // Constructor vacío requerido por GSON
    public TarjetaTorneoResponse() {}

    // Getters
    public Integer getIdTarjeta() { return idTarjeta; }
    public String getTipoTarjeta() { return tipoTarjeta; }
    public Double getValorTarjeta() { return valorTarjeta; }
    public String getMotivoTarjeta() { return motivoTarjeta; }
    public String getNombreJugador() { return nombreJugador; }
    public String getApellidosJugador() { return apellidosJugador; }
    public String getNombreEquipo() { return nombreEquipo; }
    public Double getMontoAbonado() { return montoAbonado; }
    public Double getSaldoPendiente() { return saldoPendiente; }
    public String getEstadoPago() { return estadoPago; }
    public String getNombreCompletoJugador() { return nombreCompletoJugador; }
}
