package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.AuthApiService;
import com.ddrd.goldeskapp.data.model.login.LoginCodigoResponse;
import com.ddrd.goldeskapp.data.model.login.LoginOrganizadorResponse;
import com.ddrd.goldeskapp.util.TokenManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final AuthApiService apiService;
    private final TokenManager tokenManager;

    //Recibe Context para inicializar internamente
    public AuthRepository(Context context) {
        this.apiService = ApiClient.getClient(context).create(AuthApiService.class);
        this.tokenManager = new TokenManager(context);
    }

    public void loginPorCodigo(String codigo, final AuthCallback<LoginCodigoResponse> callback) {
        Map<String, String> body = new HashMap<>();
        body.put("codigo", codigo);

        apiService.loginPorCodigo(body).enqueue(new Callback<LoginCodigoResponse>() {
            @Override
            public void onResponse(Call<LoginCodigoResponse> call, Response<LoginCodigoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Usamos tu TokenManager existente para guardar todo
                    tokenManager.saveToken(response.body().getToken());
                    tokenManager.saveRole(response.body().getRol());
                    tokenManager.saveNombre(response.body().getNombre());
                    tokenManager.saveCodigo(response.body().getCodigo());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Código no válido");
                }
            }

            @Override
            public void onFailure(Call<LoginCodigoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void loginOrganizador(String email, String password, final AuthCallback<LoginOrganizadorResponse> callback) {
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("email", email);
        credenciales.put("password", password);

        apiService.loginOrganizador(credenciales).enqueue(new Callback<LoginOrganizadorResponse>() {
            @Override
            public void onResponse(Call<LoginOrganizadorResponse> call, Response<LoginOrganizadorResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginOrganizadorResponse res = response.body();
                    // 1. Guardamos el Token para el interceptor
                    tokenManager.saveToken(res.getToken());

                    // 2. Extraemos y guardamos los datos del objeto anidado
                    if (res.getPerfil() != null) {
                        tokenManager.saveNombre(res.getPerfil().getNombre());
                        tokenManager.saveRole(res.getPerfil().getRol());
                        tokenManager.saveCodigo(res.getPerfil().getCedula());
                    }
                    // Usamos un objeto genérico para el callback o manejamos la respuesta según necesites
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Email o contraseña incorrectos");
                }
            }
            @Override
            public void onFailure(Call<LoginOrganizadorResponse> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public void recuperarContrasenaOrg(String email, final AuthCallback<Void> callback){
        apiService.recuperarContrasena(email).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(null);
                }else {
                    callback.onError("No se pudo procesar la solicitud");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }



    public interface AuthCallback<T> {
        void onSuccess(T response);
        void onError(String error);
    }
}
