package com.ddrd.goldeskapp.data.api;

import android.content.Context;


import com.ddrd.goldeskapp.BuildConfig;
import com.ddrd.goldeskapp.security.AuthInterceptor;
import com.ddrd.goldeskapp.util.TokenManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit= null;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    //private static final String IP_EMULADOR= "10.0.2.2";

    public static Retrofit getClient(Context context){
        if (retrofit==null){
            // 1. Instanciar el TokenManager
            TokenManager tokenManager = new TokenManager(context);

            // 2. Crear un interceptor de logs (para ver las peticiones en el Logcat)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 3. Configurar el cliente OkHttp con tus interceptores
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging) // Para ver qué pasa en consola
                    .addInterceptor(new AuthInterceptor(tokenManager)) // Tu interceptor de seguridad
                    .build();

            // 4. Construir Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // IP para el emulador de Android
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit;
    }
}
