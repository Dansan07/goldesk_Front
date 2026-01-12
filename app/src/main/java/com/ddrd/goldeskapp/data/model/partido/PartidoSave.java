package com.ddrd.goldeskapp.data.model.partido;

import java.time.LocalDate;
import java.time.LocalTime;

public class PartidoSave {
    private Integer idTorneo;
    private Integer idEquipoLocal;
    private Integer idEquipoVisitante;
    private String fecha;
    private String hora;
    private String cancha;
    private String fase;
    private boolean confirmarDuplicado;

    public PartidoSave(Integer idTorneo, Integer idEquipoLocal, Integer idEquipoVisitante,
                       String fecha, String hora, String cancha, String fase, boolean confirmarDuplicado) {
        this.idTorneo = idTorneo;
        this.idEquipoLocal = idEquipoLocal;
        this.idEquipoVisitante = idEquipoVisitante;
        this.fecha = fecha;
        this.hora = hora;
        this.cancha = cancha;
        this.fase = fase;
        this.confirmarDuplicado = confirmarDuplicado;
    }

    @Override
    public String toString() {
        return "PartidoSave{" +
                "idTorneo=" + idTorneo +
                ", idEquipoLocal=" + idEquipoLocal +
                ", idEquipoVisitante=" + idEquipoVisitante +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", cancha='" + cancha + '\'' +
                ", fase='" + fase + '\'' +
                ", confirmarDuplicado=" + confirmarDuplicado +
                '}';
    }
}
