package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.GolApiService;
import com.ddrd.goldeskapp.data.model.gol.GolCreate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GolRepository {

    private final GolApiService golApiService;

    public GolRepository(Context context) {
        this.golApiService = ApiClient.getClient(context).create(GolApiService.class);
    }

    public void registrarGol(GolCreate golCreate, GolCallback callback) {
        golApiService.registrargol(golCreate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Error al registrar gol: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface GolCallback{
        void onSuccess();
        void onError(String mensaje);
    }
}
