package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TablaPosicionesApiService;
import com.ddrd.goldeskapp.data.model.TablaPosiciones.TablaPosicionesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablaPosicionesRepository {

    private final TablaPosicionesApiService tablaPosicionesApiService;

    public TablaPosicionesRepository(Context context){
        this.tablaPosicionesApiService = ApiClient.getClient(context).create(TablaPosicionesApiService.class);
    }

    public void obtenerTablaPosiciones(Integer idTorneo, obtenerTablaCallback callback){
        tablaPosicionesApiService.obtenerTablaPosiciones(idTorneo).enqueue(new Callback<List<TablaPosicionesResponse>>() {
            @Override
            public void onResponse(Call<List<TablaPosicionesResponse>> call, Response<List<TablaPosicionesResponse>> response) {
                if (response.isSuccessful()){
                    if (response.body() == null){
                        callback.onNoContent();
                        return;
                    }
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener tabla de posiciones");
                }
            }
            @Override
            public void onFailure(Call<List<TablaPosicionesResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface obtenerTablaCallback{
        void onSuccess(List<TablaPosicionesResponse> response);
        void onNoContent();
        void onError(String mensaje);
    }
}
