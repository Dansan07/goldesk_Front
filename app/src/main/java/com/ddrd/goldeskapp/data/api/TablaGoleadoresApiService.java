package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.tablaGoleadores.TablaGoleadoresResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TablaGoleadoresApiService {

    @GET("api/goles/tabla-goleadores/{idTorneo}")
    Call<List<TablaGoleadoresResponse>> obtenerTablaGoleadores(@Path("idTorneo") Long idTorneo);
}
