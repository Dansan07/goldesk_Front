package com.ddrd.goldeskapp.data.model.traspasos;

public class TraspasoResponse {

    private Integer idTraspaso;
    private String nombreJugador;
    private String cedulaJugador;
    private String equipoOrigen;
    private String equipoDestino;
    private String asunto;
    private String estado;
    private String fechaSolicitud;
    private String fechaRespuesta;

    public TraspasoResponse(Integer idTraspaso, String nombreJugador, String cedulaJugador, String equipoOrigen, String equipoDestino, String asunto, String estado, String fechaSolicitud, String fechaRespuesta) {
        this.idTraspaso = idTraspaso;
        this.nombreJugador = nombreJugador;
        this.cedulaJugador = cedulaJugador;
        this.equipoOrigen = equipoOrigen;
        this.equipoDestino = equipoDestino;
        this.asunto = asunto;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaRespuesta = fechaRespuesta;
    }

    public Integer getIdTraspaso() {
        return idTraspaso;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getCedulaJugador() {
        return cedulaJugador;
    }

    public String getEquipoOrigen() {
        return equipoOrigen;
    }

    public String getEquipoDestino() {
        return equipoDestino;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getFechaRespuesta() {
        return fechaRespuesta;
    }
}
