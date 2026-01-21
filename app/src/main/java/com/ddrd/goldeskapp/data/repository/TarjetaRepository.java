package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TarjetaApiService;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;

import java.util.List;

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

    public void buscarTarjetasPorJugador(Integer idParticipacion, String tipoTarjeta, ListaTarjetasCallback callback){
        tarjetaApiService.buscarTarjetasPorJugador(idParticipacion, tipoTarjeta).enqueue(new Callback<List<TarjetasResponse>>() {
            @Override
            public void onResponse(Call<List<TarjetasResponse>> call, Response<List<TarjetasResponse>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.onSuccess(response.body());
                    }else {
                        callback.onNoContent();
                    }
                }else {
                    callback.onError("Error al buscar tarjetas");
                }
            }
            @Override
            public void onFailure(Call<List<TarjetasResponse>> call, Throwable t) {
                callback.onError("Error al buscar tarjetas");
            }
        });
    }

    public interface TarjetaCallback{
        void onSuccess();
        void onError(String mensaje);
    }

    public interface ListaTarjetasCallback{
        void onSuccess(List<TarjetasResponse> responses);
        void onNoContent();
        void onError(String mensaje);
    }

}
