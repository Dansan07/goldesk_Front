package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.Inscripcion.AbonoDetalleInscripcion;
import com.ddrd.goldeskapp.data.model.Inscripcion.InscripcionTorneoResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InscripcionApiService {
    @GET("api/pagos-inscripcion/listado/{idTorneo}")
    Call<List<InscripcionTorneoResponse>> obtenerInscripcionesPorTorneo(
            @Path("idTorneo") Integer idTorneo,
            @Query("estadoPago") String estadoPago
    );
    @POST("api/pagos-inscripcion/registrar")
    Call<Map<String, String>> registrarInscripcion(@Body Map<String, Object> inscripcionCreate);

    @GET("api/pagos-inscripcion/estado-cuenta/{idTorneoEquipo}")
    Call<List<AbonoDetalleInscripcion>> obtenerAbonosInscripcionEquipo(@Path("idTorneoEquipo") Integer idTorneoEquipo);
}
