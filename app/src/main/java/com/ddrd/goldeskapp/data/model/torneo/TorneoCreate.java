package com.ddrd.goldeskapp.data.model.torneo;

public class TorneoCreate extends TorneoClass{
    public TorneoCreate(String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo, Boolean activo) {
        super(cedulaOrganizador, nombreTorneo, valorAmarilla, valorAzul, valorRoja, valorArbitraje, valorInscripcion, valorBalonPetos, partidosInicial, categoriaTorneo, activo);
    }
}
