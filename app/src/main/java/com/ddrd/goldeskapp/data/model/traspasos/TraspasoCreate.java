package com.ddrd.goldeskapp.data.model.traspasos;

public class TraspasoCreate {

    private Integer idJugador;
    private Integer idTorneoEquipoJugador;
    private Integer idTorneoEquipoSolicita; // El equipo que quiere al jugador
    private String asuntoTraspaso; // La razón o motivo

    public TraspasoCreate(Integer idJugador, Integer idTorneoEquipoJugador, Integer idTorneoEquipoSolicita, String asuntoTraspaso) {
        this.idJugador = idJugador;
        this.idTorneoEquipoJugador = idTorneoEquipoJugador;
        this.idTorneoEquipoSolicita = idTorneoEquipoSolicita;
        this.asuntoTraspaso = asuntoTraspaso;
    }
}
