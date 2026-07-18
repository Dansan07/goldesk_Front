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
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoCreate;
import com.ddrd.goldeskapp.data.model.torneo.TorneoResponse;
import com.ddrd.goldeskapp.data.model.torneo.TorneoUpdate;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.ddrd.goldeskapp.util.TokenManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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
    private Button btnLimpiar, btnSave;
    private FloatingActionButton fabGestionarTorneos;
    private CardView cardSelectorTorneo;
    private MaterialButton btnModoNuevo, btnModoEditar;
    private MaterialButtonToggleGroup toggleModoTorneo;

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
        btnSave = findViewById(R.id.btnSave);
        fabGestionarTorneos = findViewById(R.id.fabGestionarTorneos);
        cardSelectorTorneo = findViewById(R.id.cardSelectorTorneo);
        btnModoNuevo = findViewById(R.id.btnModoNuevo);
        btnModoEditar = findViewById(R.id.btnModoEditar);
        toggleModoTorneo = findViewById(R.id.toggleModoTorneo);
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
        btnModoEditar.setOnClickListener(view -> {
            cardSelectorTorneo.setVisibility(View.VISIBLE);
            btnSave.setText(R.string.btn_Actualizar_datos);
        });
        btnModoNuevo.setOnClickListener(view -> {
            cardSelectorTorneo.setVisibility(View.GONE);
            btnSave.setText(R.string.btn_guardar);
        });
        fabGestionarTorneos.setOnClickListener(view -> {mostrarTodosLosTorneos();});
        btnBuscarTorneo.setOnClickListener(view-> {buscarInfoTorneo(idTorneo);});
        btnLimpiar.setOnClickListener(view -> {limpiarCampos();});
        btnSave.setOnClickListener(view -> {
            TorneoUpdate torneoUpdate = obtenerInfoTorneo();
            if (torneoUpdate == null){
                dialogsResponse.mostrarDialogoWarning(
                        "Campos incompletos",
                        "Debe llenar todos los campos");
                return;
            }
            int id = toggleModoTorneo.getCheckedButtonId();
            if (id == R.id.btnModoNuevo){
                TorneoCreate torneoCreate = new TorneoCreate(
                        tokenManager.getOrganizador().getCedula(),
                        torneoUpdate.getNombreTorneo(),
                        torneoUpdate.getValorAmarilla(),
                        torneoUpdate.getValorAzul(),
                        torneoUpdate.getValorRoja(),
                        torneoUpdate.getValorArbitraje(),
                        torneoUpdate.getValorInscripcion(),
                        torneoUpdate.getValorBalonPetos(),
                        torneoUpdate.getPartidosInicial(),
                        torneoUpdate.getCategoriaTorneo(),
                        true
                );
                crearTorneo(torneoCreate);
            }else if (id == R.id.btnModoEditar){
                actualizarTorneo(torneoUpdate);
            }
        });
    }
    public void desactivarTorneo(Integer idTorneo){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.desactivarTorneo(idTorneo, new TorneoRepository.CallbackString<Map<String, String>>() {
            @Override
            public void onSuccess(String response) {
                progressBarGoldesk.mostrarCargando(false);
                mostrarTorneosActivos();
                dialogsResponse.mostrarDialogoSuccess(
                        "Torneo Desactivado",
                        response);
            }
            @Override
            public void onError(String message) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(message);
            }
        });

    }
    public void activarTorneo(Integer idTorneo){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.activarTorneo(idTorneo, new TorneoRepository.CallbackString<Map<String, String>>() {
            @Override
            public void onSuccess(String response) {
                progressBarGoldesk.mostrarCargando(false);
                mostrarTorneosActivos();
                dialogsResponse.mostrarDialogoSuccess(
                        "Torneo Activado",
                        response);
            }
            @Override
            public void onError(String message) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(message);
            }
        });
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
                dialogsResponse.mostrarDialogoNoContentTorneos();
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
                dialogsResponse.mostrarDialogoNoContentTorneos();
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
        torneoRepository.obtenerTorneoPorId(idTorneo, new TorneoRepository.BuscarCallback<TorneoResponse>() {
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
    private void crearTorneo(TorneoCreate torneo){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.crearTorneo(torneo, new TorneoRepository.CallbackString<Map<String, String>>() {
            @Override
            public void onSuccess(String response) {
                progressBarGoldesk.mostrarCargando(false);
                mostrarTorneosActivos();
                dialogsResponse.mostrarDialogoSuccess(
                        "Torneo Creado",
                        response);
            }
            @Override
            public void onError(String message) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Torneo Existente",
                        message);
            }
        });

    }
    private void actualizarTorneo(TorneoUpdate torneo){
        progressBarGoldesk.mostrarCargando(true);
        torneoRepository.actualizarTorneo(torneo, new TorneoRepository.CallbackString<Map<String, String>>() {
            @Override
            public void onSuccess(String response) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Torneo Actualizado",
                        response);
            }
            @Override
            public void onError(String message) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Error al actualizar",
                        message);
            }
        });
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
    private TorneoUpdate obtenerInfoTorneo() {
        // 1. Obtener los textos directamente
        String nombreTorneo = editTextNombreTorneo.getText().toString().trim();
        String txtAmarilla = editTextValorAmarilla.getText().toString().trim().replaceAll("[^0-9.]", "");
        String txtAzul = editTextValorAzul.getText().toString().trim().replaceAll("[^0-9.]", "");
        String txtRoja = editTextValorRoja.getText().toString().trim().replaceAll("[^0-9.]", "");
        String txtArbitraje = editTextValorArbitraje.getText().toString().trim().replaceAll("[^0-9.]", "");
        String txtInscripcion = editTextValorInscripcion.getText().toString().trim().replaceAll("[^0-9.]", "");
        String txtBalonPetos = editTextValorBalonPetos.getText().toString().trim().replaceAll("[^0-9.]", "");
        String txtPartidos = editTextPartidosInicial.getText().toString().trim();
        String categoriaTorneo;
        try {
            categoriaTorneo = spinnerCategoria.getSelectedItem().toString();
        }catch (Exception e){
            return null;
        }
        // 2. Validaciones de presencia y lógica de negocio
        if (nombreTorneo.isEmpty()) {
            editTextNombreTorneo.setError("El nombre del torneo es requerido");
            editTextNombreTorneo.requestFocus();
            progressBarGoldesk.mostrarCargando(false);
            return null;
        }
        if (txtAmarilla.isEmpty() || Double.parseDouble(txtAmarilla) <= 0) {
            editTextValorAmarilla.setError("El valor de la tarjeta amarilla debe ser mayor a 0");
            editTextValorAmarilla.requestFocus();
            return null;
        }
        if (txtAzul.isEmpty() || Double.parseDouble(txtAzul) <= 0) {
            editTextValorAzul.setError("El valor de la tarjeta azul debe ser mayor a 0");
            editTextValorAzul.requestFocus();
            return null;
        }
        if (txtRoja.isEmpty() || Double.parseDouble(txtRoja) <= 0) {
            editTextValorRoja.setError("El valor de la tarjeta roja debe ser mayor a 0");
            editTextValorRoja.requestFocus();
            return null;
        }
        if (txtArbitraje.isEmpty() || Double.parseDouble(txtArbitraje) <= 0) {
            editTextValorArbitraje.setError("El valor del arbitraje debe ser mayor a 0");
            editTextValorArbitraje.requestFocus();
            return null;
        }
        if (txtInscripcion.isEmpty() || Double.parseDouble(txtInscripcion) <= 0) {
            editTextValorInscripcion.setError("El valor de la inscripción debe ser mayor a 0");
            editTextValorInscripcion.requestFocus();
            return null;
        }
        if (txtBalonPetos.isEmpty() || Double.parseDouble(txtBalonPetos) <= 0) {
            editTextValorBalonPetos.setError("El valor del balón de petos debe ser mayor a 0");
            editTextValorBalonPetos.requestFocus();
            return null;
        }
        if (txtPartidos.isEmpty() || Integer.parseInt(txtPartidos) <= 0) {
            editTextPartidosInicial.setError("Los partidos iniciales deben ser mayores a 0");
            editTextPartidosInicial.requestFocus();
            return null;
        }

        // 3. Si todo está bien, parseamos con seguridad para crear el objeto
        return new TorneoUpdate(
                idTorneo, // Asegúrate de que esta variable sea accesible en el scope de la clase
                tokenManager.getOrganizador().getCedula(),
                nombreTorneo,
                Double.parseDouble(txtAmarilla),
                Double.parseDouble(txtAzul),
                Double.parseDouble(txtRoja),
                Double.parseDouble(txtArbitraje),
                Double.parseDouble(txtInscripcion),
                Double.parseDouble(txtBalonPetos),
                Integer.parseInt(txtPartidos),
                categoriaTorneo
        );
    }
}