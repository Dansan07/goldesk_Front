package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.EquipoApiService;
import com.ddrd.goldeskapp.data.model.equipo.ActualizarNombreEquipo;
import com.ddrd.goldeskapp.data.model.equipo.SpinnerEquipoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipoRepository {

    private final EquipoApiService equipoApiService;

    public EquipoRepository(Context context) {
        this.equipoApiService = ApiClient.getClient(context).create(EquipoApiService.class);
    }

    public void obtenerEquiposSpinner(Integer idTorneo, EquipoCallback callback) {
        equipoApiService.obtenerEquiposDelSpinner(idTorneo).enqueue(new Callback<List<SpinnerEquipoResponse>>() {
            @Override
            public void onResponse(Call<List<SpinnerEquipoResponse>> call, Response<List<SpinnerEquipoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else if (response.code() == 204) {
                    callback.onNoContent();
                } else {
                    callback.onError("Error al obtener Equipos: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<SpinnerEquipoResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void actualizarNombreEquipo(ActualizarNombreEquipo actualizarNombreEquipo, ResponseSuccesCallback callback){
        equipoApiService.actualizarNombreEquipo(actualizarNombreEquipo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onError("Error al actualizar el nombre del equipo");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Interfaz para comunicar los resultados a la UI
    public interface EquipoCallback {
        void onSuccess(List<SpinnerEquipoResponse> equipos);
        void onNoContent();
        void onError(String mensaje);
    }

    public interface ResponseSuccesCallback{
        void onSuccess();
        void onError(String mensaje);
    }
}
