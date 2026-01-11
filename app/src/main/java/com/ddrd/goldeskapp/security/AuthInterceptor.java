package com.ddrd.goldeskapp.security;

import androidx.annotation.NonNull;
import com.ddrd.goldeskapp.util.TokenManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor{

    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        // 1. Recuperar el token guardado
        String token = tokenManager.getToken();

        // 2. Si existe el token, añadirlo al Header
        if (token != null && !token.isEmpty()) {
            // "Authorization" es la clave que busca tu backend
            // "Bearer " es el prefijo que definimos en el filtro de Spring
            builder.addHeader("Authorization", "Bearer " + token);
        }

        // 3. Continuar con la petición modificada
        return chain.proceed(builder.build());
    }
}
