package com.ddrd.goldeskapp.data.model.Inscripcion;

public class InscripcionTorneoResponse {
    private Integer idTorneoEquipos;
    private String nombreEquipo;
    private Double valorInscripcion;
    private Double montoAbonado;
    private Double saldoPendiente;
    private String estadoPago;

    public InscripcionTorneoResponse() {
    }

    public InscripcionTorneoResponse(Integer idTorneoEquipos, String nombreEquipo, Double valorInscripcion, Double montoAbonado, Double saldoPendiente, String estadoPago) {
        this.idTorneoEquipos = idTorneoEquipos;
        this.nombreEquipo = nombreEquipo;
        this.valorInscripcion = valorInscripcion;
        this.montoAbonado = montoAbonado;
        this.saldoPendiente = saldoPendiente;
        this.estadoPago = estadoPago;
    }
    public Integer getIdTorneoEquipos() {
        return idTorneoEquipos;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public Double getValorInscripcion() {
        return valorInscripcion;
    }

    public Double getMontoAbonado() {
        return montoAbonado;
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public String getEstadoPago() {
        return estadoPago;
    }
}
