package com.ddrd.goldeskapp.data.model.Inscripcion;

import java.time.LocalDateTime;

public class AbonoDetalleInscripcion {
    private Integer idPagoInscripcion;
    private Double monto;
    private String fecha;

    public AbonoDetalleInscripcion(Integer idPagoInscripcion, Double monto, String fecha) {
        this.idPagoInscripcion = idPagoInscripcion;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdPagoInscripcion() {
        return idPagoInscripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }
}
