package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.PartidoApiService;
import com.ddrd.goldeskapp.data.model.partido.FiltroHistorialPartidos;
import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidoSave;
import com.ddrd.goldeskapp.data.model.partido.PartidosHistorialResponse;
import com.ddrd.goldeskapp.data.model.planillaDigital.PlanillaDigitalResponse;

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

        partidoApiService.obetenerHistorialPartidos(
                filtro.getIdTorneo(),
                filtro.getNombreEquipo(),
                filtro.getFechaInicio(),
                filtro.getFechaFin()
        ).enqueue(new Callback<List<PartidosHistorialResponse>>() {
            @Override
            public void onResponse(Call<List<PartidosHistorialResponse>> call, Response<List<PartidosHistorialResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else if (response.code() == 204) {
                    callback.onNoContent();
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

    public void abrirPlanillaDigital(Integer idPartido, PartidoBuscarCalback partidoBuscarCalback){

        partidoApiService.obtenerPlanillaDigital(idPartido).enqueue(new Callback<PlanillaDigitalResponse>() {
            @Override
            public void onResponse(Call<PlanillaDigitalResponse> call, Response<PlanillaDigitalResponse> response) {
                if (response.isSuccessful() && response.body()!=null){
                    partidoBuscarCalback.onSuccess(response.body());
                }else {
                    partidoBuscarCalback.onError("Error al obtener Planilla Digital: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<PlanillaDigitalResponse> call, Throwable t) {
                partidoBuscarCalback.onError(t.getMessage());
            }
        });
    }

    public void iniciarPartido(Integer idPartido, StatusPartidoCallback partidoIniciadoCallback){
        partidoApiService.iniciarPartido(idPartido).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    partidoIniciadoCallback.onSuccess("Partido iniciado correctamente. Las participaciones han sido generadas.");
                }else {
                    partidoIniciadoCallback.onError("Error al iniciar partido: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                partidoIniciadoCallback.onError(t.getMessage());
            }
        });
    }
    public void finalizarPartido(Integer idpartido, StatusPartidoCallback statusPartidoCallback){
        partidoApiService.finalizarPartido(idpartido).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    statusPartidoCallback.onSuccess("Partido finalizado correctamente.");
                }else {
                    statusPartidoCallback.onError("Error al finalizar partido");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                statusPartidoCallback.onError(t.getMessage());
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
    public interface PartidoBuscarCalback {
        void onSuccess(PlanillaDigitalResponse response);
        void onError(String mensaje);
    }
    public interface StatusPartidoCallback {
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }
}
