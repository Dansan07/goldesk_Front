package com.ddrd.goldeskapp.ui.usuarios;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.organizador.ActualizaDatosOrg;
import com.ddrd.goldeskapp.data.model.organizador.ActualizaPassOrg;
import com.ddrd.goldeskapp.data.model.organizador.OrganizadorResponse;
import com.ddrd.goldeskapp.data.repository.OrganizadorRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.util.TokenManager;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private TokenManager tokenManager;
    private DialogsResponse dialogsResponse;
    private ProgressBarGoldesk progressBarGoldesk;
    private OrganizadorRepository organizadorRepository;
    private EditText editTextNombreUsuario,
                    editTextApellidosUsuario,
                    editTextTelefonoUsuario,
                    editTextEmailUsuario,
                    editTextActualizarPass;
    private TextView textViewCodigoInvitado,
                    textViewCuentaStatus;
    private SwitchCompat switchStatusCuenta;
    private Button btnGuardar,
                    btnCopiarCodigoInvitado,
                    btnCambiarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario);

        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initComponents(){
        tokenManager = new TokenManager(this);
        dialogsResponse = new DialogsResponse(this);
        progressBarGoldesk = new ProgressBarGoldesk(this);
        //Repositories
        organizadorRepository = new OrganizadorRepository(this);
        //init editTexts
        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        editTextApellidosUsuario = findViewById(R.id.editTextApellidosUsuario);
        editTextTelefonoUsuario = findViewById(R.id.editTextTelefonoUsuario);
        editTextEmailUsuario = findViewById(R.id.editTextEmailUsuario);
        editTextActualizarPass = findViewById(R.id.editTextActualizarPass);
        //init textViews
        textViewCodigoInvitado = findViewById(R.id.textViewCodigoInvitado);
        textViewCuentaStatus = findViewById(R.id.textViewCuentaStatus);
        //init switches
        switchStatusCuenta = findViewById(R.id.switchStatusCuenta);
        //init buttons
        btnGuardar = findViewById(R.id.btnSave);
        btnCopiarCodigoInvitado = findViewById(R.id.btnCopiarCodigoInvitado);
        btnCambiarPassword = findViewById(R.id.btnCambiarPassword);

        llenarInformacionPersonal();
        configClickListener();

    }

    private void configClickListener(){
        btnGuardar.setOnClickListener(v -> {
            validarInformacion();
        });
        btnCopiarCodigoInvitado.setOnClickListener(v -> {
            copiarCodigoInvitado();
        });
        btnCambiarPassword.setOnClickListener(v -> {
            //cambiar contraseña
            if (editTextActualizarPass.getText().toString().isEmpty()){
                editTextActualizarPass.setError("Debe Escribir una Contraseña");
                return;
            }
            dialogsResponse.mostrarVentanaComprobacion(
                    "Actualización de Contraseña",
                    "¿Estás seguro que deseas actualizar tu contraseña?",
                    "Actualizar",
                    this::actualizarContrasena
            );
        });
    }

    private void llenarInformacionPersonal(){
        OrganizadorResponse response = tokenManager.getOrganizador();
        editTextNombreUsuario.setText(response.getNombre());
        editTextApellidosUsuario.setText(response.getApellidos());
        editTextTelefonoUsuario.setText(response.getTelefono());
        editTextEmailUsuario.setText(response.getEmail());
        textViewCodigoInvitado.setText(response.getCodigoInvitado());
        switchStatusCuenta.setChecked(response.isActivo());
        if (switchStatusCuenta.isChecked()){
            textViewCuentaStatus.setText(R.string.cuenta_activa);
            switchStatusCuenta.setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(PerfilUsuarioActivity.this,R.color.partido_finalizado)));
        }else {
            textViewCuentaStatus.setText(R.string.cuenta_inactiva);
        }
    }
    private void copiarCodigoInvitado() {
        // 1. Obtener el texto del TextView
        String textoACopiar = textViewCodigoInvitado.getText().toString();

        // 2. Verificar que no esté vacío para no copiar nada
        if (!textoACopiar.isEmpty()) {
            // 3. Obtener el servicio del Portapapeles
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            // 4. Crear un objeto ClipData (etiqueta identificativa + el texto)
            ClipData clip = ClipData.newPlainText("Texto Copiado", textoACopiar);

            // 5. Guardar el clip en el portapapeles
            clipboard.setPrimaryClip(clip);

            // 6. Avisar al usuario con un Toast
            Toast.makeText(this, "Copiado al portapapeles", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay texto para copiar", Toast.LENGTH_SHORT).show();
        }
    }
    private void actualizarContrasena(){
        String email = tokenManager.getOrganizador().getEmail();
        String password = editTextActualizarPass.getText().toString();
        ActualizaPassOrg body = new ActualizaPassOrg(email,password);
        progressBarGoldesk.mostrarCargando(true);
        organizadorRepository.actualizarContrasena(body, new OrganizadorRepository.OrganizadorCallback<String>() {
            @Override
            public void onSuccess(String response) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoSuccess(
                        "Contraseña Actualizada",
                        response
                );
                editTextActualizarPass.setText("");
            }
            @Override
            public void onError(String error) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoWarning(
                        "Caracteres insuficientes", error);
            }
        });
    }
    private void actualizarInformacion(Button btnGuardar){
        progressBarGoldesk.mostrarCargando(true);
        String cedula = tokenManager.getOrganizador().getCedula();
        String nombre = editTextNombreUsuario.getText().toString();
        String apellidos = editTextApellidosUsuario.getText().toString();
        String telefono = editTextTelefonoUsuario.getText().toString();
        String email = editTextEmailUsuario.getText().toString();
        ActualizaDatosOrg body = new ActualizaDatosOrg(cedula,nombre,apellidos,telefono,email);
        organizadorRepository.actualizarDatos(body, new OrganizadorRepository.OrganizadorCallback<String>() {
            @Override
            public void onSuccess(String response) {
                progressBarGoldesk.mostrarCargando(true);
                dialogsResponse.mostrarDialogoSuccess(
                        "Información Actualizada",
                        response
                );
                btnGuardar.setEnabled(true);
            }
            @Override
            public void onError(String error) {
                progressBarGoldesk.mostrarCargando(false);
                dialogsResponse.mostrarDialogoError(error);
                btnGuardar.setEnabled(true);
            }
        });
    }
    //validar información antes de actualizar
    private void validarInformacion(){
        //verificar que no esten vacios
        if (editTextNombreUsuario.getText().toString().isEmpty()){
            editTextNombreUsuario.setError("Debe Escribir un Nombre");
            return;
        } else if (editTextApellidosUsuario.getText().toString().isEmpty()) {
            editTextApellidosUsuario.setError("Debe Escribir Apellidos");
            return;
        } else if (editTextEmailUsuario.getText().toString().isEmpty()) {
            editTextEmailUsuario.setError("Debe Escribir un Email");
            return;
        } else if (editTextTelefonoUsuario.getText().toString().isEmpty()) {
            editTextTelefonoUsuario.setError("Debe Escribir un Teléfono");
            return;
        }
        //verificar cambios
        String nombreActual =tokenManager.getOrganizador().getNombre();
        String nombreNuevo = editTextNombreUsuario.getText().toString();
        String apellidosActual =tokenManager.getOrganizador().getApellidos();
        String apellidosNuevo = editTextApellidosUsuario.getText().toString();
        String emailActual = tokenManager.getOrganizador().getEmail();
        String emailNuevo = editTextEmailUsuario.getText().toString();
        String telefonoActual = tokenManager.getOrganizador().getTelefono();
        String telefonoNuevo = editTextTelefonoUsuario.getText().toString();
        if (nombreActual.equals(nombreNuevo)&&
            apellidosActual.equals(apellidosNuevo)&&
            emailActual.equals(emailNuevo)&&
            telefonoActual.equals(telefonoNuevo)){
            dialogsResponse.mostrarDialogoWarning(
                    "No Hay Cambios",
                    "No Hay Cambios Para Guardar"
            );
            return;
        }
        //actualizo datos del sharepreferences
        OrganizadorResponse response = tokenManager.getOrganizador();
        response.setNombre(nombreNuevo);
        response.setApellidos(apellidosNuevo);
        response.setEmail(emailNuevo);
        response.setTelefono(telefonoNuevo);
        //guardar cambios
        btnGuardar.setEnabled(false);
        actualizarInformacion(btnGuardar);
        tokenManager.saveOrganizador(response);
    }
}