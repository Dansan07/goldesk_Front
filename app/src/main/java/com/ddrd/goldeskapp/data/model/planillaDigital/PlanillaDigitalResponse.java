package com.ddrd.goldeskapp.data.model.planillaDigital;

import com.ddrd.goldeskapp.data.model.jugador.JugadorPlanillaResponse;

import java.util.List;

public class PlanillaDigitalResponse {
    // Campos básicos del partido
    private Integer idPartido;
    private Integer idTorneo;
    private String nombreTorneo;
    private String fechaPartido;
    private String horaPartido;
    private String nombreCancha;
    private Integer golesLocal;
    private Integer golesVisitante;
    private Integer penalesLocal;
    private Integer penalesVisitante;
    private String faseTorneo;
    private String estado;

    // Información del equipo local
    private Integer idEquipoLocal;
    private String nombreEquipoLocal;
    private List<JugadorPlanillaResponse> jugadoresLocal;
    private Double pagoArbitrajeLocal;
    private Boolean arbPagadoLocal;

    // Información del equipo visitante
    private Integer idEquipoVisitante;
    private String nombreEquipoVisitante;
    private List<JugadorPlanillaResponse> jugadoresVisitante;
    private Double pagoArbitrajeVisitante;
    private Boolean arbPagadoVisitante;

    public PlanillaDigitalResponse() {
    }

    public PlanillaDigitalResponse(Integer idPartido, Integer idTorneo, String nombreTorneo, String fechaPartido, String horaPartido, String nombreCancha, Integer golesLocal, Integer golesVisitante, Integer penalesLocal, Integer penalesVisitante, String faseTorneo, String estado, Integer idEquipoLocal, String nombreEquipoLocal, List<JugadorPlanillaResponse> jugadoresLocal, Double pagoArbitrajeLocal, Boolean arbPagadoLocal, Integer idEquipoVisitante, String nombreEquipoVisitante, List<JugadorPlanillaResponse> jugadoresVisitante, Double pagoArbitrajeVisitante, Boolean arbPagadoVisitante) {
        this.idPartido = idPartido;
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.fechaPartido = fechaPartido;
        this.horaPartido = horaPartido;
        this.nombreCancha = nombreCancha;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.penalesLocal = penalesLocal;
        this.penalesVisitante = penalesVisitante;
        this.faseTorneo = faseTorneo;
        this.estado = estado;
        this.idEquipoLocal = idEquipoLocal;
        this.nombreEquipoLocal = nombreEquipoLocal;
        this.jugadoresLocal = jugadoresLocal;
        this.pagoArbitrajeLocal = pagoArbitrajeLocal;
        this.arbPagadoLocal = arbPagadoLocal;
        this.idEquipoVisitante = idEquipoVisitante;
        this.nombreEquipoVisitante = nombreEquipoVisitante;
        this.jugadoresVisitante = jugadoresVisitante;
        this.pagoArbitrajeVisitante = pagoArbitrajeVisitante;
        this.arbPagadoVisitante = arbPagadoVisitante;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public String getFechaPartido() {
        return fechaPartido;
    }

    public String getHoraPartido() {
        return horaPartido;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public Integer getPenalesLocal() {
        return penalesLocal;
    }

    public Integer getPenalesVisitante() {
        return penalesVisitante;
    }

    public String getFaseTorneo() {
        return faseTorneo;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getIdEquipoLocal() {
        return idEquipoLocal;
    }

    public String getNombreEquipoLocal() {
        return nombreEquipoLocal;
    }

    public List<JugadorPlanillaResponse> getJugadoresLocal() {
        return jugadoresLocal;
    }

    public Double getPagoArbitrajeLocal() {
        return pagoArbitrajeLocal;
    }

    public Boolean getArbPagadoLocal() {
        return arbPagadoLocal;
    }

    public Integer getIdEquipoVisitante() {
        return idEquipoVisitante;
    }

    public String getNombreEquipoVisitante() {
        return nombreEquipoVisitante;
    }

    public List<JugadorPlanillaResponse> getJugadoresVisitante() {
        return jugadoresVisitante;
    }

    public Double getPagoArbitrajeVisitante() {
        return pagoArbitrajeVisitante;
    }

    public Boolean getArbPagadoVisitante() {
        return arbPagadoVisitante;
    }
}
