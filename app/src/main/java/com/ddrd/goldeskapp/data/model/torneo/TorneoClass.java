package com.ddrd.goldeskapp.data.model.torneo;

public class TorneoClass {

    protected Integer idTorneo;
    protected String cedulaOrganizador;
    protected String nombreTorneo;
    protected double valorAmarilla;
    protected double valorAzul;
    protected double valorRoja;
    protected double valorArbitraje;
    protected double valorInscripcion;
    protected double valorBalonPetos;
    protected Integer partidosInicial;
    protected String categoriaTorneo;
    protected Boolean activo;

    public TorneoClass(Integer idTorneo, String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo, Boolean activo) {
        this.idTorneo = idTorneo;
        this.cedulaOrganizador = cedulaOrganizador;
        this.nombreTorneo = nombreTorneo;
        this.valorAmarilla = valorAmarilla;
        this.valorAzul = valorAzul;
        this.valorRoja = valorRoja;
        this.valorArbitraje = valorArbitraje;
        this.valorInscripcion = valorInscripcion;
        this.valorBalonPetos = valorBalonPetos;
        this.partidosInicial = partidosInicial;
        this.categoriaTorneo = categoriaTorneo;
        this.activo = activo;
    }
    public TorneoClass(Integer idTorneo, String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo) {
        this.idTorneo = idTorneo;
        this.cedulaOrganizador = cedulaOrganizador;
        this.nombreTorneo = nombreTorneo;
        this.valorAmarilla = valorAmarilla;
        this.valorAzul = valorAzul;
        this.valorRoja = valorRoja;
        this.valorArbitraje = valorArbitraje;
        this.valorInscripcion = valorInscripcion;
        this.valorBalonPetos = valorBalonPetos;
        this.partidosInicial = partidosInicial;
        this.categoriaTorneo = categoriaTorneo;
    }

    public TorneoClass(String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo, Boolean activo) {
        this.cedulaOrganizador = cedulaOrganizador;
        this.nombreTorneo = nombreTorneo;
        this.valorAmarilla = valorAmarilla;
        this.valorAzul = valorAzul;
        this.valorRoja = valorRoja;
        this.valorArbitraje = valorArbitraje;
        this.valorInscripcion = valorInscripcion;
        this.valorBalonPetos = valorBalonPetos;
        this.partidosInicial = partidosInicial;
        this.categoriaTorneo = categoriaTorneo;
        this.activo = activo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getCedulaOrganizador() {
        return cedulaOrganizador;
    }

    public void setCedulaOrganizador(String cedulaOrganizador) {
        this.cedulaOrganizador = cedulaOrganizador;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public double getValorAmarilla() {
        return valorAmarilla;
    }

    public void setValorAmarilla(double valorAmarilla) {
        this.valorAmarilla = valorAmarilla;
    }

    public double getValorAzul() {
        return valorAzul;
    }

    public void setValorAzul(double valorAzul) {
        this.valorAzul = valorAzul;
    }

    public double getValorRoja() {
        return valorRoja;
    }

    public void setValorRoja(double valorRoja) {
        this.valorRoja = valorRoja;
    }

    public double getValorArbitraje() {
        return valorArbitraje;
    }

    public void setValorArbitraje(double valorArbitraje) {
        this.valorArbitraje = valorArbitraje;
    }

    public double getValorInscripcion() {
        return valorInscripcion;
    }

    public void setValorInscripcion(double valorInscripcion) {
        this.valorInscripcion = valorInscripcion;
    }

    public double getValorBalonPetos() {
        return valorBalonPetos;
    }

    public void setValorBalonPetos(double valorBalonPetos) {
        this.valorBalonPetos = valorBalonPetos;
    }

    public Integer getPartidosInicial() {
        return partidosInicial;
    }

    public void setPartidosInicial(Integer partidosInicial) {
        this.partidosInicial = partidosInicial;
    }

    public String getCategoriaTorneo() {
        return categoriaTorneo;
    }

    public void setCategoriaTorneo(String categoriaTorneo) {
        this.categoriaTorneo = categoriaTorneo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
