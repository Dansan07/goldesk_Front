package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.organizador.ActualizaDatosOrg;
import com.ddrd.goldeskapp.data.model.organizador.ActualizaPassOrg;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;

public interface OrganizadorApiService {

    @PATCH("api/organizadores/actualizar-password")
    Call<ResponseBody> actualizarContrasena(@Body ActualizaPassOrg body);

    @PUT("api/organizadores/actualizar-datos")
    Call<ResponseBody> actualizarDatos(@Body ActualizaDatosOrg body);
}
