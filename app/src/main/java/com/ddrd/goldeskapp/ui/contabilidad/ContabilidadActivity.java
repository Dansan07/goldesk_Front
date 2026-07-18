package com.ddrd.goldeskapp.ui.contabilidad;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.Inscripcion.AbonoDetalleInscripcion;
import com.ddrd.goldeskapp.data.model.Inscripcion.InscripcionTorneoResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaTorneoResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;
import com.ddrd.goldeskapp.data.model.torneo.ResumenInscripcion;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.InscripcionRepository;
import com.ddrd.goldeskapp.data.repository.TarjetaRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContabilidadActivity extends AppCompatActivity {

    private Spinner spinnerTorneoContabilidad;
    private MaterialAutoCompleteTextView editTextFiltroEquipo;
    private TabLayout tabLayoutContabilidad;
    private MaterialButtonToggleGroup toggleTipoTarjeta;
    private MaterialButton btnFiltroDeben, btnFiltroAbono, btnFiltroPagaron, btnAmarillas, btnAzules, btnRojas;
    private TextView textViewMontoTotalRecolectado, textViewMontoTotalAdeudado;
    private RecyclerView recyclerViewContabilidad;
    private LinearLayout layoutEmptyContabilidad;

    //utiles
    private List<TarjetaTorneoResponse> listaTarjetas;
    private List<InscripcionTorneoResponse> listaInscripciones;
    private ProgressBarGoldesk progressBarGoldesk;
    private SpinnerTorneo spinnerTorneo;
    private SpinnerTorneoResponse torneoSeleccionado;
    private TorneoRepository torneoRepository;
    private TarjetaRepository tarjetaRepository;
    private InscripcionRepository inscripcionRepository;
    private DialogsResponse dialogsResponse;
    private int positionTabContabilidad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contabilidad);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents(){
        spinnerTorneoContabilidad = findViewById(R.id.spinnerTorneoContabilidad);
        editTextFiltroEquipo = findViewById(R.id.editTextFiltroEquipo);
        tabLayoutContabilidad = findViewById(R.id.tabLayoutContabilidad);
        toggleTipoTarjeta = findViewById(R.id.toggleTipoTarjeta);
        btnFiltroDeben = findViewById(R.id.btnFiltroDeben);
        btnFiltroAbono = findViewById(R.id.btnFiltroAbono);
        btnFiltroPagaron = findViewById(R.id.btnFiltroPagaron);
        btnAmarillas = findViewById(R.id.btnAmarillas);
        btnAzules = findViewById(R.id.btnAzules);
        btnRojas = findViewById(R.id.btnRojas);
        textViewMontoTotalRecolectado = findViewById(R.id.textViewMontoTotalRecolectado);
        textViewMontoTotalAdeudado = findViewById(R.id.textViewMontoTotalAdeudado);
        recyclerViewContabilidad = findViewById(R.id.recyclerViewContabilidad);
        recyclerViewContabilidad.setLayoutManager(new LinearLayoutManager(this));
        layoutEmptyContabilidad = findViewById(R.id.layoutEmptyContabilidad);

        //utiles
        listaTarjetas = new ArrayList<>();
        listaInscripciones = new ArrayList<>();
        progressBarGoldesk = new ProgressBarGoldesk(this);
        spinnerTorneo = new SpinnerTorneo();
        torneoRepository = new TorneoRepository(this);
        tarjetaRepository = new TarjetaRepository(this);
        inscripcionRepository = new InscripcionRepository(this);
        dialogsResponse = new DialogsResponse(this);

        buscarTorneos();
        initListeners();
    }
    private void initListeners(){
        spinnerTorneoContabilidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerTorneoResponse torneo = (SpinnerTorneoResponse) spinnerTorneoContabilidad.getSelectedItem();
                torneoSeleccionado = torneo;
                btnFiltroDeben.setChecked(true);
                if (positionTabContabilidad == 1) {
                    listarInscripnes(torneoSeleccionado.getIdTorneo(), "DEBE");
                }else {
                    listarTarjetas(torneoSeleccionado.getIdTorneo(), "DEBE");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tabLayoutContabilidad.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                positionTabContabilidad = tab.getPosition();
                switch (positionTabContabilidad){
                    case 0:
                        toggleTipoTarjeta.setVisibility(View.VISIBLE);
                        if (btnFiltroDeben.isChecked()) {
                            listarTarjetas(torneoSeleccionado.getIdTorneo(),"DEBE");
                        } else if (btnFiltroAbono.isChecked()) {
                            listarTarjetas(torneoSeleccionado.getIdTorneo(),"ABONO");
                        } else if (btnFiltroPagaron.isChecked()) {
                            listarTarjetas(torneoSeleccionado.getIdTorneo(),"PAGADO");
                        } else {
                            btnFiltroDeben.setChecked(true);
                            listarTarjetas(torneoSeleccionado.getIdTorneo(),"DEBE");
                        }
                        break;
                    case 1:
                        toggleTipoTarjeta.setVisibility(View.GONE);
                        if (btnFiltroDeben.isChecked()) {
                            listarInscripnes(torneoSeleccionado.getIdTorneo(),"DEBE");
                        } else if (btnFiltroAbono.isChecked()) {
                            listarInscripnes(torneoSeleccionado.getIdTorneo(),"ABONO");
                        } else if (btnFiltroPagaron.isChecked()) {
                            listarInscripnes(torneoSeleccionado.getIdTorneo(),"PAGADO");
                        }else {
                            btnFiltroDeben.setChecked(true);
                            listarInscripnes(torneoSeleccionado.getIdTorneo(),"DEBE");
                        }
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        editTextFiltroEquipo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                if (positionTabContabilidad == 1){
                    filtroEquipoInscripcion(input);
                }else {
                    filtroEquipoJugador(input);
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnFiltroDeben.setOnClickListener(view -> {
            if (positionTabContabilidad == 1) {
                listarInscripnes(torneoSeleccionado.getIdTorneo(), "DEBE");
            }else {
                listarTarjetas(torneoSeleccionado.getIdTorneo(), "DEBE");
            }
            toggleTipoTarjeta.clearChecked();
        });
        btnFiltroAbono.setOnClickListener(view -> {
            if (positionTabContabilidad == 1) {
                listarInscripnes(torneoSeleccionado.getIdTorneo(), "ABONO");
            }else {
                listarTarjetas(torneoSeleccionado.getIdTorneo(), "ABONO");
            }
            toggleTipoTarjeta.clearChecked();
        });
        btnFiltroPagaron.setOnClickListener(view -> {
            if (positionTabContabilidad == 1) {
                listarInscripnes(torneoSeleccionado.getIdTorneo(), "PAGADO");
            }else {
                listarTarjetas(torneoSeleccionado.getIdTorneo(), "PAGADO");
            }
            toggleTipoTarjeta.clearChecked();
        });
        btnAmarillas.setOnClickListener(view -> {filtrarTipoTarjeta("amarilla");
        });
        btnAzules.setOnClickListener(view -> {filtrarTipoTarjeta("azul");
        });
        btnRojas.setOnClickListener(view -> {filtrarTipoTarjeta("roja");
        });
    }
    private void obtenerAbonosInscripcionEquipo(Integer idTorneoEquipo, RecyclerView recyclerView, LinearLayout layoutEmptyAbonos, TextView textViewCantidadAbonos){
        progressBarGoldesk.mostrarCargando(true);
        inscripcionRepository.obtenerAbonosInscripcionEquipo(idTorneoEquipo, new InscripcionRepository.InscripcionCallback<AbonoDetalleInscripcion>() {
            @Override
            public void onSuccess(List<AbonoDetalleInscripcion> inscripciones) {
                progressBarGoldesk.mostrarCargando(false);
                AdapterAbonosInscripcion adapter = new AdapterAbonosInscripcion(ContabilidadActivity.this, inscripciones);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
                layoutEmptyAbonos.setVisibility(View.GONE);
                textViewCantidadAbonos.setText(String.valueOf(inscripciones.size()));
            }

            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                recyclerView.setVisibility(View.GONE);
                layoutEmptyAbonos.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                recyclerView.setVisibility(View.GONE);
                layoutEmptyAbonos.setVisibility(View.VISIBLE);
            }
        });
    }
    public void abrirDialogDetallePagos(Object object, String concepto){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContabilidadActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detalle_pago, null);
        builder.setView(dialogView);

        //init components dialog
        DecimalFormat df = new DecimalFormat("$ #,###");
        TextView textViewTipoConceptoPago = dialogView.findViewById(R.id.tvTipoConceptoPago);
        TextView textViewNombreJugadorPago = dialogView.findViewById(R.id.tvNombreJugadorPago);
        TextView textViewEquipoJugadorPago = dialogView.findViewById(R.id.tvEquipoJugadorPago);
        TextView textViewMontoTotalPago = dialogView.findViewById(R.id.tvMontoTotalPago);
        TextView textViewYaPagadoPago = dialogView.findViewById(R.id.tvYaPagadoPago);
        TextView textViewSaldoPendientePago = dialogView.findViewById(R.id.tvSaldoPendientePago);
        TextView textViewCantidadAbonos = dialogView.findViewById(R.id.tvCantidadAbonos);
        RecyclerView recyclerViewAbonos = dialogView.findViewById(R.id.recyclerViewAbonos);
        LinearLayout layoutEmptyAbonos = dialogView.findViewById(R.id.layoutEmptyContabilidad);
        EditText editTextMontoPago = dialogView.findViewById(R.id.editTextMontoPago);
        Button btnRegistrarPago = dialogView.findViewById(R.id.btnRegistrarPago);
        Button btnCerrarDetallePago = dialogView.findViewById(R.id.btnCerrarDetallePago);

        Map<String, Object> map = new HashMap<>();
        if (object instanceof TarjetaTorneoResponse){
            TarjetaTorneoResponse tarjeta = (TarjetaTorneoResponse) object;
            textViewTipoConceptoPago.setText(concepto);
            textViewNombreJugadorPago.setText(tarjeta.getNombreCompletoJugador());
            textViewEquipoJugadorPago.setText(tarjeta.getNombreEquipo());
            textViewMontoTotalPago.setText(df.format(tarjeta.getValorTarjeta()));
            textViewYaPagadoPago.setText(df.format((tarjeta.getMontoAbonado())));
            textViewSaldoPendientePago.setText(df.format((tarjeta.getValorTarjeta() - tarjeta.getMontoAbonado())));
            //textViewCantidadAbonos.setText(tarjeta.ge);
            map.put("idTarjeta",tarjeta.getIdTarjeta());
        }else if (object instanceof InscripcionTorneoResponse){
            InscripcionTorneoResponse inscripcion = (InscripcionTorneoResponse) object;
            textViewTipoConceptoPago.setText(concepto);
            textViewNombreJugadorPago.setText(inscripcion.getNombreEquipo());
            textViewEquipoJugadorPago.setText(inscripcion.getEstadoPago());
            textViewMontoTotalPago.setText(df.format(inscripcion.getValorInscripcion()));
            textViewYaPagadoPago.setText(df.format(inscripcion.getMontoAbonado()));
            textViewSaldoPendientePago.setText(df.format(inscripcion.getValorInscripcion() - inscripcion.getMontoAbonado()));
            //textViewCantidadAbonos.setText(inscripcion.get);
            map.put("idTorneoEquipo", inscripcion.getIdTorneoEquipos());
            obtenerAbonosInscripcionEquipo(inscripcion.getIdTorneoEquipos(), recyclerViewAbonos, layoutEmptyAbonos, textViewCantidadAbonos);
        }

        AlertDialog dialog = builder.create();
        btnRegistrarPago.setOnClickListener(view -> {
            //lógica para registrar pago
            String monto = editTextMontoPago.getText().toString();

            if (monto.isEmpty()){
                editTextMontoPago.setError("Ingrese un monto");
                return;
            }
            Double montoDouble = Double.parseDouble(monto);
            map.put("monto", montoDouble);
            abrirDialogObservacion(map,dialog);
        });
        btnCerrarDetallePago.setOnClickListener(view -> {dialog.dismiss();});
        dialog.show();
    }
    private void abrirDialogObservacion(Map<String, Object> map, AlertDialog dialogDetallePagos){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContabilidadActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_observacion, null);
        builder.setView(dialogView);

        EditText editTextObservacion = dialogView.findViewById(R.id.editTextObservacion);
        Button btnCancelarObservacion = dialogView.findViewById(R.id.btnCancelarObservacion);
        Button btnAceptarObservacion = dialogView.findViewById(R.id.btnAceptarObservacion);

        AlertDialog dialog = builder.create();
        btnAceptarObservacion.setOnClickListener(view -> {
            String observacion = editTextObservacion.getText().toString();
            map.put("observacion", observacion);
            if (map.containsKey("idTarjeta")){
                registrarPagoTarjeta(map);
            }else {
                registrarPagoInscripcion(map);
            }
            dialog.dismiss();
            dialogDetallePagos.dismiss();
        });
        btnCancelarObservacion.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }
    private void registrarPagoTarjeta(Map<String, Object> map){
        progressBarGoldesk.mostrarCargando(true);
        tarjetaRepository.registrarPagoTarjetas(map, new TarjetaRepository.TarjetaCallback() {
            @Override
            public void onSuccess() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Pago registrado",
                        "El pago se registró correctamente"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void registrarPagoInscripcion(Map<String, Object> map){
        progressBarGoldesk.mostrarCargando(true);
        inscripcionRepository.registrarInscripcion(map, new InscripcionRepository.crearInscripcionCallback() {
            @Override
            public void onSuccess(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Pago registrado",
                        "El pago se registró correctamente"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void filtroEquipoInscripcion(String input){
        List<InscripcionTorneoResponse> listaFiltrada = new ArrayList<>();

        if (input == null || input.trim().isEmpty()){
            AdapterContabilidad adapterContabilidad = new AdapterContabilidad(null, ContabilidadActivity.this, listaInscripciones);
            recyclerViewContabilidad.setAdapter(adapterContabilidad);
            return;
        }

        String query = input.toLowerCase().trim();

        for (InscripcionTorneoResponse inscripcion : listaInscripciones){
            String equipo = inscripcion.getNombreEquipo() != null ? inscripcion.getNombreEquipo().toLowerCase() : "";
            if (equipo.contains(query)){
                listaFiltrada.add(inscripcion);
            }
        }
        AdapterContabilidad adapterContabilidad = new AdapterContabilidad(null, ContabilidadActivity.this, listaFiltrada);
        recyclerViewContabilidad.setAdapter(adapterContabilidad);
    }
    private void filtroEquipoJugador(String input) {
        List<TarjetaTorneoResponse> listaFiltrada = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            calcularTotalesTarjetas(listaTarjetas);
            AdapterContabilidad adapterContabilidad = new AdapterContabilidad(listaTarjetas, ContabilidadActivity.this, null);
            recyclerViewContabilidad.setAdapter(adapterContabilidad);
            return;
        }

        String query = input.toLowerCase().trim();

        for (TarjetaTorneoResponse tarjeta : listaTarjetas) {
            String equipo = tarjeta.getNombreEquipo() != null ? tarjeta.getNombreEquipo().toLowerCase() : "";
            String jugador = tarjeta.getNombreCompletoJugador() != null ? tarjeta.getNombreCompletoJugador().toLowerCase() : "";

            if (equipo.contains(query) || jugador.contains(query)) {
                listaFiltrada.add(tarjeta);
            }
        }
        calcularTotalesTarjetas(listaFiltrada);
        AdapterContabilidad adapterContabilidad = new AdapterContabilidad(listaFiltrada, ContabilidadActivity.this, null);
        recyclerViewContabilidad.setAdapter(adapterContabilidad);
    }
    private void calcularTotalesTarjetas(List<TarjetaTorneoResponse> listaTarjetas){
        DecimalFormat df = new DecimalFormat("$ #,###");
        double montoTotalAdeudadoTarjetas = 0;
        double montoTotalRecolectadoTarjetas = 0;
        for (TarjetaTorneoResponse tarjeta : listaTarjetas){
            montoTotalRecolectadoTarjetas += tarjeta.getMontoAbonado();
            montoTotalAdeudadoTarjetas += tarjeta.getValorTarjeta();
        }
        textViewMontoTotalAdeudado.setText(df.format(montoTotalAdeudadoTarjetas));
        textViewMontoTotalRecolectado.setText(df.format(montoTotalRecolectadoTarjetas));
    }
    private void calcularTotalesInscripciones(Integer idTorneo){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerResumenInscripcion(idTorneo, new TorneoRepository.BuscarCallback<ResumenInscripcion>() {
            @Override
            public void onSuccess(ResumenInscripcion response) {
                progressBarGoldesk.mostrarCargando(false);
                DecimalFormat df = new DecimalFormat("$ #,###");
                Double montoTotalAdeudadoInscripciones = response.getSaldoPendienteGeneral();
                Double montoTotalRecolectadoInscripciones = response.getTotalAbonado();
                textViewMontoTotalAdeudado.setText(df.format(montoTotalAdeudadoInscripciones));
                textViewMontoTotalRecolectado.setText(df.format(montoTotalRecolectadoInscripciones));
            }

            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Error al obtener totales de inscripcion",
                        mensaje);
            }
        });
    }
    private void filtrarTipoTarjeta(String tipoTarjeta){
        List<TarjetaTorneoResponse> lista = new ArrayList<>();
        for (TarjetaTorneoResponse tarjeta : listaTarjetas){
            if (tarjeta.getTipoTarjeta().equals(tipoTarjeta)){
                lista.add(tarjeta);
            }
        }
        if (!editTextFiltroEquipo.getText().toString().isEmpty()){
            editTextFiltroEquipo.setText("");
        }
        calcularTotalesTarjetas(lista);
        AdapterContabilidad adapterContabilidad = new AdapterContabilidad(lista, ContabilidadActivity.this, null);
        recyclerViewContabilidad.setAdapter(adapterContabilidad);
    }
    private void buscarTorneos(){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneosActivos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                progressBarGoldesk.mostrarCargando(false);
                spinnerTorneo.actualizarSpinnerTorneos(torneos, ContabilidadActivity.this, spinnerTorneoContabilidad);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentTorneos();
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void listarTarjetas(Integer idTorneo, String estadoPago){
        progressBarGoldesk.mostrarCargando(true);
        tarjetaRepository.listaTarjetas(idTorneo, estadoPago, new TarjetaRepository.ListaTarjetasCallback() {
            @Override
            public void onSuccess(List<TarjetasResponse> responses) {

            }

            @Override
            public void onSuccessList(List<TarjetaTorneoResponse> responses) {
                progressBarGoldesk.mostrarCargando(false);
                editTextFiltroEquipo.setText("");
                listaTarjetas.clear();
                listaTarjetas.addAll(responses);
                AdapterContabilidad adapterContabilidad = new AdapterContabilidad(listaTarjetas, ContabilidadActivity.this, null);
                recyclerViewContabilidad.setAdapter(adapterContabilidad);
                calcularTotalesTarjetas(responses);
                recyclerViewContabilidad.setVisibility(View.VISIBLE);
                layoutEmptyContabilidad.setVisibility(View.GONE);
            }

            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                listaTarjetas.clear();
                editTextFiltroEquipo.setText("");
                textViewMontoTotalAdeudado.setText("$ 0");
                textViewMontoTotalRecolectado.setText("$ 0");
                dialogsResponse.mostrarDialogoInformacion(
                        "No hay tarjetas",
                        "No hay tarjetas para mostrar"
                );
                recyclerViewContabilidad.setVisibility(View.GONE);
                layoutEmptyContabilidad.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                listaTarjetas.clear();
                editTextFiltroEquipo.setText("");
                textViewMontoTotalAdeudado.setText("$ 0");
                textViewMontoTotalRecolectado.setText("$ 0");
                recyclerViewContabilidad.setVisibility(View.GONE);
                layoutEmptyContabilidad.setVisibility(View.VISIBLE);
            }
        });
    }
    private void listarInscripnes(Integer idTorneo, String estadoPago){
        progressBarGoldesk.mostrarCargando(true);
        inscripcionRepository.obtenerInscripcionesPorTorneo(idTorneo, estadoPago, new InscripcionRepository.InscripcionCallback<InscripcionTorneoResponse>() {
            @Override
            public void onSuccess(List<InscripcionTorneoResponse> inscripciones) {
                progressBarGoldesk.mostrarCargando(false);
                editTextFiltroEquipo.setText("");
                listaInscripciones.clear();
                listaInscripciones.addAll(inscripciones);
                calcularTotalesInscripciones(idTorneo);
                AdapterContabilidad adapterContabilidad = new AdapterContabilidad(null, ContabilidadActivity.this, inscripciones);
                recyclerViewContabilidad.setAdapter(adapterContabilidad);
                recyclerViewContabilidad.setVisibility(View.VISIBLE);
                layoutEmptyContabilidad.setVisibility(View.GONE);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                editTextFiltroEquipo.setText("");
                textViewMontoTotalAdeudado.setText("$ 0");
                textViewMontoTotalRecolectado.setText("$ 0");
                dialogsResponse.mostrarDialogoInformacion(
                        "No hay inscripciones",
                        "No hay inscripciones para mostrar"
                );
                recyclerViewContabilidad.setVisibility(View.GONE);
                layoutEmptyContabilidad.setVisibility(View.VISIBLE);
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                editTextFiltroEquipo.setText("");
                textViewMontoTotalAdeudado.setText("$ 0");
                textViewMontoTotalRecolectado.setText("$ 0");
                recyclerViewContabilidad.setVisibility(View.GONE);
                layoutEmptyContabilidad.setVisibility(View.VISIBLE);
            }
        });
    }

}