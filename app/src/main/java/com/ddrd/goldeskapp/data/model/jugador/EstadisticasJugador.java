package com.ddrd.goldeskapp.data.model.jugador;

public class EstadisticasJugador {
    private Integer idTorneoEquipoJugador;
    private String nombreCompleto;
    private Long partidosJugados;
    private Long golesAnotados;
    private Long amarillas;
    private Long azules;
    private Long rojas;

    public Integer getIdTorneoEquipoJugador() {
        return idTorneoEquipoJugador;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Long getPartidosJugados() {
        return partidosJugados;
    }

    public Long getGolesAnotados() {
        return golesAnotados;
    }

    public Long getAmarillas() {
        return amarillas;
    }

    public Long getAzules() {
        return azules;
    }

    public Long getRojas() {
        return rojas;
    }
}
