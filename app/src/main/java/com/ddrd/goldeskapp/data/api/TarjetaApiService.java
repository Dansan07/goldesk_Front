package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TarjetaApiService {

    @POST("api/tarjetas/registrar-tarjeta")
    Call<Void> registrarTarjeta(@Body TarjetaCreate tarjetaCreate);

    @GET("api/tarjetas/buscar-x-jugador")
    Call<List<TarjetasResponse>> buscarTarjetasPorJugador(
            @Query("idParticipacion") Integer idParticipacion,
            @Query("tipoTarjeta") String tipoTarjeta
    );
}
