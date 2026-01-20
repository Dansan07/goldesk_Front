package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TarjetaApiService {

    @POST("api/tarjetas/registrar-tarjeta")
    Call<Void> registrarTarjeta(@Body TarjetaCreate tarjetaCreate);
}
