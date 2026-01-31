package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.partido.FiltroHistorialPartidos;
import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidoSave;
import com.ddrd.goldeskapp.data.model.partido.PartidosHistorialResponse;
import com.ddrd.goldeskapp.data.model.planillaDigital.PlanillaDigitalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PartidoApiService {

    @POST("api/partidos/programar-partido")
    Call<PartidoResponseDuplicate> programarPartido(@Body PartidoSave partidoSave);

    /*@GET("api/partidos/historial-partidos")
    Call<List<PartidosHistorialResponse>> obetenerHistorialPartidos(@Body FiltroHistorialPartidos filtro);*/

    @GET("api/partidos/historial-partidos")
    Call<List<PartidosHistorialResponse>> obetenerHistorialPartidos(
            @Query("idTorneo") Integer idTorneo,
            @Query("nombreEquipo") String nombreEquipo,
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin
    );

    @GET("api/partidos/{idPartido}/ver-planilla")
    Call<PlanillaDigitalResponse> obtenerPlanillaDigital(@Path("idPartido") Integer idPartido);

    @POST("api/partidos/{idPartido}/iniciar")
    Call<Void> iniciarPartido(@Path("idPartido")Integer idPartido);

    @POST("api/partidos/{idPartido}/finalizar")
    Call<Void> finalizarPartido(@Path("idPartido")Integer idPartido);

    @PATCH("api/partidos/participacion/{idParticipacion}/dorsal/{dorsal}")
    Call<Void> actualizarDorsal(@Path("idParticipacion") Integer idParticipacion, @Path("dorsal") String dorsal);
}
