package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.InscripcionApiService;
import com.ddrd.goldeskapp.data.model.Inscripcion.AbonoDetalleInscripcion;
import com.ddrd.goldeskapp.data.model.Inscripcion.InscripcionTorneoResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscripcionRepository {
    private final InscripcionApiService inscripcionApiService;

    public InscripcionRepository(Context context) {
        this.inscripcionApiService = ApiClient.getClient(context).create(InscripcionApiService.class);
    }

    public void obtenerAbonosInscripcionEquipo(Integer idTorneoEquipo, InscripcionCallback<AbonoDetalleInscripcion> callback){
        inscripcionApiService.obtenerAbonosInscripcionEquipo(idTorneoEquipo).enqueue(new Callback<List<AbonoDetalleInscripcion>>() {
            @Override
            public void onResponse(Call<List<AbonoDetalleInscripcion>> call, Response<List<AbonoDetalleInscripcion>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.onSuccess(response.body());
                    }else {
                        callback.onNoContent();
                    }
                }else {
                    callback.onError("Error al listar abonos");
                }
            }
            @Override
            public void onFailure(Call<List<AbonoDetalleInscripcion>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void obtenerInscripcionesPorTorneo(Integer idTorneo, String estadoPago, InscripcionCallback<InscripcionTorneoResponse> callback) {
        inscripcionApiService.obtenerInscripcionesPorTorneo(idTorneo, estadoPago).enqueue(new Callback<List<InscripcionTorneoResponse>>() {
            @Override
            public void onResponse(Call<List<InscripcionTorneoResponse>> call, Response<List<InscripcionTorneoResponse>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.onSuccess(response.body());
                    }else {
                        callback.onNoContent();
                    }
                }else {
                    callback.onError("Error al listar inscripciones");
                }
            }
            @Override
            public void onFailure(Call<List<InscripcionTorneoResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void registrarInscripcion(Map<String, Object> inscripcionCreate, crearInscripcionCallback callback){
        inscripcionApiService.registrarInscripcion(inscripcionCreate).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.onSuccess(response.body().get("mensaje"));
                    }else {
                        callback.onError("Error al registrar inscripción");
                    }
                }else {
                    callback.onError("Error al registrar inscripción");
                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onError("Error al registrar inscripción");
            }
        });
    }

    public interface crearInscripcionCallback{
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }
    public interface InscripcionCallback<T>{
        void onSuccess(List<T> inscripciones);
        void onNoContent();
        void onError(String mensaje);
    }
}
