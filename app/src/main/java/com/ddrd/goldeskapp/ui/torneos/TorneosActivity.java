package com.ddrd.goldeskapp.ui.torneos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.TablaPosiciones.TablaPosicionesActivity;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;

import java.util.List;

public class TorneosActivity extends AppCompatActivity {
    private Spinner spinnerTorneos, spinnerCategoria;
    private EditText editTextNombreTorneo, editTextPartidosInicial,
            editTextValorAmarilla, editTextValorAzul, editTextValorRoja,
            editTextValorArbitraje, editTextValorInscripcion, editTextValorBalonPetos;
    private SwitchCompat switchActivo;
    private SpinnerTorneo spinnerTorneo;
    private ProgressBarGoldesk progressBarGoldesk;
    private DialogsResponse dialogsResponse;
    private TorneoRepository torneoRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_torneos);

        initComponent();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponent(){
        //elementos de la vista
        spinnerTorneos = findViewById(R.id.spinnerTorneos);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        editTextNombreTorneo = findViewById(R.id.editTextNombreTorneo);
        editTextPartidosInicial = findViewById(R.id.editTextPartidosInicial);
        editTextValorAmarilla = findViewById(R.id.editTextValorAmarilla);
        editTextValorAzul = findViewById(R.id.editTextValorAzul);
        editTextValorRoja = findViewById(R.id.editTextValorRoja);
        editTextValorArbitraje = findViewById(R.id.editTextValorArbitraje);
        editTextValorInscripcion = findViewById(R.id.editTextValorInscripcion);
        editTextValorBalonPetos = findViewById(R.id.editTextValorBalonPetos);
        switchActivo = findViewById(R.id.switchActivo);
        //elementos utiles
        progressBarGoldesk = new ProgressBarGoldesk(this);
        dialogsResponse = new DialogsResponse(this);
        spinnerTorneo = new SpinnerTorneo();
        //init Repository
        torneoRepository = new TorneoRepository(this);
        //configurar listeners
        setConfigListeners();
        mostrarTorneos();
    }
    private void setConfigListeners(){
        spinnerTorneos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void mostrarTorneos(){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                progressBarGoldesk.mostrarCargando(false);
                spinnerTorneo.actualizarSpinnerTorneos(torneos, TorneosActivity.this, spinnerTorneos);
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
}