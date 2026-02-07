package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.traspasos.TraspasoCreate;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoUpdate;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TraspasosApiService {

    @POST("api/traspaso/crear")
    Call<ResponseBody> crearTraspaso(@Body TraspasoCreate traspaso);

    @GET("api/traspaso/mis-solicitudes")
    Call<List<TraspasoResponse>> listarSolicitudes(@Query(value = "estado") String estadoSolicitud);

    @PATCH("api/traspaso/rechazar")
    Call<ResponseBody> rechazarSolicitud(@Body TraspasoUpdate traspasoUpdate);

    @PATCH("api/traspaso/aprobar/{idTraspaso}")
    Call<ResponseBody> aprobarSolicitud(@Path("idTraspaso") Integer idTraspaso);



}
