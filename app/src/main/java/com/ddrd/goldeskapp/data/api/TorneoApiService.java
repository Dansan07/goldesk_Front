package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.equipo.ActualizarNombreEquipo;
import com.ddrd.goldeskapp.data.model.torneo.ResumenInscripcion;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoCreate;
import com.ddrd.goldeskapp.data.model.torneo.TorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoUpdate;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TorneoApiService {

    @GET("api/torneos/resumen-inscripcion/{idTorneo}")
    Call<ResumenInscripcion> obtenerResumenInscripcion(@Path("idTorneo") Integer idTorneo);

    @PUT("api/torneos/delete/{id}")
    Call<Map<String, String>> desactivarTorneo(@Path("id") Integer id);
    @PUT("api/torneos/recuperar/{id}")
    Call<Map<String, String>> activarTorneo(@Path("id") Integer id);

    @POST("api/torneos/guardar_torneo")
    Call<Map<String, String>> crearTorneo(@Body TorneoCreate torneoCreate);

    @PUT("api/torneos/actualizar_torneo")
    Call<Map<String, String>> actualizarTorneo(@Body TorneoUpdate torneoUpdate);

    @GET("api/organizadores/{cedula}/torneos_activos")
    Call<List<SpinnerTorneoResponse>> obtenerTorneosActivosDelOrganizador(@Path("cedula") String cedula);

    @GET("api/organizadores/{cedula}/torneos")
    Call<List<SpinnerTorneoResponse>> obtenerTorneosDelOrganizador(@Path("cedula") String cedula);
    @GET("api/torneos/{idTorneo}")
    Call<TorneoResponse> obtenerTorneoPorId(@Path("idTorneo") Integer idTorneo);

    @GET("api/torneos/categorias/{cedulaOrg}")
    Call<List<String>> buscarCategorias(@Path("cedulaOrg") String cedulaOrg);
}
