package com.ddrd.goldeskapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ddrd.goldeskapp.data.model.organizador.OrganizadorResponse;

public class TokenManager {

    private static final String PREF_NAME = "GOLDESK_PREFS";
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_ROL = "user_role";
    private static final String KEY_CEDULA = "user_cedula";
    private static final String KEY_NOMBRE = "user_nombre";
    private static final String KEY_APELLIDOS = "user_apellidos";
    private static final String KEY_TELEFONO = "user_telefono";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_CODIGO_INVITADO = "user_codigo_invitado";
    private static final String KEY_ACTIVO = "user_activo";
    private static final String KEY_URL_LOGO_ORG = "user_url_logo_org";
    private static final String KEY_URL_LOGO_EQUIPO = "user_url_logo_equipo";
    private static final String KEY_CODIGO = "user_codigo";
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        this.prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Guarda el token cuando el login es exitoso
    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }
    // Recupera el token para el AuthInterceptor
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // Guardar el objeto Organizador que se obtiene
    public void saveOrganizador(OrganizadorResponse organizadorResponse){
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(KEY_CEDULA, organizadorResponse.getCedula());
        editor.putString(KEY_NOMBRE, organizadorResponse.getNombre());
        editor.putString(KEY_APELLIDOS, organizadorResponse.getApellidos());
        editor.putString(KEY_TELEFONO, organizadorResponse.getTelefono());
        editor.putString(KEY_EMAIL, organizadorResponse.getEmail());
        editor.putString(KEY_CODIGO_INVITADO, organizadorResponse.getCodigoInvitado());
        editor.putString(KEY_ROL, organizadorResponse.getRol());
        editor.putBoolean(KEY_ACTIVO, organizadorResponse.isActivo());
        editor.putString(KEY_URL_LOGO_ORG, organizadorResponse.getUrlLogoOrg());

        editor.apply();
    }

    // Recuperar el objeto Organizador
    public OrganizadorResponse getOrganizador() {
        String cedula = prefs.getString(KEY_CEDULA, null);
        String nombre = prefs.getString(KEY_NOMBRE, null);
        String apellidos = prefs.getString(KEY_APELLIDOS, null);
        String telefono = prefs.getString(KEY_TELEFONO, null);
        String email = prefs.getString(KEY_EMAIL, null);
        String codigoInvitado = prefs.getString(KEY_CODIGO_INVITADO, null);
        String rol = prefs.getString(KEY_ROL, null);
        boolean activo = prefs.getBoolean(KEY_ACTIVO, false);
        String urlLogoOrg = prefs.getString(KEY_URL_LOGO_ORG, null);
        return new OrganizadorResponse(cedula, nombre, apellidos, telefono, email, codigoInvitado, rol, activo, urlLogoOrg);
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

    public void saveCodigo(String codigo) {prefs.edit().putString(KEY_CODIGO, codigo).apply();}
    //se configura de esta manera porque al iniciar sesión un delegado el será leido el codigo en vez de la cedula.
    public String getCodigo() {
        return prefs.getString(KEY_CODIGO, null) == null?
                prefs.getString(KEY_CEDULA, null):prefs.getString(KEY_CODIGO, null);
    }

    // Limpia al cerrar sesión (Logout)
    public void clear() {
        prefs.edit().clear().apply();
    }
}
