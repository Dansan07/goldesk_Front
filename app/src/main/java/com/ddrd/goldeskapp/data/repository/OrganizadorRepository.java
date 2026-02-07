package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.OrganizadorApiService;
import com.ddrd.goldeskapp.data.model.organizador.ActualizaDatosOrg;
import com.ddrd.goldeskapp.data.model.organizador.ActualizaPassOrg;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizadorRepository {

    private final OrganizadorApiService organizadorApiService;

    public OrganizadorRepository(Context context) {
        this.organizadorApiService = ApiClient.getClient(context).create(OrganizadorApiService.class);
    }

    public void actualizarContrasena(ActualizaPassOrg body, OrganizadorCallback<String> callback){
        organizadorApiService.actualizarContrasena(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String mensajeSucces = response.body().string();
                        callback.onSuccess(mensajeSucces);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }else {
                    try {
                        String error_message = response.errorBody().string();
                        callback.onError(error_message);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void actualizarDatos(ActualizaDatosOrg body, OrganizadorCallback<String> callback){
        organizadorApiService.actualizarDatos(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String mensajeSucces = response.body().string();
                        callback.onSuccess(mensajeSucces);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }else {
                    try {
                        String error_message = response.errorBody().string();
                        callback.onError(error_message);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface OrganizadorCallback<T>{
        void onSuccess(T response);
        void onError(String error);
    }
}
