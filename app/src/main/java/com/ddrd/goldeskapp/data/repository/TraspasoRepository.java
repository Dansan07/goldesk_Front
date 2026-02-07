package com.ddrd.goldeskapp.data.repository;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TraspasosApiService;
import com.ddrd.goldeskapp.data.model.traspasos.PdfResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoCreate;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoUpdate;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraspasoRepository {

    private final TraspasosApiService traspasosApiService;

    public TraspasoRepository(Context context) {
        this.traspasosApiService = ApiClient.getClient(context).create(TraspasosApiService.class);
    }

    public void crearTraspaso(TraspasoCreate traspaso, TraspasoCallback callback){
        traspasosApiService.crearTraspaso(traspaso).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response);
                }else {
                    try {
                        String mensaje_error= response.errorBody().string();
                        callback.onError(mensaje_error);
                    }catch (IOException e){
                        callback.onError("Error en el servidor");
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void listarTraspasos(String estadoSolicitud, ListaTraspasoCallback callback){
        traspasosApiService.listarSolicitudes(estadoSolicitud).enqueue(new Callback<List<TraspasoResponse>>() {
            @Override
            public void onResponse(Call<List<TraspasoResponse>> call, Response<List<TraspasoResponse>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    try {
                        String mensaje_error = response.errorBody().string();
                        callback.onError(mensaje_error);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<TraspasoResponse>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void aprobarSolicitud(Integer idTraspaso, AprobacionTraspasosCallback callback){
        traspasosApiService.aprobarSolicitud(idTraspaso).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String mensaje = response.body().string();
                        callback.onSuccess(mensaje);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }else {
                    try {
                        String mensaje_error = response.errorBody().string();
                        callback.onError(mensaje_error);
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
    public void rechazarSolicitud(TraspasoUpdate traspaso, AprobacionTraspasosCallback callback){
        traspasosApiService.rechazarSolicitud(traspaso).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String mensaje = response.body().string();
                        callback.onSuccess(mensaje);
                    }catch (IOException e){
                        callback.onError(e.getMessage());
                    }
                }else {
                    try {
                        String mensaje_error = response.errorBody().string();
                        callback.onError(mensaje_error);
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

    public interface TraspasoCallback{
        void onSuccess(Response<ResponseBody> responseBody);
        void onError(String mensaje);
    }
    public interface ListaTraspasoCallback{
        void onSuccess(List<TraspasoResponse> responseBody);
        void onError(String mensaje);
    }
    public interface AprobacionTraspasosCallback{
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }
}
