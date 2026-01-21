package com.ddrd.goldeskapp.ui.planillaDigital;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.gol.GolCreate;
import com.ddrd.goldeskapp.data.model.gol.GolResponse;
import com.ddrd.goldeskapp.data.model.jugador.JugadorPlanillaResponse;
import com.ddrd.goldeskapp.data.model.planillaDigital.PlanillaDigitalResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoResponse;
import com.ddrd.goldeskapp.data.repository.GolRepository;
import com.ddrd.goldeskapp.data.repository.PartidoRepository;
import com.ddrd.goldeskapp.data.repository.TarjetaRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.goles.AdapterGolesParticipacion;
import com.ddrd.goldeskapp.ui.tarjetas.AdapterTarjetasParticipacion;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class PlanillaDigitalActivity extends AppCompatActivity {
    private PlanillaDigitalResponse planillaDigitalResponse;
    private JugadorPlanillaResponse jugadorPlanillaResponse;
    private FormatearFechaHoraUser formatearFechaHoraUser;
    private AdapterJugadoresPlanilla adapterJugadoresPlanilla;
    private TorneoRepository torneoRepository;
    private PartidoRepository partidoRepository;
    private GolRepository golRepository;
    private TarjetaRepository tarjetaRepository;
    private ProgressBarGoldesk progressBarGoldesk;
    private DialogsResponse dialogsResponse;
    private TextView textViewMatchStatus, textViewChampionship, textViewMatchDate, textViewStadium,
            textViewTeam2Score,textViewTeam1Score,
            textViewTeam1Name, textViewTeam2Name,
            textViewTeam1pagoArbitraje, textViewTeam2pagoArbitraje;
    private SwitchCompat switchTeam1Payment, switchTeam2Payment;
    private Button btnStartMatch, btnEndMatch;
    private RecyclerView recyclerViewTeam1Players, recyclerViewTeam2Players;
    private double valorAmarilla, valorAzul, valorRoja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_planilla_digital);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents(){
        progressBarGoldesk = new ProgressBarGoldesk(this);
        dialogsResponse = new DialogsResponse(this);
        formatearFechaHoraUser = new FormatearFechaHoraUser();
        //data object init
        planillaDigitalResponse = new PlanillaDigitalResponse();
        jugadorPlanillaResponse = new JugadorPlanillaResponse();
        //repository init
        torneoRepository = new TorneoRepository(PlanillaDigitalActivity.this);
        partidoRepository = new PartidoRepository(PlanillaDigitalActivity.this);
        golRepository = new GolRepository(PlanillaDigitalActivity.this);
        tarjetaRepository = new TarjetaRepository(PlanillaDigitalActivity.this);
        //textviews init
        textViewMatchStatus = findViewById(R.id.textViewMatchStatus);
        textViewChampionship = findViewById(R.id.textViewChampionship);
        textViewMatchDate = findViewById(R.id.textViewMatchDate);
        textViewStadium = findViewById(R.id.textViewStadium);
            //equipo 1
        textViewTeam1Score = findViewById(R.id.textViewTeam1Score);
        textViewTeam1Name = findViewById(R.id.textViewTeam1Name);
        textViewTeam1pagoArbitraje = findViewById(R.id.textViewTeam1pagoArbitraje);
            //equipo 2
        textViewTeam2Score = findViewById(R.id.textViewTeam2Score);
        textViewTeam2Name = findViewById(R.id.textViewTeam2Name);
        textViewTeam2pagoArbitraje = findViewById(R.id.textViewTeam2pagoArbitraje);
        //switch init
        switchTeam1Payment = findViewById(R.id.switchTeam1Payment);
        switchTeam2Payment = findViewById(R.id.switchTeam2Payment);
        //button init
        btnStartMatch = findViewById(R.id.btnStartMatch);
        btnEndMatch = findViewById(R.id.btnEndMatch);
        //recycler init
        recyclerViewTeam1Players = findViewById(R.id.recyclerViewTeam1Players);
        recyclerViewTeam1Players.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTeam2Players = findViewById(R.id.recyclerViewTeam2Players);
        recyclerViewTeam2Players.setLayoutManager(new LinearLayoutManager(this));



        //recibir intent
        Integer idPartido = getIntent().getIntExtra("idPartido", 0);
        obtenerDatosPartido(idPartido);




    }
    public void obtenerDatosPartido(Integer idPartido){
        progressBarGoldesk.mostrarCargando(true);
        partidoRepository.abrirPlanillaDigital(idPartido, new PartidoRepository.PartidoBuscarCalback() {
            @Override
            public void onSuccess(PlanillaDigitalResponse response) {
                progressBarGoldesk.mostrarCargando(false);
                planillaDigitalResponse = response;
                llenarPlanilla(planillaDigitalResponse);
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });

    }
    private void llenarPlanilla(PlanillaDigitalResponse planillaDigitalResponse){
        //información general del partido
        textViewMatchStatus.setText(planillaDigitalResponse.getEstado());
        String fecha = formatearFechaHoraUser.formatearFechaUser(planillaDigitalResponse.getFechaPartido());
        String hora = formatearFechaHoraUser.formatearHoraUser(planillaDigitalResponse.getHoraPartido());
        String fechaHora = fecha+" - "+hora;
        textViewChampionship.setText(planillaDigitalResponse.getNombreTorneo());
        textViewMatchDate.setText(fechaHora);
        textViewStadium.setText(planillaDigitalResponse.getNombreCancha());
        //estado del partido
        configBotones(planillaDigitalResponse);

        //información equipo local
        textViewTeam1Score.setText(String.valueOf(planillaDigitalResponse.getGolesLocal()));
        textViewTeam1Name.setText(planillaDigitalResponse.getNombreEquipoLocal());
        textViewTeam1pagoArbitraje.setText(String.valueOf(planillaDigitalResponse.getPagoArbitrajeLocal()));
            //estado pago arbitraje equipo local
        switchTeam1Payment.setChecked(planillaDigitalResponse.getArbPagadoLocal());

            //jugadores equipo local
        AdapterJugadoresPlanilla adapterJugadoresLocal = new AdapterJugadoresPlanilla(
                PlanillaDigitalActivity.this,
                planillaDigitalResponse.getJugadoresLocal());
        recyclerViewTeam1Players.setAdapter(adapterJugadoresLocal);

        //información equipo visitante
        textViewTeam2Score.setText(String.valueOf(planillaDigitalResponse.getGolesVisitante()));
        textViewTeam2Name.setText(planillaDigitalResponse.getNombreEquipoVisitante());
        textViewTeam2pagoArbitraje.setText(String.valueOf(planillaDigitalResponse.getPagoArbitrajeVisitante()));
            //estado pago arbitraje equipo visitante
        switchTeam2Payment.setChecked(planillaDigitalResponse.getArbPagadoVisitante());

            //jugadores equipo visitante
        AdapterJugadoresPlanilla adapterJugadoresVisitante = new AdapterJugadoresPlanilla(
                PlanillaDigitalActivity.this,
                planillaDigitalResponse.getJugadoresVisitante());
        recyclerViewTeam2Players.setAdapter(adapterJugadoresVisitante);
    }

    private void registrarGol(GolCreate golCreate){
        progressBarGoldesk.mostrarCargando(true);
        golRepository.registrarGol(golCreate, new GolRepository.GolCallback() {
            @Override
            public void onSuccess() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Gol registrado",
                        "Gol registrado correctamente");
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });

    }
    private void registrarTarjeta(TarjetaCreate tarjetaCreate){
        progressBarGoldesk.mostrarCargando(true);
        tarjetaRepository.registrarTarjeta(tarjetaCreate, new TarjetaRepository.TarjetaCallback() {
            @Override
            public void onSuccess() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Tarjeta registrada",
                        "Tarjeta registrada correctamente");
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }

    private void listaTarjetas(Integer idParticipacion, String tipoTarjeta, RecyclerView recyclerView, TextView tvEmptyMessage){
        if (tipoTarjeta.isEmpty()){
            dialogsResponse.mostrarDialogoError("Error al listar tarjetas");
            return;
        }
        progressBarGoldesk.mostrarCargando(true);
        tarjetaRepository.buscarTarjetasPorJugador(idParticipacion,tipoTarjeta, new TarjetaRepository.ListaTarjetasCallback() {
            @Override
            public void onSuccess(List<TarjetasResponse> responses) {
                progressBarGoldesk.mostrarCargando(false);
                if (responses.isEmpty()){
                    tvEmptyMessage.setVisibility(View.VISIBLE);
                }else {
                    tvEmptyMessage.setVisibility(View.GONE);
                }
                AdapterTarjetasParticipacion adapterTarjetasParticipacion = new AdapterTarjetasParticipacion(
                        PlanillaDigitalActivity.this,
                        responses);
                recyclerView.setAdapter(adapterTarjetasParticipacion);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoInformacion(
                        "Sin tarjetas",
                        "Aún No hay tarjetas registradas");
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void listarGoles(Integer idParticipacion, RecyclerView recyclerView, TextView tvEmptyMessage){
        progressBarGoldesk.mostrarCargando(true);
        golRepository.listarGolesPorParticipacion(idParticipacion, new GolRepository.ListaGolesCallback() {
            @Override
            public void onSuccess(List<GolResponse> responses) {
                progressBarGoldesk.mostrarCargando(false);
                if (responses.isEmpty()){
                    tvEmptyMessage.setVisibility(View.VISIBLE);
                }else {
                    tvEmptyMessage.setVisibility(View.GONE);
                }
                AdapterGolesParticipacion adapterGolesParticipacion = new AdapterGolesParticipacion(
                        PlanillaDigitalActivity.this,
                        responses);
                recyclerView.setAdapter(adapterGolesParticipacion);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoInformacion(
                        "Sin Goles",
                        "Aún No hay goles registrados");
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }

    public void mostrarVentadaInsertEvent(GolCreate golCreate, TarjetaCreate tarjetaCreate, String dorsal, String nombreJugador){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_config_goles_tarjetas, null);

        //init textViews
        TextView textViewEquipo = view.findViewById(R.id.nombreEquipoEstats);
        TextView textViewDorsal = view.findViewById(R.id.dorsalJugadorEstats);
        TextView textViewNombre = view.findViewById(R.id.nombreJugadorEstats);
        TextView tvGolesParticipante = view.findViewById(R.id.tvGolesParticipante);
        TextView tvAmarillasParticipante = view.findViewById(R.id.tvAmarillasParticipante);
        TextView tvAzulesParticipante = view.findViewById(R.id.tvAzulesParticipante);
        TextView tvRojasParticipante = view.findViewById(R.id.tvRojasParticipante);

        //init Buttons
        ImageButton btnSumarGol = view.findViewById(R.id.btnSumarGol);
        ImageButton btnSumarAmarilla = view.findViewById(R.id.btnSumarAmarilla);
        ImageButton btnSumarAzul = view.findViewById(R.id.btnSumarAzul);
        ImageButton btnSumarRoja = view.findViewById(R.id.btnSumarRoja);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        //llenar textViews
        textViewEquipo.setText(textViewTeam1Name.getText().toString());
        textViewDorsal.setText(dorsal);
        textViewNombre.setText(nombreJugador);

        //configurar botones
        btnSumarGol.setOnClickListener(b->mostrarVentanaRegistrarTiempo(nombreJugador, golCreate, tarjetaCreate, btnSumarGol));
        btnSumarAmarilla.setOnClickListener(b->mostrarVentanaRegistrarTiempo(nombreJugador, golCreate, tarjetaCreate, btnSumarAmarilla));
        btnSumarAzul.setOnClickListener(b->mostrarVentanaRegistrarTiempo(nombreJugador, golCreate, tarjetaCreate, btnSumarAzul));
        btnSumarRoja.setOnClickListener(b->mostrarVentanaRegistrarTiempo(nombreJugador, golCreate, tarjetaCreate, btnSumarRoja));

        //configurar click textviews
        tvGolesParticipante.setOnClickListener(
                b->mostrarVentanaListaEventos(
                        nombreJugador, tvGolesParticipante, golCreate.getIdParticipacion()));
        tvAmarillasParticipante.setOnClickListener(
                b->mostrarVentanaListaEventos(
                        nombreJugador, tvAmarillasParticipante, golCreate.getIdParticipacion()));
        tvAzulesParticipante.setOnClickListener(
                b->mostrarVentanaListaEventos(
                        nombreJugador, tvAzulesParticipante, golCreate.getIdParticipacion()));
        tvRojasParticipante.setOnClickListener(
                b->mostrarVentanaListaEventos(
                        nombreJugador, tvRojasParticipante, golCreate.getIdParticipacion()));

        builder.setView(view);
        AlertDialog dialog = builder.create();
        btnCancelar.setOnClickListener(b->dialog.dismiss());
        dialog.show();
    }
    public void mostrarVentanaRegistrarTiempo(String nombreJugador, GolCreate golCreate, TarjetaCreate tarjetaCreate, ImageButton btn){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_registrar_tiempo, null);

        buscarDatosTorneo(planillaDigitalResponse);

        //init spinner
        Spinner spinnerPeriodoPartido = view.findViewById(R.id.spinnerPeriodoPartido);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.periodo_partido,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodoPartido.setAdapter(adapter);

        //init textViews
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextInputEditText et_Min = view.findViewById(R.id.etMinutos);
        TextInputEditText et_Seg = view.findViewById(R.id.etSegundos);

        TextView tvNombreJugador = view.findViewById(R.id.tvNombreJugador);
        tvNombreJugador.setText(nombreJugador);


        //init Buttons
        Button btnCancelar = view.findViewById(R.id.btnCancelar);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);

        double valorTarjeta = 0.0;

        if (btn.getId() == R.id.btnSumarGol){
            tvTitulo.setText("⚽ Registrar Gol");
        } else if (btn.getId() == R.id.btnSumarAmarilla) {
            tvTitulo.setText("\uD83D\uDFE8 Registrar Amarilla");
            tarjetaCreate.setTipoTarjeta("AMARILLA");
            valorTarjeta = valorAmarilla;
        } else if (btn.getId() == R.id.btnSumarAzul) {
            tvTitulo.setText("\uD83D\uDFE6 Registrar Azul");
            tarjetaCreate.setTipoTarjeta("AZUL");
            valorTarjeta = valorAzul;
        } else if (btn.getId() == R.id.btnSumarRoja) {
            tvTitulo.setText("\uD83D\uDFE5 Registrar Roja");
            tarjetaCreate.setTipoTarjeta("ROJA");
            valorTarjeta = valorRoja;
        }

        //configurar botones
        double finalValorTarjeta = valorTarjeta;
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_Min.getText().toString().isEmpty()){
                    et_Min.setError("Ingrese un valor válido");
                    return;
                } else if (et_Seg.getText().toString().isEmpty()) {
                    et_Seg.setError("Ingrese un valor válido");
                    return;
                }
                String periodoPartido = spinnerPeriodoPartido.getSelectedItem().toString();
                String tiempoEvento = Objects.requireNonNull(et_Min.getText(),"Debe Escribir un valor")+"' "+ Objects.requireNonNull(et_Seg.getText(),"Debe Escribir un valor");
                if (btn.getId() == R.id.btnSumarGol){
                    golCreate.setPeriodoPartido(periodoPartido);
                    golCreate.setTiempoEvento(tiempoEvento);
                    registrarGol(golCreate);
                } else {
                    tarjetaCreate.setPeriodoPartido(periodoPartido);
                    tarjetaCreate.setTiempoEvento(tiempoEvento);
                    tarjetaCreate.setValorTarjeta(finalValorTarjeta);
                    registrarTarjeta(tarjetaCreate);
                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cerrar el dialog
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void mostrarVentanaListaEventos(String nombreJugador, TextView textView, Integer idParticipacion){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_estadisticas_lista, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        //init Texviews
        TextView tvEquipo = view.findViewById(R.id.tvEquipo);
        TextView tvJugador = view.findViewById(R.id.tvJugador);
        TextView tvTituloSeccion = view.findViewById(R.id.tvTituloSeccion);
        TextView tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage);

        tvEquipo.setText(textViewTeam1Name.getText().toString());
        tvJugador.setText(nombreJugador);
        tvTituloSeccion.setText(textView.getText().toString());

        //init RecyclerView
        RecyclerView rvEstadisticas = view.findViewById(R.id.rvEstadisticas);
        rvEstadisticas.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if (textView.getId() == R.id.tvGolesParticipante){
            listarGoles(idParticipacion, rvEstadisticas, tvEmptyMessage);
        }else {
            String tipoTarjeta="";
            if (textView.getId() == R.id.tvAmarillasParticipante){
                tipoTarjeta= "amarilla";
            } else if (textView.getId() == R.id.tvAzulesParticipante) {
                tipoTarjeta = "azul";
            } else if (textView.getId() == R.id.tvRojasParticipante) {
                tipoTarjeta = "roja";
            }
            listaTarjetas(idParticipacion, tipoTarjeta, rvEstadisticas, tvEmptyMessage);
        }


        //init Buttons
        Button btnCerrar = view.findViewById(R.id.btnCerrar);


        btnCerrar.setOnClickListener(b->dialog.dismiss());
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

    }

    private void buscarDatosTorneo(PlanillaDigitalResponse planilla){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneoPorId(planilla.getIdTorneo(), new TorneoRepository.TorneoBuscarCallback() {
            @Override
            public void onSuccess(TorneoResponse response) {
                progressBarGoldesk.mostrarCargando(false);
                valorAmarilla = response.getValorAmarilla();
                valorAzul = response.getValorAzul();
                valorRoja = response.getValorRoja();
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void configBotones(PlanillaDigitalResponse planillaDigitalResponse){
        if(planillaDigitalResponse.getEstado().equals("FINALIZADO")){
            textViewMatchStatus.setBackgroundColor(ContextCompat.getColor(
                    PlanillaDigitalActivity.this,R.color.partido_finalizado
            ));
            btnStartMatch.setEnabled(false);
            btnEndMatch.setEnabled(false);
            //ocultar boton iniciar y finalizar partido
            btnStartMatch.setVisibility(View.GONE);
            btnEndMatch.setVisibility(View.GONE);
            //deshabilitar campos de pago arbitraje y jugadores
            switchTeam1Payment.setEnabled(false);
            switchTeam2Payment.setEnabled(false);
            textViewTeam1pagoArbitraje.setEnabled(false);
            textViewTeam2pagoArbitraje.setEnabled(false);
        } else if (planillaDigitalResponse.getEstado().equals("EN CURSO")) {
            textViewMatchStatus.setBackgroundColor(ContextCompat.getColor(
                    PlanillaDigitalActivity.this,R.color.partido_en_curso
            ));
            btnStartMatch.setEnabled(false);
            btnEndMatch.setEnabled(true);
            //ocultar boton iniciar partido
            btnStartMatch.setVisibility(View.GONE);
            btnEndMatch.setVisibility(View.VISIBLE);
            //deshabilitar campos de pago arbitraje y jugadores
            switchTeam1Payment.setEnabled(true);
            switchTeam2Payment.setEnabled(true);
            textViewTeam1pagoArbitraje.setEnabled(true);
            textViewTeam2pagoArbitraje.setEnabled(true);
        } else if (planillaDigitalResponse.getEstado().equals("PROGRAMADO")) {
            textViewMatchStatus.setBackgroundColor(ContextCompat.getColor(
                    PlanillaDigitalActivity.this,R.color.partido_programado
            ));
            btnStartMatch.setEnabled(true);
            btnEndMatch.setEnabled(false);
            //mostrar boton finalizar partido
            btnStartMatch.setVisibility(View.VISIBLE);
            btnEndMatch.setVisibility(View.GONE);
            //habilitar campos de pago arbitraje y jugadores
            switchTeam1Payment.setEnabled(true);
            switchTeam2Payment.setEnabled(true);
            textViewTeam1pagoArbitraje.setEnabled(true);
            textViewTeam2pagoArbitraje.setEnabled(true);
        }
    }
}