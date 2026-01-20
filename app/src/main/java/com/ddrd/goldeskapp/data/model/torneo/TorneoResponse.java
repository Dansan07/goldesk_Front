package com.ddrd.goldeskapp.data.model.torneo;

public class TorneoResponse {

    private Integer idTorneo;
    private String cedulaOrganizador;
    private String nombreTorneo;
    private double valorAmarilla;
    private double valorAzul;
    private double valorRoja;
    private double valorArbitraje;
    private double valorInscripcion;
    private double valorBalonPetos;
    private Integer partidosInicial;
    private String categoriaTorneo;
    private Boolean activo;

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public String getCedulaOrganizador() {
        return cedulaOrganizador;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public double getValorAmarilla() {
        return valorAmarilla;
    }

    public double getValorAzul() {
        return valorAzul;
    }

    public double getValorRoja() {
        return valorRoja;
    }

    public double getValorArbitraje() {
        return valorArbitraje;
    }

    public double getValorInscripcion() {
        return valorInscripcion;
    }

    public double getValorBalonPetos() {
        return valorBalonPetos;
    }

    public Integer getPartidosInicial() {
        return partidosInicial;
    }

    public String getCategoriaTorneo() {
        return categoriaTorneo;
    }

    public Boolean getActivo() {
        return activo;
    }
}
