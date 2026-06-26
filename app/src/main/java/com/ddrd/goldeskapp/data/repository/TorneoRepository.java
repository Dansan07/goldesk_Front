package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TorneoApiService;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoResponse;
import com.ddrd.goldeskapp.util.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TorneoRepository {

    private final TorneoApiService apiService;
    private final TokenManager tokenManager;

    public TorneoRepository(Context context) {
        this.apiService = ApiClient.getClient(context).create(TorneoApiService.class);
        this.tokenManager = new TokenManager(context);
    }

    public void buscarCategorias(String cedulaOrg, CategoriasTorneos callback){
        apiService.buscarCategorias(cedulaOrg).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener categorias");
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public void obtenerTorneos(TorneoCallback callback) {
        apiService.obtenerTorneosDelOrganizador(tokenManager.getCodigo()).enqueue(new Callback<List<SpinnerTorneoResponse>>() {
            @Override
            public void onResponse(Call<List<SpinnerTorneoResponse>> call, Response<List<SpinnerTorneoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else if (response.code() == 204) {
                    callback.onNoContent();
                } else {
                    callback.onError("Error al obtener torneos: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<SpinnerTorneoResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void obtenerTorneosActivos(TorneoCallback callback) {
        apiService.obtenerTorneosActivosDelOrganizador(tokenManager.getCodigo()).enqueue(new Callback<List<SpinnerTorneoResponse>>() {
            @Override
            public void onResponse(Call<List<SpinnerTorneoResponse>> call, Response<List<SpinnerTorneoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else if (response.code() == 204) {
                    callback.onNoContent();
                } else {
                    callback.onError("Error al obtener torneos: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<SpinnerTorneoResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void obtenerTorneoPorId(Integer idTorneo, TorneoBuscarCallback torneoBuscarCallback){
        apiService.obtenerTorneoPorId(idTorneo).enqueue(new Callback<TorneoResponse>() {
            @Override
            public void onResponse(Call<TorneoResponse> call, Response<TorneoResponse> response) {
                if (response.isSuccessful()){
                    torneoBuscarCallback.onSuccess(response.body());
                } else {
                    torneoBuscarCallback.onError("Error al obtener torneo");
                }
            }
            @Override
            public void onFailure(Call<TorneoResponse> call, Throwable t) {
                torneoBuscarCallback.onError(t.getMessage());
            }
        });
    }

    // Interfaz para comunicar los resultados a la UI
    public interface CategoriasTorneos{
        void onSuccess(List<String> categorias);
        void onError(String message);
    }

    public interface TorneoCallback {
        void onSuccess(List<SpinnerTorneoResponse> torneos);
        void onNoContent();
        void onError(String mensaje);
    }
    public interface TorneoBuscarCallback {
        void onSuccess(TorneoResponse response);
        void onError(String mensaje);
    }
}
