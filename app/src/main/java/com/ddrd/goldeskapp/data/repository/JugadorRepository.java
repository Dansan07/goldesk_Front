package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.JugadorApiService;
import com.ddrd.goldeskapp.data.model.jugador.EstadisticasJugador;
import com.ddrd.goldeskapp.data.model.jugador.JugadorCarnet;
import com.ddrd.goldeskapp.data.model.jugador.JugadorCreate;
import com.ddrd.goldeskapp.data.model.jugador.JugadorResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugadorRepository {

    private final JugadorApiService jugadorApiService;

    public JugadorRepository(Context context){
        this.jugadorApiService = ApiClient.getClient(context).create(JugadorApiService.class);
    }

    public void obtenerJugadoresPorEquipo(Integer idTorneoEquipo, JugadorCallback<List<JugadorResponse>> callback){
        jugadorApiService.obtenerJugadoresPorEquipo(idTorneoEquipo).enqueue(new Callback<List<JugadorResponse>>() {
            @Override
            public void onResponse(Call<List<JugadorResponse>> call, Response<List<JugadorResponse>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<JugadorResponse> responses = response.body();
                    if (responses.isEmpty()){
                        callback.onNoContent();
                    }else {
                        callback.onSuccess(responses);
                    }
                }else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }
            @Override
            public void onFailure(Call<List<JugadorResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public void obtenerEstadisticasPorEquipo(Integer idTorneoEquipo, JugadorCallback<List<EstadisticasJugador>> callback){
        jugadorApiService.obtenerEstadisticasPorEquipo(idTorneoEquipo).enqueue(new Callback<List<EstadisticasJugador>>() {
            @Override
            public void onResponse(Call<List<EstadisticasJugador>> call, Response<List<EstadisticasJugador>> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body());
                }else if (response.code() == 204){
                    callback.onNoContent();
                }else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }
            @Override
            public void onFailure(Call<List<EstadisticasJugador>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void buscarInfoJugador(String cedula, JugadorCallback<JugadorResponse> callback){
        jugadorApiService.buscarInfoJugador(cedula).enqueue(new Callback<JugadorResponse>() {
            @Override
            public void onResponse(Call<JugadorResponse> call, Response<JugadorResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() == null){
                        callback.onNoContent();
                        return;
                    }
                    callback.onSuccess(response.body());
                }else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }
            @Override
            public void onFailure(Call<JugadorResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public void inscribirJugador(JugadorCreate jugadorCreate, Integer idTorneoEquipo, JugadorCallback<Object> callback){
        jugadorApiService.inscribirJugador(jugadorCreate,idTorneoEquipo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    try {
                        String error = response.errorBody().string();
                        if (response.code() == 412){
                            callback.onNoContent();
                        }else {
                            callback.onError(error);
                        }
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void eliminarJugadorDeEquipo(Integer idInscripcion, JugadorCallback<Object> callback){
        jugadorApiService.eliminarJugadorDeEquipo(idInscripcion).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    try {
                        String error = response.errorBody().string();
                        callback.onError(error);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public void obtenerCarnetJugador(Integer idInscripcion, JugadorCallback<JugadorCarnet> callback){
        jugadorApiService.obtenerCarnetJugador(idInscripcion).enqueue(new Callback<JugadorCarnet>() {
            @Override
            public void onResponse(Call<JugadorCarnet> call, Response<JugadorCarnet> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    try {
                        String error_mesagge = response.errorBody().string();
                        callback.onError(error_mesagge);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<JugadorCarnet> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface JugadorCallback<T>{
        void onSuccess(T responses);
        void onNoContent();
        void onError(String mensaje);
    }
}
