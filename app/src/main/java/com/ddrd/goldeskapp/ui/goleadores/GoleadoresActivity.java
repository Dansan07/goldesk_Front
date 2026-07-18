package com.ddrd.goldeskapp.ui.goleadores;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.tablaGoleadores.TablaGoleadoresResponse;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.TablaGoleadoresRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;

import java.util.List;

public class GoleadoresActivity extends AppCompatActivity {

    //utilities
    private ProgressBarGoldesk progressBarGoldesk;
    private DialogsResponse dialogsResponse;
    private SpinnerTorneo spinnerTorneo;

    //Repository
    private TorneoRepository torneoRepository;
    private TablaGoleadoresRepository tablaGoleadoresRepository;

    //elementos de la vista
    private AppCompatSpinner spinner_torneos;
    private RecyclerView recycler_goleadores;
    private TextView txt_total_goles, txt_total_jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_goleadores);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents(){
        //init utilities
        progressBarGoldesk = new ProgressBarGoldesk(this);
        dialogsResponse = new DialogsResponse(this);
        spinnerTorneo = new SpinnerTorneo();
        //init Repository
        torneoRepository = new TorneoRepository(this);
        tablaGoleadoresRepository = new TablaGoleadoresRepository(this);
        //init elementos de la vista
        spinner_torneos = findViewById(R.id.spinner_torneos);
        txt_total_goles = findViewById(R.id.txt_total_goles);
        txt_total_jugadores = findViewById(R.id.txt_total_jugadores);
        recycler_goleadores = findViewById(R.id.recycler_goleadores);
        recycler_goleadores.setLayoutManager(new LinearLayoutManager(this));

        cargarSpinnerTorneos();
        configClickListener();
    }
    private void configClickListener(){
        spinner_torneos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTorneoResponse torneoSeleccionado = (SpinnerTorneoResponse) parent.getSelectedItem();
                cargarTablaGoleadores(Long.parseLong(String.valueOf(torneoSeleccionado.getIdTorneo())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void cargarSpinnerTorneos(){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneosActivos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                progressBarGoldesk.mostrarCargando(false);
                spinnerTorneo.actualizarSpinnerTorneos(torneos, GoleadoresActivity.this, spinner_torneos);
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
    private void cargarTablaGoleadores(Long idTorneo){
        progressBarGoldesk.mostrarCargando(true);
        tablaGoleadoresRepository.obtenerTablaGoleadores(idTorneo, new TablaGoleadoresRepository.GoleadoresCallback() {
            @Override
            public void onSuccess(List<TablaGoleadoresResponse> response) {
                AdapterGoleadores adapterGoleadores = new AdapterGoleadores(GoleadoresActivity.this, response);
                recycler_goleadores.setAdapter(adapterGoleadores);
                cargarinforGeneral(response);
                progressBarGoldesk.mostrarCargando(false);
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "No hay datos",
                        mensaje);
            }
        });
    }
    private void cargarinforGeneral(List<TablaGoleadoresResponse> goleadores){
        int totalGoles = 0;
        int totalJugadores = goleadores.size();
        for (TablaGoleadoresResponse tablaGoleador : goleadores) {
            totalGoles+=tablaGoleador.getGoles();
        }
        txt_total_goles.setText(String.valueOf(totalGoles));
        txt_total_jugadores.setText(String.valueOf(totalJugadores));
    }
}