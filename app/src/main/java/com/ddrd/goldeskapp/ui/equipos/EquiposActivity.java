package com.ddrd.goldeskapp.ui.equipos;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.equipo.ActualizarNombreEquipo;
import com.ddrd.goldeskapp.data.model.equipo.SpinnerEquipoResponse;
import com.ddrd.goldeskapp.data.model.jugador.EstadisticasJugador;
import com.ddrd.goldeskapp.data.model.jugador.JugadorCarnet;
import com.ddrd.goldeskapp.data.model.jugador.JugadorCreate;
import com.ddrd.goldeskapp.data.model.jugador.JugadorResponse;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;
import com.ddrd.goldeskapp.data.model.traspasos.PdfResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoCreate;
import com.ddrd.goldeskapp.data.repository.EquipoRepository;
import com.ddrd.goldeskapp.data.repository.JugadorRepository;
import com.ddrd.goldeskapp.data.repository.TorneoRepository;
import com.ddrd.goldeskapp.data.repository.TraspasoRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraUser;
import com.ddrd.goldeskapp.ui.utilities.spinnersContent.SpinnerTorneo;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class EquiposActivity extends AppCompatActivity {

    private DialogsResponse dialogsResponse;
    private ProgressBarGoldesk progressBarGoldesk;
    private FormatearFechaHoraUser formatearFechaHoraUser;
    private TorneoRepository torneoRepository;
    private EquipoRepository equipoRepository;
    private JugadorRepository jugadorRepository;
    private TraspasoRepository traspasoRepository;
    private Spinner spinnerTournament;
    private SpinnerTorneo spinnerTorneo;
    private AutoCompleteTextView autoCompleteTeamSearch;
    private ImageButton btnClearSearch, btnEditTeamName, btnSearch;
    private EditText editTextTeamName;
    private Button btnCancelEdit, btnSaveEdit, btnAddPlayer;
    private RecyclerView recyclerViewPlayers;
    private TextView textEmptyPlayers;
    private LinearLayout layoutEmptyState;
    //private FloatingActionButton fabAddTeam;
    private ExtendedFloatingActionButton fabAddTeam;
    private CardView cardTeamDetails, cardPlayersList,CardViewCamposIncripcion;
    private Map<String, Integer> mapIdEquipo = new HashMap<>();
    //editText ventana inscripcion
    TextView campoCedula;
    TextView campoNombre;
    TextView campoApellido;
    TextView campoTelefono;
    TextView campoEmail;
    TextView campoIdJugador;
    TextView campoUrlFoto;
    CheckBox campoEsDelegado;
    Button btnCancelar;
    Button btnGuardar;
    //ventana Traspaso
    TextInputEditText campoAsuntoTraspaso;
    JugadorResponse jugadorResponse = new JugadorResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_equipos);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents(){
        dialogsResponse = new DialogsResponse(this);
        progressBarGoldesk = new ProgressBarGoldesk(this);
        formatearFechaHoraUser = new FormatearFechaHoraUser();
        //init Repositorios
        torneoRepository = new TorneoRepository(this);
        equipoRepository = new EquipoRepository(this);
        jugadorRepository = new JugadorRepository(this);
        traspasoRepository = new TraspasoRepository(this);
        //init Spinner
        spinnerTournament = findViewById(R.id.spinnerTournament);
        spinnerTorneo = new SpinnerTorneo();
        //init AutoCompleteTextView
        autoCompleteTeamSearch = findViewById(R.id.autoCompleteTeamSearch);
        //init imagenButton
        btnClearSearch = findViewById(R.id.btnClearSearch);
        btnEditTeamName = findViewById(R.id.btnEditTeamName);
        btnSearch = findViewById(R.id.btnSearch);
        //init EditText
        editTextTeamName = findViewById(R.id.editTextTeamName);
        //init Button
        btnCancelEdit = findViewById(R.id.btnCancelEdit);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        btnAddPlayer = findViewById(R.id.btnAddPlayer);
        //init RecyclerView
        recyclerViewPlayers = findViewById(R.id.recyclerViewPlayers);
        recyclerViewPlayers.setLayoutManager(new LinearLayoutManager(this));
        //init TextView
        textEmptyPlayers = findViewById(R.id.textEmptyPlayers);
        //init LinearLayout
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        //init CardView
        cardTeamDetails = findViewById(R.id.cardTeamDetails);
        cardPlayersList = findViewById(R.id.cardPlayersList);
        //init FloatingActionButton
        fabAddTeam = findViewById(R.id.fabAddTeam);

        //vista inicial
        cardPlayersList.setVisibility(View.GONE);
        layoutEmptyState.setVisibility(View.VISIBLE);

        cargarTorneos();
        configClickListeners();
    }

    private void configClickListeners(){
        spinnerTournament.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                limpiarTablero();
                cargarEquipos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnEditTeamName.setOnClickListener(v -> {
            // 1. Medir la altura objetivo (el tamaño del contenido)
            cardTeamDetails.measure(
                    View.MeasureSpec.makeMeasureSpec(cardTeamDetails.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            int targetHeight = cardTeamDetails.getMeasuredHeight();
            int initialHeight = cardTeamDetails.getHeight();

            // 2. Animar entre números reales
            ValueAnimator animator = ValueAnimator.ofInt(initialHeight, targetHeight);
            animator.addUpdateListener(animation -> {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cardTeamDetails.getLayoutParams();
                params.height = (int) animation.getAnimatedValue();
                params.topMargin = dpToPx(16);
                cardTeamDetails.setLayoutParams(params);
            });

            // 3. Al finalizar, ponemos WRAP_CONTENT por seguridad dinámica
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    cardTeamDetails.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    cardTeamDetails.requestLayout(); // Refresco final
                }
            });
            animator.setDuration(500);
            animator.start();
        });
        btnCancelEdit.setOnClickListener(v -> {
            ValueAnimator animator = ValueAnimator.ofInt(cardTeamDetails.getHeight(), dpToPx(60));
            animator.addUpdateListener(animation -> {
                ViewGroup.LayoutParams params = cardTeamDetails.getLayoutParams();
                params.height = (int) animator.getAnimatedValue();
                cardTeamDetails.setLayoutParams(params);
            });
            animator.setDuration(500);
            animator.start();
        });
        btnSearch.setOnClickListener(v -> {
            cargarEstadisticasJugadores();
        });
        btnClearSearch.setOnClickListener(v -> {
            limpiarTablero();
            cardPlayersList.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        });
        btnSaveEdit.setOnClickListener(v -> {
            validaActualizaNombreEquipo();
        });
        btnAddPlayer.setOnClickListener(v -> {
            ventanaIncripcionJugador();
        });
    }

    //metodos para cargar datos
    private void cargarTorneos(){
        torneoRepository.obtenerTorneos(new TorneoRepository.TorneoCallback() {
            @Override
            public void onSuccess(List<SpinnerTorneoResponse> torneos) {
                spinnerTorneo.actualizarSpinnerTorneos(torneos, EquiposActivity.this, spinnerTournament);
            }
            @Override
            public void onNoContent() {
                dialogsResponse.mostrarDialogoNoContentTorneos(
                        "Torneos",
                        "No tienes torneos activos.",
                        "Crear Torneo"
                );
                cardPlayersList.setVisibility(View.GONE);
                layoutEmptyState.setVisibility(View.VISIBLE);
            }
            @Override
            public void onError(String mensaje) {
                dialogsResponse.mostrarDialogoError(mensaje);
                cardPlayersList.setVisibility(View.GONE);
                layoutEmptyState.setVisibility(View.VISIBLE);
            }
        });

    }
    private void cargarEquipos(){
        progressBarGoldesk.mostrarCargando(true);
        SpinnerTorneoResponse torneo= (SpinnerTorneoResponse) spinnerTournament.getSelectedItem();
        equipoRepository.obtenerEquiposSpinner(torneo.getIdTorneo(), new EquipoRepository.EquipoCallback() {
            @Override
            public void onSuccess(List<SpinnerEquipoResponse> equipos) {
                progressBarGoldesk.mostrarCargando(false);
                ArrayAdapter<SpinnerEquipoResponse> adapter = new ArrayAdapter<>(
                        EquiposActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        equipos
                );
                for (SpinnerEquipoResponse e :equipos) {
                    mapIdEquipo.put(e.getNombreEquipo(), e.getIdTorneoEquipo());
                }
                autoCompleteTeamSearch.setAdapter(adapter);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentEquipos(
                        "Equipos",
                        "No tienes equipos registrados.",
                        "Crear Equipo"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void cargarDatosJugadores(){
        Integer idTorneoEquipo = mapIdEquipo.get(autoCompleteTeamSearch.getText().toString());
        progressBarGoldesk.mostrarCargando(true);
        jugadorRepository.obtenerJugadoresPorEquipo(idTorneoEquipo, new JugadorRepository.JugadorCallback<List<JugadorResponse>>() {
            @Override
            public void onSuccess(List<JugadorResponse> responses) {
                progressBarGoldesk.mostrarCargando(false);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentJugadores(
                        "Jugadores",
                        "No tienes jugadores registrados.",
                        "Crear Jugador"
                );
            }

            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void cargarEstadisticasJugadores(){
        String nombreEquipo = autoCompleteTeamSearch.getText().toString();
        if (nombreEquipo.isEmpty()){
            dialogsResponse.mostrarDialogoInformacion(
                    "Advertencia",
                    "Por favor, escriba un equipo"
            );
            return;
        }
        if (mapIdEquipo.get(nombreEquipo)==null){
            dialogsResponse.mostrarDialogoInformacion(
                    "Advertencia",
                    "Por favor, escriba un equipo válido"
            );
            return;
        }
        Integer idTorneoEquipo = mapIdEquipo.get(nombreEquipo);
        progressBarGoldesk.mostrarCargando(true);
        jugadorRepository.obtenerEstadisticasPorEquipo(idTorneoEquipo, new JugadorRepository.JugadorCallback<List<EstadisticasJugador>>() {
            @Override
            public void onSuccess(List<EstadisticasJugador> responses) {
                progressBarGoldesk.mostrarCargando(false);
                AdapterEstadisticasJugadores adapter = new AdapterEstadisticasJugadores(
                        EquiposActivity.this,
                        responses
                        );
                recyclerViewPlayers.setAdapter(adapter);
                recyclerViewPlayers.setVisibility(View.VISIBLE);
                textEmptyPlayers.setVisibility(View.GONE);
                cardPlayersList.setVisibility(View.VISIBLE);
                layoutEmptyState.setVisibility(View.GONE);
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoNoContentJugadores(
                        "Jugadores",
                        "No tienes jugadores registrados.",
                        "Crear Jugador"
                );
                AdapterEstadisticasJugadores adapter = new AdapterEstadisticasJugadores(
                        EquiposActivity.this,
                        new ArrayList<>()
                );
                recyclerViewPlayers.setAdapter(adapter);
                recyclerViewPlayers.setVisibility(View.GONE);
                textEmptyPlayers.setVisibility(View.VISIBLE);
                cardPlayersList.setVisibility(View.VISIBLE);
                layoutEmptyState.setVisibility(View.GONE);
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                AdapterEstadisticasJugadores adapter = new AdapterEstadisticasJugadores(
                        EquiposActivity.this,
                        new ArrayList<>()
                );
                recyclerViewPlayers.setAdapter(adapter);
                dialogsResponse.mostrarDialogoError(mensaje);
                cardPlayersList.setVisibility(View.GONE);
                layoutEmptyState.setVisibility(View.VISIBLE);
            }
        });
    }

    //metodos para actualizar datos
    private void actualizarNombreEquipo(ActualizarNombreEquipo actualizar){
        progressBarGoldesk.mostrarCargando(true);
        equipoRepository.actualizarNombreEquipo(actualizar, new EquipoRepository.ResponseSuccesCallback() {
            @Override
            public void onSuccess() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Equipo Actualizado",
                        "El nombre del equipo se actualizó correctamente"
                );
                limpiarTablero();
                cargarTorneos();
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(mensaje);
            }
        });
    }
    private void validaActualizaNombreEquipo(){
        String equipo=autoCompleteTeamSearch.getText().toString().trim();
        if (equipo.isEmpty()){
            autoCompleteTeamSearch.setError("Debe escriba un equipo");
            return;
        }
        Integer idEquipo;
        if ( mapIdEquipo.get(equipo) == null){
            dialogsResponse.mostrarDialogoInformacion(
                    "Advertencia",
                    "Verifique que el equipo esté bien escrito o seleccione uno de la lista"
            );
            return;
        }
        String nombreActualizado = editTextTeamName.getText().toString().trim();
        if (nombreActualizado.isEmpty()){
            editTextTeamName.setError("Debe escribir un nombre");
            return;
        }
        idEquipo = mapIdEquipo.get(equipo);
        actualizarNombreEquipo(
                new ActualizarNombreEquipo(idEquipo, nombreActualizado));
    }

    //ventana para inscribir jugador
    public void ventanaIncripcionJugador(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_inscribir_jugador, null);

        CardViewCamposIncripcion = view.findViewById(R.id.CardViewCamposIncripcion);

        campoCedula = view.findViewById(R.id.editTextCedula);

        campoNombre = view.findViewById(R.id.editTextNombre);
        campoApellido = view.findViewById(R.id.editTextApellidos);
        campoTelefono = view.findViewById(R.id.editTextTelefono);
        campoEmail = view.findViewById(R.id.editTextEmail);
        campoIdJugador = view.findViewById(R.id.editTextIdJugador);
        campoUrlFoto = view.findViewById(R.id.editTextUrlFoto);
        campoEsDelegado = view.findViewById(R.id.checkBoxEsDelegado);


        //CardViewCamposIncripcion.setVisibility(View.GONE);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnGuardar = view.findViewById(R.id.btnGuardarInsJugador);
        btnGuardar.setText(R.string.buscar);
        btnCancelar.setText(R.string.cerrar);


        btnGuardar.setOnClickListener(v -> {
            String cedula = campoCedula.getText().toString().trim();
            if (cedula.isEmpty()){
                campoCedula.setError("Debe escribir una cédula");
                return;
            }
            if (btnGuardar.getText().toString().equals(getString(R.string.Inscribir_a_mi_Equipo))){
                validarCamposDatosJugador();
                return;
            }
            buscarInfoJugador(cedula);
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnCancelar.setOnClickListener(v -> {
            if (btnCancelar.getText().equals(getString(R.string.cerrar))){
                dialog.dismiss();
                return;
            }
            ValueAnimator animator = ValueAnimator.ofInt(CardViewCamposIncripcion.getHeight(), dpToPx(0));
            animator.addUpdateListener(animation -> {
                ViewGroup.LayoutParams params = CardViewCamposIncripcion.getLayoutParams();
                params.height = (int) animator.getAnimatedValue();
                CardViewCamposIncripcion.setLayoutParams(params);
            });
            animator.setDuration(500);
            animator.start();
            btnCancelar.setText(R.string.cerrar);
            btnGuardar.setText(R.string.buscar);
            campoCedula.setEnabled(true);
            campoCedula.setBackgroundResource(R.drawable.bg_edittext);
            limpiarDatosJugador();
        });



        dialog.show();
    }
    public void llenarDatosJugador(JugadorResponse response){
        campoNombre.setText(response.getNombre());
        campoApellido.setText(response.getApellidos());
        campoTelefono.setText(response.getTelefono());
        campoEmail.setText(response.getEmail());
        campoIdJugador.setText(String.valueOf(response.getIdTorneoEquipoJugador()));
        campoUrlFoto.setText(response.getUrlFoto());
        campoEsDelegado.setChecked(response.getEsDelegado());
    }
    private void limpiarDatosJugador(){
        campoNombre.setText("");
        campoApellido.setText("");
        campoTelefono.setText("");
        campoEmail.setText("");
        campoIdJugador.setText("");
        campoUrlFoto.setText("");
        campoEsDelegado.setChecked(false);
    }
    public void validarCamposDatosJugador(){
        if (campoNombre.getText().toString().trim().isEmpty()){
            campoNombre.setError("Debe escribir un nombre");
            return;
        } else if (campoApellido.getText().toString().trim().isEmpty()) {
            campoApellido.setError("Debe escribir un apellido");
            return;
        }else if (campoTelefono.getText().toString().trim().isEmpty()){
            campoTelefono.setError("Debe escribir un teléfono");
            return;
        } else if (campoEmail.getText().toString().trim().isEmpty()) {
            campoEmail.setError("Debe escribir un email");
            return;
        }
        obtenerDatosJuador();
    }
    public void buscarInfoJugador(String cedula){
        progressBarGoldesk.mostrarCargando(true);
        jugadorRepository.buscarInfoJugador(cedula, new JugadorRepository.JugadorCallback<JugadorResponse>() {
            @Override
            public void onSuccess(JugadorResponse responses) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoIncribirJugadores(
                        "Jugador Inscrito",
                        "El jugador con la cedula: "+cedula+" ya se encuentra registrado, y podría estar jugando para otro equipo.\n" +
                                "si reclama a este jugador podría necesitar realizar una solicitud de traspaso \n" +
                                "¿Desea reclamar a este jugador para que haga parte de su equipo?",
                        "Inscribir", responses
                );
                jugadorResponse = responses;
            }
            @Override
            public void onNoContent() {


            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoIncribirJugadores(
                        "Inscribir Jugador",
                        "El jugador no ha sido registrado, desea inscribir uno con esta cedula?",
                        "si",
                        null);
            }
        });
    }
    private void inscribirJugador(JugadorCreate jugador, Integer idTorneoEquipo){
        progressBarGoldesk.mostrarCargando(true);
        jugadorRepository.inscribirJugador(jugador, idTorneoEquipo, new JugadorRepository.JugadorCallback<Object>() {
            @Override
            public void onSuccess(Object responses) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Jugador Inscrito",
                        "El jugador se inscribió correctamente"
                );
                limpiarDatosJugador();
            }
            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarVentanaComprobacion(
                        "Jugador Inscrito",
                        "El jugador esta actualmente inscrito en otro equipo para este torneo\n" +
                                "¿Deseas iniciar una solicitud de traspaso?",
                        "solicitar traspaso",
                        ()-> ventanaTraspaso()
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Error al Inscribir Jugador",
                        mensaje);
            }
        });
    }
    private void obtenerDatosJuador(){
        JugadorCreate j = new JugadorCreate();
        j.setCedula(campoCedula.getText().toString().trim());
        j.setNombre(campoNombre.getText().toString().trim());
        j.setApellidos(campoApellido.getText().toString().trim());
        j.setTelefono(campoTelefono.getText().toString().trim());
        j.setEmail(campoEmail.getText().toString().trim());
        j.setUrlFoto(campoUrlFoto.getText().toString().trim());
        j.setEsDelegado(campoEsDelegado.isChecked());
        //ids de jugador y tej
        j.setIdJugador(jugadorResponse.getIdJugador());
        j.setIdTorneoEquipoJugador(jugadorResponse.getIdTorneoEquipoJugador());
        Integer idTorneoEquipo = mapIdEquipo.get(autoCompleteTeamSearch.getText().toString());
        inscribirJugador(j, idTorneoEquipo);
    }
    private void eliminarJugadorDeEquipo(Integer idTorneoEquipoJugador){
        progressBarGoldesk.mostrarCargando(true);
        jugadorRepository.eliminarJugadorDeEquipo(idTorneoEquipoJugador, new JugadorRepository.JugadorCallback<Object>() {
            @Override
            public void onSuccess(Object responses) {
                progressBarGoldesk.mostrarCargando(false);
            }

            @Override
            public void onNoContent() {

            }

            @Override
            public void onError(String mensaje) {

            }
        });
    }

    //metodo carnet de jugador
    public void obtenerCarnetJugador(Integer idParticipacion){
        progressBarGoldesk.mostrarCargando(true);
        jugadorRepository.obtenerCarnetJugador(idParticipacion, new JugadorRepository.JugadorCallback<JugadorCarnet>() {
            @Override
            public void onSuccess(JugadorCarnet responses) {
                progressBarGoldesk.mostrarCargando(false);
                llenarCarnetJugador(responses);
            }

            @Override
            public void onNoContent() {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Jugador no Encontrado",
                        "El jugador no se encuentra registrado"
                );
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Jugador no Encontrado",
                        mensaje);

            }
        });
    }
    private void llenarCarnetJugador(JugadorCarnet jugadorCarnet){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_carnet_digital, null);

        TextView tvTorneoNombre, tvCategoria, tvNombreCompleto, tvBadgeDelegado,
                tvCedula, tvEquipo, tvIdInscripcion, tvTelefono, tvEmail, tvFechaInscripcion;
        ImageView imgFotoJugador;
        Button btnCerrarCarnet;

        //init views
        tvTorneoNombre = view.findViewById(R.id.tvTorneoNombre);
        tvCategoria = view.findViewById(R.id.tvCategoria);
        tvNombreCompleto = view.findViewById(R.id.tvNombreCompleto);
        tvBadgeDelegado = view.findViewById(R.id.tvBadgeDelegado);
        tvCedula = view.findViewById(R.id.tvCedula);
        tvEquipo = view.findViewById(R.id.tvEquipo);
        tvIdInscripcion = view.findViewById(R.id.tvIdInscripcion);
        tvTelefono = view.findViewById(R.id.tvTelefono);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvFechaInscripcion = view.findViewById(R.id.tvFechaInscripcion);
        imgFotoJugador = view.findViewById(R.id.imgFotoJugador);
        btnCerrarCarnet = view.findViewById(R.id.btnCerrar);

        tvTorneoNombre.setText(jugadorCarnet.getNombreTorneo());
        tvCategoria.setText(jugadorCarnet.getCategoriaTorneo());
        String nombreCompleto = jugadorCarnet.getNombre()+jugadorCarnet.getApellidos();
        tvNombreCompleto.setText(nombreCompleto);
        tvBadgeDelegado.setText(jugadorCarnet.getEsDelegado()?"Delegado":"Jugador");
        tvCedula.setText(jugadorCarnet.getCedula());
        tvEquipo.setText(jugadorCarnet.getNombreEquipo());
        tvIdInscripcion.setText(String.valueOf(jugadorCarnet.getIdInscripcion()));
        tvTelefono.setText(jugadorCarnet.getTelefono());
        tvEmail.setText(jugadorCarnet.getEmail());
        String fechayHora = formatearFechaHoraUser.formatearFechaDesdeJSON(jugadorCarnet.getFechaInscripcion().toString());

        tvFechaInscripcion.setText(fechayHora);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        btnCerrarCarnet.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    //metodos para crear traspasos
    public void ventanaTraspaso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_traspasos, null);

        Button btnCancelarTraspaso = view.findViewById(R.id.btnCancelar);
        Button btnAceptarTraspaso = view.findViewById(R.id.btnAceptar);

        campoAsuntoTraspaso = view.findViewById(R.id.editTextAsuntoTraspaso);
        campoAsuntoTraspaso.setHint(R.string.escribe_detallada_traspaso);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        btnAceptarTraspaso.setOnClickListener(v -> {
            Integer idJugador = jugadorResponse.getIdJugador();
            Integer idTorneoEquipoJugador = jugadorResponse.getIdTorneoEquipoJugador();
            Integer idTorneoEquipoSolicita = mapIdEquipo.get(autoCompleteTeamSearch.getText().toString());
            String asuntoTraspaso = campoAsuntoTraspaso.getText() != null?
                    campoAsuntoTraspaso.getText().toString().trim():"";
            TraspasoCreate traspaso = new TraspasoCreate(
                    idJugador,
                    idTorneoEquipoJugador,
                    idTorneoEquipoSolicita,
                    asuntoTraspaso
            );
            ejecutarCreacionTraspaso(traspaso);
            dialog.dismiss();
        });

        btnCancelarTraspaso.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
    private void ejecutarCreacionTraspaso(TraspasoCreate traspaso){
        //solicita los permisos necesarios para descargar el pdf
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return; // Detener hasta que dé el permiso
            }
        }
        crearTraspaso(traspaso);
    }
    private void crearTraspaso(TraspasoCreate traspaso){
        progressBarGoldesk.mostrarCargando(true);
        traspasoRepository.crearTraspaso(traspaso, new TraspasoRepository.TraspasoCallback() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                progressBarGoldesk.mostrarCargando(false);
                try {
                    // ÉXITO: Guardamos el PDF
                    String nombre = "Solicitud_Traspaso_" + System.currentTimeMillis();
                    PdfResponse.guardarPdfEnDescargas(EquiposActivity.this, response.body(), nombre);
                    dialogsResponse.mostrarDialogoSuccess(
                            "Solicitud de Traspaso",
                            "La solicitud de traspaso se creó correctamente"
                    );
                } catch (IOException e) {
                    onError("Error al escribir el archivo");
                }
            }
            @Override
            public void onError(String mensaje) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Solicitud de Traspaso",
                        mensaje);
            }
        });
    }

    //metodos para animaciones
    public void animarDialogInscripcion() {
        btnGuardar.setText(R.string.Inscribir_a_mi_Equipo);
        btnCancelar.setText(R.string.cancelar);
        campoCedula.setEnabled(false);
        campoCedula.setBackgroundResource(R.drawable.bg_edittext_secondary);
        // 1. Medir la altura objetivo (el tamaño del contenido)
        CardViewCamposIncripcion.measure(
                View.MeasureSpec.makeMeasureSpec(CardViewCamposIncripcion.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        int targetHeight = CardViewCamposIncripcion.getMeasuredHeight();
        int initialHeight = CardViewCamposIncripcion.getHeight();

        // 2. Animar entre números reales
        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, targetHeight);
        animator.addUpdateListener(animation -> {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) CardViewCamposIncripcion.getLayoutParams();
            params.height = (int) animation.getAnimatedValue();
            params.topMargin = dpToPx(16);
            CardViewCamposIncripcion.setLayoutParams(params);
        });

        // 3. Al finalizar, ponemos WRAP_CONTENT por seguridad dinámica
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                CardViewCamposIncripcion.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                CardViewCamposIncripcion.requestLayout(); // Refresco final
            }
        });

        animator.setDuration(500);
        animator.start();
    }
    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
    private void limpiarTablero(){
        autoCompleteTeamSearch.setText("");
        editTextTeamName.setText("");
        AdapterEstadisticasJugadores adapter = new AdapterEstadisticasJugadores(
                EquiposActivity.this,
                new ArrayList<>()
        );
        recyclerViewPlayers.setAdapter(adapter);
    }

}