package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TablaGoleadoresApiService;
import com.ddrd.goldeskapp.data.model.tablaGoleadores.TablaGoleadoresResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablaGoleadoresRepository {

    private final TablaGoleadoresApiService tablaGoleadoresApiService;

    public TablaGoleadoresRepository(Context context) {
        this.tablaGoleadoresApiService = ApiClient.getClient(context).create(TablaGoleadoresApiService.class);
    }

    public void obtenerTablaGoleadores(Long idTorneo, GoleadoresCallback callback) {
        tablaGoleadoresApiService.obtenerTablaGoleadores(idTorneo).enqueue(new Callback<List<TablaGoleadoresResponse>>() {
            @Override
            public void onResponse(Call<List<TablaGoleadoresResponse>> call, Response<List<TablaGoleadoresResponse>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }else {
                    try {
                        String mensajeError = response.errorBody().string();
                        callback.onError(mensajeError);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<TablaGoleadoresResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface GoleadoresCallback{
        void onSuccess(List<TablaGoleadoresResponse> response);
        void onError(String mensaje);
    }
}
