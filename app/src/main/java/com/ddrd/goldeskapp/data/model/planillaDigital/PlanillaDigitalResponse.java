package com.ddrd.goldeskapp.data.model.planillaDigital;

import com.ddrd.goldeskapp.data.model.jugador.JugadorPlanillaResponse;

import java.util.List;

public class PlanillaDigitalResponse {
    // Campos básicos del partido
    private Integer idPartido;
    private Integer idTorneo;
    private String nombreTorneo;
    private Double ArbitrajeTotal;
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
    private Integer idPagoArbLocal;
    private Double pagoArbitrajeLocal;
    private Boolean arbPagadoLocal;

    // Información del equipo visitante
    private Integer idEquipoVisitante;
    private String nombreEquipoVisitante;
    private List<JugadorPlanillaResponse> jugadoresVisitante;
    private Integer idPagoArbVisitante;
    private Double pagoArbitrajeVisitante;
    private Boolean arbPagadoVisitante;

    public PlanillaDigitalResponse() {
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

    public Double getArbitrajeTotal() {
        return ArbitrajeTotal;
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

    public Integer getIdPagoArbLocal() {
        return idPagoArbLocal;
    }

    public Integer getIdPagoArbVisitante() {
        return idPagoArbVisitante;
    }
}
