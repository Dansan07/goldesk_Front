package com.ddrd.goldeskapp.data.model.torneo;

public class TorneoUpdate extends TorneoClass{
    public TorneoUpdate(Integer idTorneo, String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo) {
        super(idTorneo, cedulaOrganizador, nombreTorneo, valorAmarilla, valorAzul, valorRoja, valorArbitraje, valorInscripcion, valorBalonPetos, partidosInicial, categoriaTorneo);
    }
}
