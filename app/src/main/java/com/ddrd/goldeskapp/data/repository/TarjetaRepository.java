package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TarjetaApiService;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarjetaRepository {

    private final TarjetaApiService tarjetaApiService;

    public TarjetaRepository(Context context) {
        this.tarjetaApiService = ApiClient.getClient(context).create(TarjetaApiService.class);
    }

    public void registrarTarjeta(TarjetaCreate tarjetaCreate, TarjetaCallback callback){
        tarjetaApiService.registrarTarjeta(tarjetaCreate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    callback.onSuccess();
                } else {
                    callback.onError("Error al registrar tarjeta");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Error al registrar tarjeta");
            }
        });

    }

    public interface TarjetaCallback{
        void onSuccess();
        void onError(String mensaje);
    }

}
