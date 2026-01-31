package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.jugador.EstadisticasJugador;
import com.ddrd.goldeskapp.data.model.jugador.JugadorCreate;
import com.ddrd.goldeskapp.data.model.jugador.JugadorResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JugadorApiService {

    @GET("api/jugadores/listar-x-equipo/{idTorneoEquipo}")
    Call<List<JugadorResponse>> obtenerJugadoresPorEquipo(@Path("idTorneoEquipo") Integer idTorneoEquipo);

    @GET("api/jugadores/estadisticas/equipo/{idTorneoEquipo}")
    Call<List<EstadisticasJugador>> obtenerEstadisticasPorEquipo(@Path("idTorneoEquipo") Integer idTorneoEquipo);

    @GET("api/jugadores/buscar_info_jugador/{cc}")
    Call<JugadorResponse> buscarInfoJugador(@Path("cc") String cedula);

    @POST("api/jugadores/inscribir/{idTorneoEquipo}")
    Call<ResponseBody> inscribirJugador(@Body JugadorCreate jugador, @Path("idTorneoEquipo") Integer idTorneoEquipo);

    @PATCH("api/jugadores/dar-de-baja/{idInscripcion}")
    Call<ResponseBody> eliminarJugadorDeEquipo(@Path("idInscripcion") Integer idInscripcion);

}
