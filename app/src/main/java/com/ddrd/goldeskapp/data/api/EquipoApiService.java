package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.equipo.ActualizarNombreEquipo;
import com.ddrd.goldeskapp.data.model.equipo.SpinnerEquipoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface EquipoApiService {

    @GET("api/torneoEquipos/torneos/{idTorneo}/equipos")
    Call<List<SpinnerEquipoResponse>> obtenerEquiposDelSpinner(@Path("idTorneo") Integer idTorneo);

    @PATCH("api/torneoEquipos/actualizar-nombre")
    Call<Void> actualizarNombreEquipo(@Body ActualizarNombreEquipo actualizarNombreEquipo);
}
