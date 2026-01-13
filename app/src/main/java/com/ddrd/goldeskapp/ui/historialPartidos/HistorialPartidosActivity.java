package com.ddrd.goldeskapp.ui.historialPartidos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.partido.FiltroHistorialPartidos;
import com.ddrd.goldeskapp.data.model.partido.PartidoResponseDuplicate;
import com.ddrd.goldeskapp.data.model.partido.PartidosHistorialResponse;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.PartidoRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBar;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogProgramarPartido;
import com.ddrd.goldeskapp.ui.utilities.formatos.FomatearFechaHora;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.List;

public class HistorialPartidosActivity extends AppCompatActivity {

    private PartidoRepository partidoRepository;
    private TorneoRepository torneoRepository;
    private SpinnerTorneo spinnerTorneo;
    private Spinner spinnerFilterChampionship;
    private DialogProgramarPartido dialogProgramarPartido;
    private TextInputEditText editTextFilterTeam;
    private EditText editTextFechaInicio, editTextFechaFin;
    private FomatearFechaHora fomatearFechaHora;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewPartidos;

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

    private void initComponents(){
        fomatearFechaHora = new FomatearFechaHora();
        progressBar = new ProgressBar(this);
        //repository init
        partidoRepository= new PartidoRepository(this);
        torneoRepository = new TorneoRepository(this);
        //spinner init
        spinnerTorneo = new SpinnerTorneo();
        spinnerFilterChampionship = findViewById(R.id.spinnerFilterChampionship);
        dialogProgramarPartido = new DialogProgramarPartido(this);
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
        mostrarSpinnerTorneos();

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

    }

    private void mostrarSpinnerTorneos(){
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                spinnerTorneo.actualizarSpinnerTorneos(torneos, HistorialPartidosActivity.this, spinnerFilterChampionship);
            }
            @Override
            public void onNoContent() {
                dialogProgramarPartido.mostrarDialogoNoContentTorneos(
                        "Torneos",
                        "No tienes torneos activos.",
                        "Crear Torneo"
                );
            }
            @Override
            public void onError(String mensaje) {
                dialogProgramarPartido.mostrarDialogoError(mensaje);
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
            fechaInicio=fomatearFechaHora.formatearFecha(fechaInicio);
        }
        if (fechaFin != null){
            fechaFin=fomatearFechaHora.formatearFecha(fechaFin);
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
        progressBar.mostrarCargando(true);
        partidoRepository.obtenerHistorialPartidos(filtro, new PartidoRepository.PartidoHistorialCalback() {
            @Override
            public void onSuccess(List<PartidosHistorialResponse> partidos) {
                if (!isFinishing()){
                    progressBar.mostrarCargando(false);
                }
            }

            @Override
            public void onNoContent() {
                progressBar.mostrarCargando(false);
                dialogProgramarPartido.mostrarDialogoNoContentPartidos(
                        "Sin Partidos",
                        "No tienes partidos registrados",
                        "Programar un Partido"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBar.mostrarCargando(false);
                dialogProgramarPartido.mostrarDialogoError(mensaje);
            }
        });
    }

}