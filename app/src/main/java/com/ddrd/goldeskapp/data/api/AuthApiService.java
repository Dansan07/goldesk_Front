package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.login.LoginCodigoResponse;
import com.ddrd.goldeskapp.data.model.login.LoginOrganizadorResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApiService {

    @POST("api/auth/acceso-codigo")
    Call<LoginCodigoResponse> loginPorCodigo(@Body Map<String, String> body);

    @POST("/api/auth/login-organizador")
    Call<LoginOrganizadorResponse> loginOrganizador(@Body Map<String, String> credenciales);

    @PATCH("/api/auth/actualizar-password/{email}")
    Call<Void> recuperarContrasena(@Path("email") String email);

}
