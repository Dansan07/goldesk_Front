package com.ddrd.goldeskapp.data.model.pagosArbitraje;

public class PagoArbitrajeCreate {

    private Integer idPartido;
    private Integer idTorneoEquipo;
    private Double monto;
    private String observacion;

    public PagoArbitrajeCreate() {
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public void setIdTorneoEquipo(Integer idTorneoEquipo) {
        this.idTorneoEquipo = idTorneoEquipo;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
