package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidoSave;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PartidoApiService {

    @POST("api/partidos/programar-partido")
    Call<PartidoResponseDuplicate> programarPartido(@Body PartidoSave partidoSave);
}
