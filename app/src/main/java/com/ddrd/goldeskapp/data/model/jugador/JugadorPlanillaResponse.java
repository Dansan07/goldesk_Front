package com.ddrd.goldeskapp.data.model.jugador;

public class JugadorPlanillaResponse {

    // Si el partido no ha iniciado, este será el id_torneo_equipos_jugadores (idTEJ)
    // Si el partido ya inició, este será el id_participacion
    private Integer idReferencia;
    private String nombreJugador;
    private String dorsal;
    private Integer cantGoles;
    private Integer cantAmarillas;
    private Integer cantRojas;
    private Integer cantAzules;


    public JugadorPlanillaResponse() {
    }

    public JugadorPlanillaResponse(Integer idReferencia, String nombreJugador, String dorsal) {
        this.idReferencia = idReferencia;
        this.nombreJugador = nombreJugador;
        this.dorsal = dorsal;
    }

    public JugadorPlanillaResponse(Integer idReferencia, String nombreJugador) {
        this.idReferencia = idReferencia;
        this.nombreJugador = nombreJugador;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getDorsal() {
        return dorsal;
    }

    public Integer getCantGoles() {
        return cantGoles;
    }

    public Integer getCantAmarillas() {
        return cantAmarillas;
    }

    public Integer getCantRojas() {
        return cantRojas;
    }

    public Integer getCantAzules() {
        return cantAzules;
    }
}
