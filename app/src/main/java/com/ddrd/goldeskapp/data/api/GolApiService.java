package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.gol.GolCreate;
import com.ddrd.goldeskapp.data.model.gol.GolResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GolApiService {

    @POST("api/goles/registrar-gol")
    Call<Void> registrargol(@Body GolCreate golCreate);

    @GET("api/goles/buscar-x-jugador")
    Call<List<GolResponse>> buscarGolesPorJugador(@Query("idParticipacion") Integer idParticipacion);


}
