package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TorneoApiService {

    @GET("api/organizadores/{cedula}/torneos")
    Call<List<SpinnerTorneoResponse>> obtenerTorneosDelOrganizador(@Path("cedula") String cedula);
}
