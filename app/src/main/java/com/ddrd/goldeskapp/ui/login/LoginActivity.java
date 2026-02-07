package com.ddrd.goldeskapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddrd.goldeskapp.ui.main.MainActivity;
import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.login.LoginCodigoResponse;
import com.ddrd.goldeskapp.data.model.login.LoginOrganizadorResponse;
import com.ddrd.goldeskapp.data.repository.AuthRepository;
import com.ddrd.goldeskapp.ui.utilities.ProgressBarGoldesk;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private DialogsResponse dialogsResponse;
    private TextInputEditText etAccessCode, etPassword;
    private TextInputLayout tilPassword, tilAccessCode;
    private MaterialButton btnLogin, btnRegistroInvitado, btnRoleDelegado, btnRoleOrganizador;
    private AuthRepository authRepository;
    private MaterialButtonToggleGroup toggleRoleGroup;
    private TextView tvForgotPassword;
    private ProgressBarGoldesk progressBarGoldesk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 1. Inicializar las vistas y el repositorio
        initViews();
        // 2. Configurar los clics de los botones
        setupListeners();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        dialogsResponse = new DialogsResponse(this);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etAccessCode = findViewById(R.id.etAccessCode);
        etPassword = findViewById(R.id.etPassword);
        tilPassword = findViewById(R.id.tilPassword);
        tilAccessCode = findViewById(R.id.tilAccessCode);
        btnLogin = findViewById(R.id.btnLogin);
        btnRoleOrganizador=findViewById(R.id.btnRoleOrganizador);
        btnRoleDelegado=findViewById(R.id.btnRoleDelegado);
        btnRegistroInvitado = findViewById(R.id.btnRegistroInvitado);
        toggleRoleGroup = findViewById(R.id.toggleRoleGroup);
        progressBarGoldesk = new ProgressBarGoldesk(this);


        // El repositorio necesita el contexto para inicializar TokenManager
        authRepository = new AuthRepository(this);

        //estado inicial
        toggleRoleGroup.check(R.id.btnRoleOrganizador);
        actualizarUI(R.id.btnRoleOrganizador);
    }

    private void sendEmailForgotPassword(){
        String email= etAccessCode.getText().toString().trim();

        if (email.isEmpty()){
            etAccessCode.setError("Ingresa tu correo para recuperar la clave");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etAccessCode.setError("Formato de correo inválido");
            return;
        }
        tvForgotPassword.setEnabled(false);
        progressBarGoldesk.mostrarCargando(true);
        authRepository.recuperarContrasenaOrg(email, new AuthRepository.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                progressBarGoldesk.mostrarCargando(false);
                tvForgotPassword.setEnabled(true);
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Correo Enviado")
                        .setMessage("Se ha enviado una contraseña temporal a tu correo.")
                        .setPositiveButton("Aceptar", null)
                        .show();
            }
            @Override
            public void onError(String error) {
                progressBarGoldesk.mostrarCargando(false);
                tvForgotPassword.setEnabled(true);
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método de apoyo para mantener la Limpieza y Optimización
    private void actualizarUI(int checkedId) {
        if (checkedId == R.id.btnRoleDelegado) {
            tilAccessCode.setHint(getString(R.string.codigo_hint));
            etAccessCode.setInputType(android.text.InputType.TYPE_CLASS_TEXT|
                    InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            tilPassword.setVisibility(View.GONE);
            tvForgotPassword.setVisibility(View.GONE);
        } else if (checkedId == R.id.btnRoleOrganizador) {
            tilAccessCode.setHint(getString(R.string.email_hint));
            etAccessCode.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            tilPassword.setVisibility(View.VISIBLE);
            tvForgotPassword.setVisibility(View.VISIBLE);
        } else if (checkedId == R.id.btnRegistroInvitado) {
            tilAccessCode.setHint(getString(R.string.invitado_hint));
            etAccessCode.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            tilPassword.setVisibility(View.GONE);
            tvForgotPassword.setVisibility(View.GONE);
            toggleRoleGroup.clearChecked();
        }
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> {
            String codigo_o_Email = etAccessCode.getText().toString().trim();
            if (codigo_o_Email.isEmpty()) {
                etAccessCode.setError("El campo es obligatorio");
            } else {
                ejecutarLogin(codigo_o_Email);
            }
        });
        btnRegistroInvitado.setOnClickListener(v -> {
            // Aquí iría la lógica para Invitados
            actualizarUI(R.id.btnRegistroInvitado);
        });
        //inicializar buttons
        toggleRoleGroup.addOnButtonCheckedListener(((group, checkedId, isChecked) -> {
            if (isChecked){
                actualizarUI(checkedId);
            }
        }));
        tvForgotPassword.setOnClickListener(v->{
            dialogsResponse.mostrarVentanaComprobacion(
                    "Recuperación de Contraseña",
                    "Esta acción hará que se genere una nueva contraseña " +
                            "temporal que será enviada a su correo registrado",
                    "Recuperar Contraseña",
                    this::sendEmailForgotPassword
            );
        });
    }


    private void ejecutarLogin(String codigo_o_Email) {
        // Deshabilitar botón para evitar múltiples peticiones (Auditoría)
        progressBarGoldesk.mostrarCargando(true);
        btnLogin.setEnabled(false);
        btnLogin.setText("Validando...");

        // 2. Identificar el rol seleccionado
        int checkedId = toggleRoleGroup.getCheckedButtonId();

        if (checkedId == R.id.btnRoleOrganizador) {
            // --- FLUJO ORGANIZADOR (Email + Password) ---
            String password = etPassword.getText().toString().trim();

            if (password.isEmpty()) {
                etPassword.setError("Contraseña obligatoria");
                resetLoginButton();
                return;
            }
            authRepository.loginOrganizador(codigo_o_Email, password, new AuthRepository.AuthCallback<LoginOrganizadorResponse>() {
                @Override
                public void onSuccess(LoginOrganizadorResponse response) {
                    // El Repository ya guardó Token, Nombre y Rol en TokenManager [cite: 2025-12-28]
                    Toast.makeText(LoginActivity.this, "Bienvenido Organizador: " + response.getPerfil().getNombre(), Toast.LENGTH_SHORT).show();
                    irAMain();
                }
                @Override
                public void onError(String error) {
                    resetLoginButton();
                    Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                }
            });
        }else {
            authRepository.loginPorCodigo(codigo_o_Email, new AuthRepository.AuthCallback<LoginCodigoResponse>() {
                @Override
                public void onSuccess(LoginCodigoResponse response) {
                    // Al tener éxito, el Token ya está guardado en SharedPreferences
                    Toast.makeText(LoginActivity.this, "Acceso concedido: " + response.getRol(), Toast.LENGTH_SHORT).show();

                    // Ir a la pantalla principal
                    irAMain();
                }

                @Override
                public void onError(String error) {
                    resetLoginButton();
                    Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void resetLoginButton() {
        progressBarGoldesk.mostrarCargando(false);
        btnLogin.setEnabled(true);
        btnLogin.setText("ENTRAR");
    }

    private void irAMain() {
        progressBarGoldesk.mostrarCargando(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Destruye el Login para que no puedan volver atrás (Auditoría) [cite: 2025-12-28]
    }
}