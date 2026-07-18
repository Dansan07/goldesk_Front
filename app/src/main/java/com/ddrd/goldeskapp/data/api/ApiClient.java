package com.ddrd.goldeskapp.data.api;

import android.content.Context;


import com.ddrd.goldeskapp.BuildConfig;
import com.ddrd.goldeskapp.security.AuthInterceptor;
import com.ddrd.goldeskapp.util.TokenManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit= null;
    //private static final String BASE_URL = BuildConfig.BASE_URL;
    //private static final String URL_EMU = BuildConfig.URL_EMU;
    private static final String LOCAL_URL = BuildConfig.LOCAL_URL;
    private static final String URL_TELEFONO = BuildConfig.URL_TELEFONO;

    public static Retrofit getClient(Context context){
        if (retrofit==null){
            Context appContext = context.getApplicationContext();
            // 1. Instanciar el TokenManager
            TokenManager tokenManager = new TokenManager(appContext);

            // 2. Crear un interceptor de logs (para ver las peticiones en el Logcat)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 3. Configurar el cliente OkHttp con tus interceptores
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS) // Tiempo máximo para conectar con el servidor
                    .readTimeout(10, TimeUnit.SECONDS)    // Tiempo máximo para esperar los datos
                    .writeTimeout(10,TimeUnit.SECONDS)   // Tiempo máximo para enviar datos
                    .addInterceptor(logging) // Para ver qué pasa en consola
                    .addInterceptor(new AuthInterceptor(tokenManager)) // Tu interceptor de seguridad
                    .build();

            // 4. Construir Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(LOCAL_URL) // IP para el emulador de Android
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit;
    }
}
