package com.ddrd.goldeskapp.ui.traspasos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoUpdate;
import com.ddrd.goldeskapp.data.repository.TraspasoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class TraspasosActivity extends AppCompatActivity {
    //constantes
    private final String PENDIENTES = "PENDIENTE";
    private final String APROBADOS = "APROBADA";
    private final String RECHAZADOS = "RECHAZADA";
    //utilities
    private ProgressBarGoldesk progressBarGoldesk;
    private DialogsResponse dialogsResponse;
    //repositorios
    private TraspasoRepository traspasoRepository;
    //elementos del Activity
    private MaterialButtonToggleGroup toggleEstadoGroup;
    private MaterialButton btnPendientes,
            btnAprobados, btnRechazados;
    private TextView textViewContador;
    private EditText editTextBuscar;
    private RecyclerView recyclerViewTraspasos;
    private LinearLayout layoutEmpty;
    //listas
    private List<TraspasoResponse> traspasosResponse = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_traspasos);

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
        //init Repositorios
        traspasoRepository = new TraspasoRepository(this);
        //init botones
        toggleEstadoGroup = findViewById(R.id.toggleEstadoGroup);
        btnPendientes = findViewById(R.id.btnPendientes);
        btnAprobados = findViewById(R.id.btnAprobados);
        btnRechazados = findViewById(R.id.btnRechazados);
        //init layout
        layoutEmpty = findViewById(R.id.layoutEmpty);
        //init textview
        textViewContador = findViewById(R.id.textViewContador);
        //init edittext
        editTextBuscar = findViewById(R.id.editTextBuscar);
        //init recycler
        recyclerViewTraspasos = findViewById(R.id.recyclerViewTraspasos);
        recyclerViewTraspasos.setLayoutManager(new LinearLayoutManager(this));

        configClickListeners();
        listarTraspasos(PENDIENTES);
    }
    private void configClickListeners(){
        toggleEstadoGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    String estadoSolicitud = PENDIENTES;
                    if (checkedId == R.id.btnPendientes) {
                        estadoSolicitud = PENDIENTES;
                    } else if (checkedId == R.id.btnAprobados) {
                        estadoSolicitud = APROBADOS;
                    } else if (checkedId == R.id.btnRechazados) {
                        estadoSolicitud = RECHAZADOS;
                    }
                    listarTraspasos(estadoSolicitud);
                }
            }
        });
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (traspasosResponse != null){ // Evita filtrar si la lista aún no carga
                    filtroTraspasos(traspasosResponse,s);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void listarTraspasos(String estadoSolicitud){
        progressBarGoldesk.mostrarCargando(true);
        traspasoRepository.listarTraspasos(estadoSolicitud, new TraspasoRepository.ListaTraspasoCallback() {
            @Override
            public void onSuccess(List<TraspasoResponse> responseBody) {
                progressBarGoldesk.mostrarCargando(false);
                traspasosResponse = responseBody;
                AdapterTraspasos adapterTraspasos = new AdapterTraspasos(
                        TraspasosActivity.this,
                        responseBody
                );
                recyclerViewTraspasos.setAdapter(adapterTraspasos);
                String cantTraspasos = getString(R.string.total_traspasos,responseBody.size());
                textViewContador.setText(cantTraspasos);
                ocultarlistaVacia(responseBody);
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "No hay Registros",mensaje);
                ocultarlistaVacia(new ArrayList<>());
                String cantTraspasos = getString(R.string.total_traspasos,new ArrayList<>().size());
                textViewContador.setText(cantTraspasos);
            }
        });
    }
    private void filtroTraspasos(List<TraspasoResponse> traspasos, CharSequence s) {
        List<TraspasoResponse> traspasosFiltrados = new ArrayList<>();
        String query = s.toString().toLowerCase().trim(); // Convertimos a minúsculas una sola vez

        for (TraspasoResponse t : traspasos) {
            // Validamos nulos y comparamos en minúsculas
            boolean coincide = (t.getNombreJugador() != null && t.getNombreJugador().toLowerCase().contains(query)) ||
                    (t.getCedulaJugador() != null && t.getCedulaJugador().toLowerCase().contains(query)) ||
                    (t.getEquipoOrigen() != null && t.getEquipoOrigen().toLowerCase().contains(query)) ||
                    (t.getEquipoDestino() != null && t.getEquipoDestino().toLowerCase().contains(query));

            if (coincide) {
                traspasosFiltrados.add(t);
            }
        }

        // ACTUALIZACIÓN ÚNICA: Fuera del bucle
        AdapterTraspasos adapter = new AdapterTraspasos(TraspasosActivity.this, traspasosFiltrados);
        recyclerViewTraspasos.setAdapter(adapter);

        // Actualizamos el contador para Auditoría
        textViewContador.setText(getString(R.string.total_traspasos, traspasosFiltrados.size()));
        ocultarlistaVacia(traspasosFiltrados);
    }

    public void aprobarSolicitud(Integer idTraspaso){
        progressBarGoldesk.mostrarCargando(true);
        traspasoRepository.aprobarSolicitud(idTraspaso, new TraspasoRepository.AprobacionTraspasosCallback() {
            @Override
            public void onSuccess(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Solicitud Aprobada",
                        mensaje
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Error al Aprobar",
                        mensaje
                );
            }
        });
    }

    private void rechazarSolicitud(TraspasoUpdate traspaso){
        progressBarGoldesk.mostrarCargando(true);
        traspasoRepository.rechazarSolicitud(traspaso, new TraspasoRepository.AprobacionTraspasosCallback() {
            @Override
            public void onSuccess(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Solicitud Rechazada",
                        mensaje
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Error al Rechazar",
                        mensaje
                );
            }
        });
    }
    public void mostrarVentanaRechazo(TraspasoUpdate traspaso){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_traspasos, null);

        Button btnCancelar = view.findViewById(R.id.btnCancelar);
        Button btnAceptar = view.findViewById(R.id.btnAceptar);

        TextView textViewTitleTraspaso = view.findViewById(R.id.textViewTitleTraspaso);
        textViewTitleTraspaso.setText(R.string.rechazo_title);
        TextView textViewDescripcionTraspaso = view.findViewById(R.id.textViewDescripcionTraspaso);
        textViewDescripcionTraspaso.setText(R.string.razon_rechazo);
        EditText editTextAsuntoTraspaso = view.findViewById(R.id.editTextAsuntoTraspaso);
        editTextAsuntoTraspaso.setHint(R.string.razon_rechazo_hint);


        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAceptar.setOnClickListener(v -> {
            String observacion;
            if (editTextAsuntoTraspaso.getText().toString().isEmpty()){
                observacion = "";
            }else {
                observacion = editTextAsuntoTraspaso.getText().toString();
            }
            traspaso.setObservaciones(observacion);
            traspaso.setEstado(RECHAZADOS);
            rechazarSolicitud(traspaso);
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void ocultarlistaVacia(List<TraspasoResponse> traspasos){
        if (traspasos.isEmpty()){
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerViewTraspasos.setVisibility(View.GONE);
        }else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerViewTraspasos.setVisibility(View.VISIBLE);
        }
    }
}