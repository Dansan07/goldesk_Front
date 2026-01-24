package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.TablaPosiciones.TablaPosicionesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TablaPosicionesApiService {

    @GET("api/tabla-posiciones/{idTorneo}")
    Call<List<TablaPosicionesResponse>> obtenerTablaPosiciones(@Path("idTorneo") int idTorneo);
}
