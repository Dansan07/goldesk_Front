package com.ddrd.goldeskapp.data.repository;

import android.content.Context;
import android.util.Log;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.PartidoApiService;
import com.ddrd.goldeskapp.data.model.partido.FiltroHistorialPartidos;
import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidoSave;
import com.ddrd.goldeskapp.data.model.partido.PartidosHistorialResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartidoRepository {

    private final PartidoApiService partidoApiService;

    public PartidoRepository(Context context) {
        this.partidoApiService = ApiClient.getClient(context).create(PartidoApiService.class);
    }

    public void programarPartido(PartidoSave partidoSave, PartidoGuardarCalback callback) {
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

    public void obtenerHistorialPartidos(FiltroHistorialPartidos filtro, PartidoHistorialCalback callback){
        Log.d("Filtro enviado", "obtenerHistorialPartidos: "+filtro.toString());
        Log.d("Filtro enviado", "obtenerHistorialPartidos: "+filtro.getIdTorneo());
        Log.d("Filtro enviado", "obtenerHistorialPartidos: "+filtro.getNombreEquipo());
        Log.d("Filtro enviado", "obtenerHistorialPartidos: "+filtro.getFechaInicio());
        Log.d("Filtro enviado", "obtenerHistorialPartidos: "+filtro.getFechaFin());

        partidoApiService.obetenerHistorialPartidos(
                filtro.getIdTorneo(),
                filtro.getNombreEquipo(),
                filtro.getFechaInicio(),
                filtro.getFechaFin()
        ).enqueue(new Callback<List<PartidosHistorialResponse>>() {
            @Override
            public void onResponse(Call<List<PartidosHistorialResponse>> call, Response<List<PartidosHistorialResponse>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    callback.onSuccess(response.body());
                }else {
                    callback.onError("Error al obtener historial: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<PartidosHistorialResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface PartidoGuardarCalback {
        void onSuccess(PartidoResponseDuplicate response);
        void onError(String mensaje);
    }
    public interface PartidoHistorialCalback{
        void onSuccess(List<PartidosHistorialResponse> partidos);
        void onNoContent();
        void onError(String mensaje);
    }
}
