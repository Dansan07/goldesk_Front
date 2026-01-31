package com.ddrd.goldeskapp.data.model.pagosArbitraje;

public class PagoArbitrajeUpdate {

    private Integer idPagoArbitraje;
    private Double monto;
    private String observacion;

    public PagoArbitrajeUpdate() {
    }

    public PagoArbitrajeUpdate(Integer idPagoArbitraje, Double monto, String observacion) {
        this.idPagoArbitraje = idPagoArbitraje;
        this.monto = monto;
        this.observacion = observacion;
    }
    public Integer getIdPagoArbitraje() {
        return idPagoArbitraje;
    }

    public void setIdPagoArbitraje(Integer idPagoArbitraje) {
        this.idPagoArbitraje = idPagoArbitraje;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
