package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.PartidoApiService;
import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidoSave;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartidoRepository {

    private final PartidoApiService partidoApiService;

    public PartidoRepository(Context context) {
        this.partidoApiService = ApiClient.getClient(context).create(PartidoApiService.class);
    }

    public void programarPartido(PartidoSave partidoSave, PartidoCalback callback) {
        partidoApiService.programarPartido(partidoSave).enqueue(new Callback<PartidoResponseDuplicate>() {
            @Override
            public void onResponse(Call<PartidoResponseDuplicate> call, Response<PartidoResponseDuplicate> response) {
                if (response.isSuccessful()) callback.onSuccess(response.body());
                else callback.onError("Error al programar: " + response.errorBody());
            }
            @Override
            public void onFailure(Call<PartidoResponseDuplicate> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public interface PartidoCalback {
        void onSuccess(PartidoResponseDuplicate partidoResponseDuplicate);
        void onError(String mensaje);
    }
}
