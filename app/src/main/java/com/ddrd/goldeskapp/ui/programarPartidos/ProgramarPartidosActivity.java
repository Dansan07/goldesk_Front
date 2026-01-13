package com.ddrd.goldeskapp.ui.programarPartidos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.equipo.SpinnerEquipoResponse;
import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidoSave;
import com.ddrd.goldeskapp.data.repository.EquipoRepository;
import com.ddrd.goldeskapp.data.repository.PartidoRepository;
import com.ddrd.goldeskapp.ui.utilities.Calendario;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogProgramarPartido;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBar;
import com.ddrd.goldeskapp.ui.utilities.formatos.FomatearFechaHora;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.ddrd.goldeskapp.util.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class ProgramarPartidosActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private TorneoRepository torneoRepository;
    private EquipoRepository equipoRepository;
    private PartidoRepository partidoRepository;
    private Spinner spinnerChampionship, spinnerLocalTeam,spinnerVisitanteTeam;
    private SpinnerTorneo spinnerTorneo;
    private DialogProgramarPartido dialogProgramarPartido;
    private ProgressBar progressBar;
    private List<SpinnerEquipoResponse> listaEquiposOriginal;
    private EditText editTextDate, editTextTime, editTextLocation;
    private Calendario calendario;
    private Button btnCancel, btnSave;
    private FomatearFechaHora fomatearFechaHora;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_programar_partidos);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents(){
        tokenManager = new TokenManager(this);
        listaEquiposOriginal = new ArrayList<>();
        calendario = new Calendario(this);
        fomatearFechaHora = new FomatearFechaHora();
        //repository init
        torneoRepository = new TorneoRepository(this);
        equipoRepository = new EquipoRepository(this);
        partidoRepository = new PartidoRepository(this);
        //spinner init
        spinnerTorneo = new SpinnerTorneo();
        spinnerChampionship = findViewById(R.id.spinnerChampionship);
        spinnerLocalTeam = findViewById(R.id.spinnerLocalTeam);
        spinnerVisitanteTeam = findViewById(R.id.spinnerVisitanteTeam);
        //edittext init
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextLocation = findViewById(R.id.editTextLocation);
        //dialog init
        dialogProgramarPartido = new DialogProgramarPartido(this);
        progressBar = new ProgressBar(this);
        //button init
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);


        cargarTorneos();
        clickListeners();
    }

    private void clickListeners() {
        editTextDate.setOnClickListener(v -> calendario.abrirCalendario(editTextDate));
        editTextTime.setOnClickListener(v -> calendario.abrirReloj(editTextTime));
        // Configuramos el listener para que el Local reaccione al cambio del Torneo
        spinnerChampionship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTorneoResponse selection= (SpinnerTorneoResponse) parent.getSelectedItem();
                if (selection != null) {
                    //logica para mostrar los equipos diponibles por campeonato
                    cargarEquipos(selection.getIdTorneo());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Configuramos el listener para que el Visitante reaccione al cambio del Local
        spinnerLocalTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerEquipoResponse seleccionadoLocal = (SpinnerEquipoResponse) parent.getSelectedItem();
                refrescarSpinnerVisitante(seleccionadoLocal);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        btnCancel.setOnClickListener(v -> {
            dialogProgramarPartido.mostrarDialogoCancelar(
                    "Cancelar Partido",
                    "Si cancelas se limpiará toda la información para este partido" +
                            "¿Estás seguro de cancelar?",
                    "Sí, salir"
            );

        });
        btnSave.setOnClickListener(v -> guardarPartido(false));
    }

    private PartidoSave obtenerDatosPartido(boolean confirmarDuplicado) {
        try {
            SpinnerTorneoResponse torneo = (SpinnerTorneoResponse) spinnerChampionship.getSelectedItem();
            SpinnerEquipoResponse local = (SpinnerEquipoResponse) spinnerLocalTeam.getSelectedItem();
            SpinnerEquipoResponse visitante = (SpinnerEquipoResponse) spinnerVisitanteTeam.getSelectedItem();

            String fecha= editTextDate.getText().toString();
            String hora= editTextTime.getText().toString();

            PartidoSave partidoSave= new PartidoSave(
                    torneo.getIdTorneo(),
                    local.getIdTorneoEquipo(),
                    visitante.getIdTorneoEquipo(),
                    fomatearFechaHora.formatearFecha(fecha), // Ahora es un String "2026-01-12"
                    fomatearFechaHora.formatearHora(hora),  // Ahora es un String "15:30:00"
                    editTextLocation.getText().toString(),
                    "Fase de Inicial",
                    confirmarDuplicado
            );
            Log.d("Datos Partido", "obtenerDatosPartido: "+partidoSave.toString());
            return partidoSave;

        } catch (Exception e) {
            Log.e("Error_Programar", "Error formateando: " + e.getMessage());
            return null;
        }
    }

    public void guardarPartido(boolean confirmarDuplicado){
        PartidoSave partido = obtenerDatosPartido(confirmarDuplicado);
        if (partido == null) {
            Toast.makeText(this, "Por favor, completa la información correctamente", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.mostrarCargando(true);
        partidoRepository.programarPartido(partido, new PartidoRepository.PartidoGuardarCalback() {
            @Override
            public void onSuccess(PartidoResponseDuplicate response) {
                progressBar.mostrarCargando(false);

                switch (response.getStatus()){
                    case "SUCCESS":
                        dialogProgramarPartido.mostrarDialogoInformacion(
                                "Partido Guardado",
                                response.getMessage(),
                                ProgramarPartidosActivity.this
                        );
                        break;
                    case "WARNING_DUPLICATE":
                        dialogProgramarPartido.mostrarDialogoDuplicado(
                                "Partido Duplicado",
                                response.getMessage(),
                                ProgramarPartidosActivity.this
                        );
                        break;
                    case "ERROR":
                        dialogProgramarPartido.mostrarDialogoError(
                                response.getMessage()
                        );
                    default:
                }

            }
            @Override
            public void onError(String mensaje) {

            }
        });
    }

    private void actualizarSpinnerEquipos(List<SpinnerEquipoResponse> equipos) {
        this.listaEquiposOriginal = equipos; // Guardamos la referencia [cite: 2025-12-28]

        // Llenamos el Local con todos los equipos
        ArrayAdapter<SpinnerEquipoResponse> adapterLocal = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                equipos);
        adapterLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocalTeam.setAdapter(adapterLocal);
    }

    private void refrescarSpinnerVisitante(SpinnerEquipoResponse equipoAExcluir) {
        List<SpinnerEquipoResponse> listaFiltrada = new ArrayList<>();

        for (SpinnerEquipoResponse e : listaEquiposOriginal) {
            // Filtramos por ID para asegurar la Limpieza de Documentos [cite: 2025-12-28]
            if (!e.getIdTorneoEquipo().equals(equipoAExcluir.getIdTorneoEquipo())) {
                listaFiltrada.add(e);
            }
        }

        ArrayAdapter<SpinnerEquipoResponse> adapterVisitante = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listaFiltrada);
        adapterVisitante.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisitanteTeam.setAdapter(adapterVisitante);
    }

    private void cargarEquipos(Integer idTorneo){
        if (isFinishing() || isDestroyed()) return;

        progressBar.mostrarCargando(true);
        equipoRepository.obtenerEquiposSpinner(idTorneo, new EquipoRepository.EquipoCallback() {
            @Override
            public void onSuccess(List<SpinnerEquipoResponse> equipos) {
                if (!isFinishing()){
                    progressBar.mostrarCargando(false);
                    actualizarSpinnerEquipos(equipos);
                }
            }

            @Override
            public void onNoContent() {
                progressBar.mostrarCargando(false);
                dialogProgramarPartido.mostrarDialogoNoContentEquipos(
                        "Sin Equipos",
                        "Este torneo no tiene equipos registrados o están inactivos.",
                        "Crear Equipo");
                limpiarSpinnerEquipos();
            }
            @Override
            public void onError(String mensaje) {
                progressBar.mostrarCargando(false);
                dialogProgramarPartido.mostrarDialogoError(mensaje);
                limpiarSpinnerEquipos();
            }
        });

    }

    private void cargarTorneos() {
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                spinnerTorneo.actualizarSpinnerTorneos(torneos, ProgramarPartidosActivity.this, spinnerChampionship);
            }

            @Override
            public void onNoContent() {
                dialogProgramarPartido.mostrarDialogoNoContentTorneos(
                        "Torneos",
                        "No tienes torneos activos.",
                        "Crear Torneo");
            }

            @Override
            public void onError(String mensaje) {
                dialogProgramarPartido.mostrarDialogoError(mensaje);
            }
        });
    }

    public void limpiarTablero(){
        editTextDate.setText("");
        editTextTime.setText("");
        editTextLocation.setText("");
        limpiarSpinnerEquipos();
    }

    private void limpiarSpinnerEquipos(){
        List<SpinnerEquipoResponse> listaVacia = new ArrayList<>();
        ArrayAdapter<SpinnerEquipoResponse> adapterVacio= new ArrayAdapter<>(
                ProgramarPartidosActivity.this,
                android.R.layout.simple_spinner_item,
                listaVacia);
        spinnerLocalTeam.setAdapter(adapterVacio);
        spinnerVisitanteTeam.setAdapter(adapterVacio);
        // También limpiamos nuestra lista de referencia para evitar inconsistencias
        if (this.listaEquiposOriginal != null) {
            this.listaEquiposOriginal.clear();
        }
    }
}