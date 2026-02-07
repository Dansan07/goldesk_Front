package com.ddrd.goldeskapp.ui.TablaPosiciones;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
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
import com.ddrd.goldeskapp.data.model.TablaPosiciones.TablaPosicionesResponse;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.TablaPosicionesRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;

import java.text.DecimalFormat;
import java.util.List;

public class TablaPosicionesActivity extends AppCompatActivity {
    //elementos de la vista
    private Spinner spinnerChampionship;
    private ProgressBar progressBarFase;
    private TextView tvPartidosRestantes, tvProgresoPercentage ;
    private RecyclerView recyclerViewTabla;

    //elementos utiles
    private ProgressBarGoldesk progressBarGoldesk;
    private DialogsResponse dialogsResponse;
    private SpinnerTorneo spinnerTorneo;

    //elementos del repositorio
    private TorneoRepository torneoRepository;
    private TablaPosicionesRepository tablaPosicionesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabla_posiciones);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents() {
        progressBarGoldesk = new ProgressBarGoldesk(this);
        progressBarFase = findViewById(R.id.progressBarFase);
        dialogsResponse = new DialogsResponse(this);
        spinnerTorneo = new SpinnerTorneo();
        //init Spinner
        spinnerChampionship = findViewById(R.id.spinnerChampionship);
        //init Texviews
        tvPartidosRestantes = findViewById(R.id.tvPartidosRestantes);
        tvProgresoPercentage = findViewById(R.id.tvProgresoPercentage);
        //init Repository
        torneoRepository = new TorneoRepository(this);
        tablaPosicionesRepository = new TablaPosicionesRepository(this);
        //init RecyclerView
        recyclerViewTabla = findViewById(R.id.recyclerViewTabla);
        recyclerViewTabla.setLayoutManager(new LinearLayoutManager(this));

        obtenerTorneos();

        //configurar listeners
        configClickListeners();
    }

    private void configClickListeners(){
        spinnerChampionship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTorneoResponse selectedTorneo = (SpinnerTorneoResponse) parent.getItemAtPosition(position);
                Integer idTorneo = selectedTorneo.getIdTorneo();
                Integer PartidosInicial = selectedTorneo.getCantPartidos();
                mostrarTablaPosiciones(idTorneo,PartidosInicial);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void calcularProgresoTorneo(List<TablaPosicionesResponse> tabla, Integer partidosTotal){
        tvPartidosRestantes.setText(String.valueOf(partidosTotal));
        long cantPartidosJugados = 0;
        Integer partidosTotales = tabla.size() *partidosTotal;
        for (TablaPosicionesResponse i : tabla) {
            cantPartidosJugados +=i.getPj();
        }
        double progreso = (double) cantPartidosJugados /partidosTotales;
        double porcentaje = progreso*100;
        DecimalFormat format = new DecimalFormat("#.##");
        tvProgresoPercentage.setText(String.valueOf(format.format(porcentaje)+"%"));
        progressBarFase.setProgress((int) porcentaje);
    }

    private void obtenerTorneos() {
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                progressBarGoldesk.mostrarCargando(false);
                spinnerTorneo.actualizarSpinnerTorneos(torneos, TablaPosicionesActivity.this, spinnerChampionship);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentTorneos(
                        "Torneos No encontrados",
                        "No se encontraron torneos para mostrar",
                        "Ir a Crear Torneo"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }

    private void mostrarTablaPosiciones(Integer idTorneo, Integer cantPartidos){
        progressBarGoldesk.mostrarCargando(true);
        tablaPosicionesRepository.obtenerTablaPosiciones(idTorneo, new TablaPosicionesRepository.obtenerTablaCallback() {
            @Override
            public void onSuccess(List<TablaPosicionesResponse> response) {
                progressBarGoldesk.mostrarCargando(false);
                AdapterTablaPosiciones adapterTablaPosiciones = new AdapterTablaPosiciones(
                        TablaPosicionesActivity.this,
                        response
                );
                recyclerViewTabla.setAdapter(adapterTablaPosiciones);
                calcularProgresoTorneo(response,cantPartidos);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentPartidos(
                        "Equipos No encontrados",
                        "No se ha disputado ningún partido aún",
                        "Ir a Programar Partido"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
}