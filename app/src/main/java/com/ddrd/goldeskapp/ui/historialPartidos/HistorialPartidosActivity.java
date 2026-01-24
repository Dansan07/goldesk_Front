package com.ddrd.goldeskapp.ui.historialPartidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.equipo.SpinnerEquipoResponse;
import com.ddrd.goldeskapp.data.model.partido.FiltroHistorialPartidos;
import com.ddrd.goldeskapp.data.model.partido.PartidosHistorialResponse;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.EquipoRepository;
import com.ddrd.goldeskapp.data.repository.PartidoRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.programarPartidos.ProgramarPartidosActivity;
import com.ddrd.goldeskapp.ui.utilities.Calendario;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraServer;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraUser;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialPartidosActivity extends AppCompatActivity {

    private PartidoRepository partidoRepository;
    private TorneoRepository torneoRepository;
    private EquipoRepository equipoRepository;
    private SpinnerTorneo spinnerTorneo;
    private Spinner spinnerFilterChampionship;
    private DialogsResponse dialogsResponse;
    private AutoCompleteTextView editTextFilterTeam;
    private EditText editTextFechaInicio, editTextFechaFin;
    private TextView textViewResultCount;
    private FormatearFechaHoraServer formatearFechaHoraServer;
    private FormatearFechaHoraUser formatearFechaHoraUser;
    private ProgressBarGoldesk progressBarGoldesk;
    private RecyclerView recyclerViewPartidos;
    private Calendario calendario;
    private Button btnApplyFilters;
    private ImageButton btnClearFilters, btnRefreshPartidos;
    private FloatingActionButton fabAddMatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial_partidos);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarSpinnerTorneos();
    }

    private void refreshPartidos(){
        obtenerHistorialPartidos(obtenerDatosFiltro());
    }

    private void initComponents(){
        formatearFechaHoraServer = new FormatearFechaHoraServer();
        formatearFechaHoraUser = new FormatearFechaHoraUser();
        progressBarGoldesk = new ProgressBarGoldesk(this);
        calendario = new Calendario(this);
        textViewResultCount = findViewById(R.id.textViewResultCount);
        //buttons init
        btnApplyFilters = findViewById(R.id.btnApplyFilters);
        btnClearFilters = findViewById(R.id.btnClearFilters);
        btnRefreshPartidos = findViewById(R.id.btnRefreshPartidos);
        fabAddMatch = findViewById(R.id.fabAddMatch);
        //repository init
        partidoRepository= new PartidoRepository(this);
        torneoRepository = new TorneoRepository(this);
        equipoRepository = new EquipoRepository(this);
        //spinner init
        spinnerTorneo = new SpinnerTorneo();
        spinnerFilterChampionship = findViewById(R.id.spinnerFilterChampionship);
        dialogsResponse = new DialogsResponse(this);
        //edittext init
        editTextFilterTeam = findViewById(R.id.editTextFilterTeam);
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio);
        editTextFechaFin = findViewById(R.id.editTextFechaFin);
        //recyclerview init
        recyclerViewPartidos = findViewById(R.id.recyclerViewPartidos);
        recyclerViewPartidos.setLayoutManager(new LinearLayoutManager(this));
        //filtro init

        //click listeners
        clickListener();

    }
    private void clickListener(){
        spinnerFilterChampionship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FiltroHistorialPartidos filtro=obtenerDatosFiltro();
                obtenerHistorialPartidos(filtro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiltroHistorialPartidos filtro=obtenerDatosFiltro();
                obtenerHistorialPartidos(filtro);
            }
        });
        btnClearFilters.setOnClickListener(v -> {limpiarFiltros();});
        btnRefreshPartidos.setOnClickListener(v -> {
            v.animate().rotationBy(360).setDuration(1000).start();
            refreshPartidos();
        });
        fabAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HistorialPartidosActivity.this, ProgramarPartidosActivity.class);
                startActivity(intent);
            }
        });
        editTextFechaInicio.setOnClickListener(v -> calendario.abrirCalendario(editTextFechaInicio));
        editTextFechaFin.setOnClickListener(v -> calendario.abrirCalendario(editTextFechaFin));
    }

    private void mostrarSpinnerTorneos(){
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                spinnerTorneo.actualizarSpinnerTorneos(torneos, HistorialPartidosActivity.this, spinnerFilterChampionship);
            }
            @Override
            public void onNoContent() {
                dialogsResponse.mostrarDialogoNoContentTorneos(
                        "Torneos",
                        "No tienes torneos activos.",
                        "Crear Torneo"
                );
            }
            @Override
            public void onError(String mensaje) {
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    //sugiere una lista de equipos en base al torneo seleccionado
    private void crearlistaEquipos(){
        SpinnerTorneoResponse torneo= (SpinnerTorneoResponse) spinnerFilterChampionship.getSelectedItem();
        equipoRepository.obtenerEquiposSpinner(torneo.getIdTorneo(), new EquipoRepository.EquipoCallback() {
            @Override
            public void onSuccess(List<SpinnerEquipoResponse> equipos) {
                ArrayAdapter<SpinnerEquipoResponse> adapter = new ArrayAdapter<>(
                        HistorialPartidosActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        equipos
                );
                editTextFilterTeam.setAdapter(adapter);
            }

            @Override
            public void onNoContent() {

            }

            @Override
            public void onError(String mensaje) {

            }
        });
    }
    private FiltroHistorialPartidos obtenerDatosFiltro() {
        //implementar logica para obtener los datos del filtro
        SpinnerTorneoResponse torneo= (SpinnerTorneoResponse) spinnerFilterChampionship.getSelectedItem();
        String nombreEquipo = editTextFilterTeam.getText().toString().trim().isEmpty()?
                null: editTextFilterTeam.getText().toString().trim();
        String fechaInicio = editTextFechaInicio.getText().toString().trim().isEmpty()?
                null: editTextFechaInicio.getText().toString().trim();
        String fechaFin = editTextFechaFin.getText().toString().trim().isEmpty()?
                null: editTextFechaFin.getText().toString().trim();

        if (fechaInicio != null){
            fechaInicio= formatearFechaHoraServer.formatearFechaServer(fechaInicio);
        }
        if (fechaFin != null){
            fechaFin= formatearFechaHoraServer.formatearFechaServer(fechaFin);
        }
        if (fechaInicio!=null && (fechaFin==null || fechaFin.isEmpty())){
            fechaFin = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US).format(new Date());
            editTextFechaFin.setText(formatearFechaHoraUser.formatearFechaUser(fechaFin));
        }
        //crear objeto con los datos
        return new FiltroHistorialPartidos(
                torneo.getIdTorneo(),
                nombreEquipo,
                fechaInicio,
                fechaFin
        );
    }
    private void obtenerHistorialPartidos(FiltroHistorialPartidos filtro){
        progressBarGoldesk.mostrarCargando(true);
        partidoRepository.obtenerHistorialPartidos(filtro, new PartidoRepository.PartidoHistorialCalback() {
            @Override
            public void onSuccess(List<PartidosHistorialResponse> partidos) {
                if (!isFinishing()){
                    progressBarGoldesk.mostrarCargando(false);
                    String cantidadPartidos = getString(R.string.mostrando_resultados,partidos.size());
                    textViewResultCount.setText(cantidadPartidos);
                    actualizarRecyclerView(partidos);
                    crearlistaEquipos();
                }
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentPartidos(
                        "Sin Partidos",
                        "No tienes partidos registrados",
                        "Programar un Partido"
                );
                recyclerViewPartidos.setAdapter(null);
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
                recyclerViewPartidos.setAdapter(null);
            }
        });
    }

    private void actualizarRecyclerView(List<PartidosHistorialResponse> partidos) {
        AdapterHistorialPartidos adapter = new AdapterHistorialPartidos(this, partidos);
        recyclerViewPartidos.setAdapter(adapter);
    }
    private void limpiarFiltros(){
        spinnerFilterChampionship.setSelection(0);
        editTextFilterTeam.setText("");
        editTextFechaInicio.setText("");
        editTextFechaFin.setText("");
    }

}