package com.ddrd.goldeskapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ddrd.goldeskapp.data.model.organizador.Organizador;

public class TokenManager {

    private static final String PREF_NAME = "GOLDESK_PREFS";
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_ROL = "user_role";
    private static final String KEY_NOMBRE = "user_nombre";
    private static final String KEY_CODIGO = "user_codigo";
    private static final String KEY_ORGANIZADOR = "organizador";
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Guarda el token cuando el login es exitoso
    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    // Recupera el token para el AuthInterceptor
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // Guarda el rol (ADMIN, ORGANIZADOR, etc.) para la lógica de la UI
    public void saveRole(String role) {
        prefs.edit().putString(KEY_ROL, role).apply();
    }

    public String getRole() {
        return prefs.getString(KEY_ROL, null);
    }

    public void saveNombre(String nombre) {
        prefs.edit().putString(KEY_NOMBRE, nombre).apply();
    }

    public String getNombre() {
        return prefs.getString(KEY_NOMBRE, null);
    }

    public void saveCodigo(String codigo) {
        prefs.edit().putString(KEY_CODIGO, codigo).apply();
    }

    public String getCodigo() {
        return prefs.getString(KEY_CODIGO, null);
    }

    // Limpia al cerrar sesión (Logout)
    public void clear() {
        prefs.edit().clear().apply();
    }
}
