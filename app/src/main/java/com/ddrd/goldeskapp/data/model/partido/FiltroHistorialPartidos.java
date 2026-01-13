package com.ddrd.goldeskapp.data.model.partido;

public class FiltroHistorialPartidos {

    private Integer idTorneo;
    private String nombreEquipo;
    private String fechaInicio;
    private String fechaFin;

    public FiltroHistorialPartidos(Integer idTorneo, String nombreEquipo, String fechainicio, String fechafin) {
        this.idTorneo = idTorneo;
        this.nombreEquipo = nombreEquipo;
        fechaInicio = fechainicio;
        fechaFin = fechafin;
    }

    @Override
    public String toString() {
        return "FiltroHistorialPartidos{" +
                "idTorneo=" + idTorneo +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                '}';
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}
