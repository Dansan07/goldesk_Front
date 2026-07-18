package com.ddrd.goldeskapp.data.model.torneo;

public class ResumenInscripcion {
    private Double valorInscripcionPorEquipo;
    private Long cantidadEquipos;
    private Double totalARecoger;
    private Double totalAbonado;
    private Double saldoPendienteGeneral;

    public ResumenInscripcion(Double valorInscripcionPorEquipo, Long cantidadEquipos, Double totalARecoger, Double totalAbonado, Double saldoPendienteGeneral) {
        this.valorInscripcionPorEquipo = valorInscripcionPorEquipo;
        this.cantidadEquipos = cantidadEquipos;
        this.totalARecoger = totalARecoger;
        this.totalAbonado = totalAbonado;
        this.saldoPendienteGeneral = saldoPendienteGeneral;
    }

    public Double getValorInscripcionPorEquipo() {
        return valorInscripcionPorEquipo;
    }

    public Long getCantidadEquipos() {
        return cantidadEquipos;
    }

    public Double getTotalARecoger() {
        return totalARecoger;
    }

    public Double getTotalAbonado() {
        return totalAbonado;
    }

    public Double getSaldoPendienteGeneral() {
        return saldoPendienteGeneral;
    }
}
