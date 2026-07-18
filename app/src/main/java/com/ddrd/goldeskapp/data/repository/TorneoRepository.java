package com.ddrd.goldeskapp.data.repository;

import android.content.Context;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TorneoApiService;
import com.ddrd.goldeskapp.data.model.torneo.ResumenInscripcion;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoCreate;
import com.ddrd.goldeskapp.data.model.torneo.TorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoUpdate;
import com.ddrd.goldeskapp.util.TokenManager;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

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

    public void obtenerResumenInscripcion(Integer idTorneo, BuscarCallback<ResumenInscripcion> callback){
        apiService.obtenerResumenInscripcion(idTorneo).enqueue(new Callback<ResumenInscripcion>() {
            @Override
            public void onResponse(Call<ResumenInscripcion> call, Response<ResumenInscripcion> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("No se pudo obtener la información del torneo");
                }
            }

            @Override
            public void onFailure(Call<ResumenInscripcion> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void desactivarTorneo(Integer idTorneo, CallbackString<Map<String, String>> callback){
        apiService.desactivarTorneo(idTorneo).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().get("message"));
                } else {
                    callback.onError("Error al desactivar el torneo");
                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void activarTorneo(Integer idTorneo, CallbackString<Map<String, String>> callback){
        apiService.activarTorneo(idTorneo).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().get("message"));
                } else {
                    callback.onError("Error al activar el torneo");
                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }
    public void crearTorneo(TorneoCreate torneoCreate, CallbackString<Map<String, String>> callback){
        apiService.crearTorneo(torneoCreate).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body().get("mensaje"));
                } else {
                    String mensajeFinal = "Error al guardar el torneo";
                    try {
                        if (response.errorBody() != null){
                            String errorString = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorString);

                            String mesajeServidor = jsonObject.optString("message");

                            if (mesajeServidor.contains("Ya tienes un torneo")) {
                                mensajeFinal = "Ya tienes un torneo registrado con el nombre '"+torneoCreate.getNombreTorneo()+"'. Usa uno diferente.";
                            } else {
                                mensajeFinal = mesajeServidor;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callback.onError(mensajeFinal);
                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public void actualizarTorneo(TorneoUpdate torneoUpdate, CallbackString<Map<String, String>> callback){
        apiService.actualizarTorneo(torneoUpdate).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body().get("mensaje"));
                } else {
                    String mensajeFinal = "Error al actualizar el torneo";
                    try {
                        String errorString = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorString);
                        String mesajeServidor = jsonObject.optString("message");

                        if (mesajeServidor.contains("Ya tienes un torneo")) {
                            mensajeFinal = "Ya tienes un torneo registrado con el nombre '"+torneoUpdate.getNombreTorneo()+"'. Usa uno diferente.";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callback.onError(mensajeFinal);
                }
            }
            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
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
    public void obtenerTorneoPorId(Integer idTorneo, BuscarCallback buscarCallback){
        apiService.obtenerTorneoPorId(idTorneo).enqueue(new Callback<TorneoResponse>() {
            @Override
            public void onResponse(Call<TorneoResponse> call, Response<TorneoResponse> response) {
                if (response.isSuccessful()){
                    buscarCallback.onSuccess(response.body());
                } else {
                    buscarCallback.onError("Error al obtener torneo");
                }
            }
            @Override
            public void onFailure(Call<TorneoResponse> call, Throwable t) {
                buscarCallback.onError(t.getMessage());
            }
        });
    }
    public interface CallbackString<Map>{
        void onSuccess(String response);
        void onError(String message);
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
    public interface BuscarCallback<T> {
        void onSuccess(T response);
        void onError(String mensaje);
    }
}
