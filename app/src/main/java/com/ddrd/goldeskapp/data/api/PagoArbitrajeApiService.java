package com.ddrd.goldeskapp.data.api;

import com.ddrd.goldeskapp.data.model.pagosArbitraje.PagoArbitrajeCreate;
import com.ddrd.goldeskapp.data.model.pagosArbitraje.PagoArbitrajeUpdate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface PagoArbitrajeApiService {

    @POST("api/pagos-arbitraje/registrar")
    Call<Void> registrarPagoArbitraje(@Body PagoArbitrajeCreate pago);

    @PUT("api/pagos-arbitraje/actualizar")
    Call<Void> actualizarPagoArbitraje(@Body PagoArbitrajeUpdate pago);
}
