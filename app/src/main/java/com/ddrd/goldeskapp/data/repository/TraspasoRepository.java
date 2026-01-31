package com.ddrd.goldeskapp.data.repository;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.ddrd.goldeskapp.data.api.ApiClient;
import com.ddrd.goldeskapp.data.api.TraspasosApiService;
import com.ddrd.goldeskapp.data.model.traspasos.PdfResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoCreate;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraspasoRepository {

    private final TraspasosApiService traspasosApiService;
    private final Context context;

    public TraspasoRepository(Context context) {
        this.traspasosApiService = ApiClient.getClient(context).create(TraspasosApiService.class);
        this.context = context;
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

    public interface TraspasoCallback{
        void onSuccess(Response<ResponseBody> responseBody);
        void onError(String mensaje);
    }
}
