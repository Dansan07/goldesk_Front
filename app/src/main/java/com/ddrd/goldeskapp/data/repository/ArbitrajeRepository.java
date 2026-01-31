package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.PagoArbitrajeApiService;
import com.ddrd.goldeskapp.data.model.pagosArbitraje.PagoArbitrajeCreate;
import com.ddrd.goldeskapp.data.model.pagosArbitraje.PagoArbitrajeUpdate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArbitrajeRepository {

    private final PagoArbitrajeApiService pagoArbitrajeApiService;

    public ArbitrajeRepository(Context context) {
        this.pagoArbitrajeApiService = ApiClient.getClient(context).create(PagoArbitrajeApiService.class);
    }

    public void registrarPagoArbitraje(PagoArbitrajeCreate pago, ArbitrajeCallback callback){
        pagoArbitrajeApiService.registrarPagoArbitraje(pago).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    callback.onSuccess();
                } else {
                    callback.onError("Error al registrar pago arbitraje");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void actualizarPagoArbitraje(PagoArbitrajeUpdate pago, ArbitrajeCallback arbitrajeCallback){
        pagoArbitrajeApiService.actualizarPagoArbitraje(pago).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    arbitrajeCallback.onSuccess();
                }else {
                    arbitrajeCallback.onError("Error al actualizar pago arbitraje");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                arbitrajeCallback.onError(t.getMessage());
            }
        });
    }

    public interface ArbitrajeCallback {
        void onSuccess();
        void onError(String mensaje);
    }
}

