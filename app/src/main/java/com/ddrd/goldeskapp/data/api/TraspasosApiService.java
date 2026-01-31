package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.traspasos.TraspasoCreate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TraspasosApiService {

    @POST("api/traspaso/crear")
    Call<ResponseBody> crearTraspaso(@Body TraspasoCreate traspaso);

}
