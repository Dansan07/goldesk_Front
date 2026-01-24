package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.GolApiService;
import com.ddrd.goldeskapp.data.model.gol.GolCreate;
import com.ddrd.goldeskapp.data.model.gol.GolResponse;

import java.util.List;

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

    public void eliminarGol(Integer idGol, GolCallback callback){
        golApiService.eliminarGol(idGol).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    callback.onSuccess();
                } else {
                    callback.onError("Error al eliminar gol");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void listarGolesPorParticipacion(Integer idParticipacion, ListaGolesCallback callback){
        golApiService.buscarGolesPorJugador(idParticipacion).enqueue(new Callback<List<GolResponse>>() {
            @Override
            public void onResponse(Call<List<GolResponse>> call, Response<List<GolResponse>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else if (response.code() == 204){
                    callback.onNoContent();
                }else {
                    callback.onError("Error al listar goles");
                }
            }
            @Override
            public void onFailure(Call<List<GolResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public interface GolCallback{
        void onSuccess();
        void onError(String mensaje);
    }
    public interface ListaGolesCallback{
        void onSuccess(List<GolResponse> listaGoles);
        void onNoContent();
        void onError(String mensaje);
    }
}
