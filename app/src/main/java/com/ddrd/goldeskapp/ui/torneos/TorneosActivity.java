package com.ddrd.goldeskapp.ui.torneos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoResponse;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.ddrd.goldeskapp.util.TokenManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

public class TorneosActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private Spinner spinnerTorneos, spinnerCategoria;
    private EditText editTextNombreTorneo, editTextPartidosInicial,
            editTextValorAmarilla, editTextValorAzul, editTextValorRoja,
            editTextValorArbitraje, editTextValorInscripcion, editTextValorBalonPetos;
    private SpinnerTorneo spinnerTorneo;
    private ProgressBarGoldesk progressBarGoldesk;
    private DialogsResponse dialogsResponse;
    private TorneoRepository torneoRepository;
    private Button btnBuscarTorneo;
    private Button btnLimpiar;
    private FloatingActionButton fabGestionarTorneos;

    //variables para listar categorías.
    int posicion;
    int idTorneo;
    String categoriaASeleccionar;



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
        tokenManager = new TokenManager(TorneosActivity.this);
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
        btnBuscarTorneo = findViewById(R.id.btnBuscarTorneo);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        fabGestionarTorneos = findViewById(R.id.fabGestionarTorneos);
        //elementos utiles
        progressBarGoldesk = new ProgressBarGoldesk(this);
        dialogsResponse = new DialogsResponse(this);
        spinnerTorneo = new SpinnerTorneo();
        //init Repository
        torneoRepository = new TorneoRepository(this);
        //configurar listeners
        setConfigListeners();
        mostrarTorneosActivos();
    }
    private void setConfigListeners(){
        spinnerTorneos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerTorneoResponse torneoSeleccionado = (SpinnerTorneoResponse) parent.getSelectedItem();
                idTorneo = torneoSeleccionado.getIdTorneo();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fabGestionarTorneos.setOnClickListener(view -> {mostrarTodosLosTorneos();});
        btnBuscarTorneo.setOnClickListener(view-> {
            buscarInfoTorneo(idTorneo);});
        btnLimpiar.setOnClickListener(view -> {limpiarCampos();});
    }
    public void llenarSpinnerCategorias(String cedulaOrg){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.buscarCategorias(cedulaOrg, new TorneoRepository.CategoriasTorneos() {
            @Override
            public void onSuccess(List<String> categorias) {
                progressBarGoldesk.mostrarCargando(false);
                ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(TorneosActivity.this, android.R.layout.simple_spinner_item, categorias);
                adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                posicion = adapterCategorias.getPosition(categoriaASeleccionar);
                spinnerCategoria.setAdapter(adapterCategorias);
                spinnerCategoria.setSelection(posicion);
            }

            @Override
            public void onError(String message) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(message);
            }
        });
    }
    private void mostrarTodosLosTorneos(){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                progressBarGoldesk.mostrarCargando(false);
                AdapterTorneos adapterTorneos = new AdapterTorneos(TorneosActivity.this, torneos);
                crearDialogInactivarTorneo(adapterTorneos);
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
    private void mostrarTorneosActivos(){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneosActivos(new TorneoRepository.TorneoCallback() {
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
    private void buscarInfoTorneo(Integer idTorneo){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.obtenerTorneoPorId(idTorneo, new TorneoRepository.TorneoBuscarCallback() {
            @Override
            public void onSuccess(TorneoResponse response) {
                progressBarGoldesk.mostrarCargando(false);
                mostrarInfoTorneos(response);
                categoriaASeleccionar = response.getCategoriaTorneo();
                llenarSpinnerCategorias(tokenManager.getOrganizador().getCedula());
            }

            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });

    }
    public void mostrarInfoTorneos(TorneoResponse torneo){
        DecimalFormat df = new DecimalFormat("$#,###");
        editTextNombreTorneo.setText(torneo.getNombreTorneo());
        editTextPartidosInicial.setText(String.valueOf(torneo.getPartidosInicial()));
        editTextValorAmarilla.setText(df.format(torneo.getValorAmarilla()));
        editTextValorAzul.setText(df.format(torneo.getValorAzul()));
        editTextValorRoja.setText(df.format(torneo.getValorRoja()));
        editTextValorArbitraje.setText(df.format(torneo.getValorArbitraje()));
        editTextValorInscripcion.setText(df.format(torneo.getValorInscripcion()));
        editTextValorBalonPetos.setText(df.format(torneo.getValorBalonPetos()));
    }

//    private void configSwitch(){
//        if (switchActivo.isChecked()){
//            switchActivo.setTrackTintList(getResources().getColorStateList(R.color.dialog_success_green));
//            switchActivo.setThumbTintList(getResources().getColorStateList(R.color.success));
//            txtTorneoActivo.setText("Torneo Activo");
//        }else{
//            switchActivo.setTrackTintList(getResources().getColorStateList(R.color.plata_trofeo));
//            switchActivo.setThumbTintList(getResources().getColorStateList(R.color.text_secondary));
//            txtTorneoActivo.setText("Torneo Inactivo");
//        }
//    }
    private void crearDialogInactivarTorneo(AdapterTorneos adapterTorneos){
        //crear diálogo para inactivar torneo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_gestionar_torneos, null);

        ImageView btnCerrarGestionTorneos = view.findViewById(R.id.btnCerrarGestionTorneos);
        RecyclerView rvTorneos = view.findViewById(R.id.recyclerViewGestionTorneos);
        rvTorneos.setLayoutManager(new LinearLayoutManager(this));
        rvTorneos.setAdapter(adapterTorneos);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        btnCerrarGestionTorneos.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void limpiarCampos(){
        editTextNombreTorneo.setText("");
        editTextPartidosInicial.setText("");
        editTextValorAmarilla.setText("");
        editTextValorAzul.setText("");
        editTextValorRoja.setText("");
        editTextValorArbitraje.setText("");
        editTextValorInscripcion.setText("");
        editTextValorBalonPetos.setText("");
    }
}