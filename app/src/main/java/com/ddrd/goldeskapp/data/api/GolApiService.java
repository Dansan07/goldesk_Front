package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.gol.GolCreate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GolApiService {

    @POST("api/goles/registrar-gol")
    Call<Void> registrargol(@Body GolCreate golCreate);


}
