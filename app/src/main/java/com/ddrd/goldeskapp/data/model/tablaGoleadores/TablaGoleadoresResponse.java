package com.ddrd.goldeskapp.data.model.tablaGoleadores;

public class TablaGoleadoresResponse {

    private String nombreJugador;
    private String nombreEquipo;
    private Long partidosJugados;
    private Long goles;

    public TablaGoleadoresResponse(String nombreJugador, String nombreEquipo, Long partidosJugados, Long goles) {
        this.nombreJugador = nombreJugador;
        this.nombreEquipo = nombreEquipo;
        this.partidosJugados = partidosJugados;
        this.goles = goles;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public Long getPartidosJugados() {
        return partidosJugados;
    }

    public Long getGoles() {
        return goles;
    }
}
