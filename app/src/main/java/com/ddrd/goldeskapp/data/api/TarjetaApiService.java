package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaTorneoResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TarjetaApiService {

    @GET("api/tarjetas/{idTorneo}")
    Call<List<TarjetaTorneoResponse>> obtenerTarjetasPorTorneo(
            @Path("idTorneo") Integer idTorneo,
            @Query("estadoPago") String estadoPago
    );
    @POST("api/tarjetas/registrar-tarjeta")
    Call<Void> registrarTarjeta(@Body TarjetaCreate tarjetaCreate);

    @DELETE("api/tarjetas/{idTarjeta}")
    Call<Void> eliminarTarjeta(@Path("idTarjeta") Integer idTarjeta);

    @GET("api/tarjetas/buscar-x-jugador")
    Call<List<TarjetasResponse>> buscarTarjetasPorJugador(
            @Query("idParticipacion") Integer idParticipacion,
            @Query("tipoTarjeta") String tipoTarjeta
    );

    @POST("api/tarjetas/registrar_pago_tarjeta")
    Call<Void> registrarPagoTarjetas(@Body Map<String, Object> map);
}
