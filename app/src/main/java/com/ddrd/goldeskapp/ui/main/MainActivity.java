package com.ddrd.goldeskapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.api.AuthApiService;
import com.ddrd.goldeskapp.data.api.TablaPosicionesApiService;
import com.ddrd.goldeskapp.ui.TablaPosiciones.TablaPosicionesActivity;
import com.ddrd.goldeskapp.ui.historialPartidos.HistorialPartidosActivity;
import com.ddrd.goldeskapp.ui.programarPartidos.ProgramarPartidosActivity;
import com.ddrd.goldeskapp.util.TokenManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AuthApiService apiService;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView txtSaludoBienvenida;
    private TokenManager tokenManager;

    //CardView Navigation
    private CardView cardProgramarPartidos, cardResultados,
            cardClasificacion, cardContabilidad,
            cardGoleadores, cardEquipos, cardPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initComponents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void initComponents(){
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        txtSaludoBienvenida= findViewById(R.id.txtSaludoBienvenida);
        tokenManager = new TokenManager(this);

        //CardView Navigation init
        cardProgramarPartidos = findViewById(R.id.cardProgramarPartidos);
        cardResultados = findViewById(R.id.cardResultados);
        cardClasificacion = findViewById(R.id.cardClasificacion);
        cardContabilidad = findViewById(R.id.cardContabilidad);
        cardGoleadores = findViewById(R.id.cardGoleadores);
        cardEquipos = findViewById(R.id.cardEquipos);
        cardPerfil = findViewById(R.id.cardPerfil);

        //saludo de bienvenida personalizado
        txtSaludoBienvenida.setText("Bienvenido "+tokenManager.getNombre());

        //PARA PONER TEXTO AUXILIAR (OPCIONAL)
        /*setSupportActionBar(toolbar);*/

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // Esto sincroniza el icono con el estado del menú
        actualizarDatosHeader(tokenManager);
        clicksettings();
    }

    public void clicksettings(){
        cardProgramarPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ProgramarPartidosActivity.class);
                startActivity(intent);
            }
        });
        cardResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistorialPartidosActivity.class);
                startActivity(intent);
            }
        });
        cardClasificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TablaPosicionesActivity.class);
                startActivity(intent);
            }
        });

        // 4. Manejar clics del menú (Auditoría y Navegación)
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            /*if (id == R.id.nav_traspasos) {
                // Ir a la pantalla de traspasos
            } else if (id == R.id.nav_logout) {
                cerrarSesion();
            }*/

            drawerLayout.closeDrawers(); // Cerrar el menú después de hacer clic
            return true;
        });
    }
    private void actualizarDatosHeader(TokenManager tokenManager) {

        // Extraemos la información guardada
        String nombreEquipo = tokenManager.getNombre(); // ej: "Tigres"
        String rolUsuario = tokenManager.getRole();      // ej: "DELEGADO"
        String idRef = tokenManager.getCodigo();  // ej: "2" (de tu base de datos) [cite: 2025-12-28]

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        // Referenciamos los TextViews del XML que analizamos
        TextView txtName = headerView.findViewById(R.id.txtHeaderTeamName);
        TextView txtRole = headerView.findViewById(R.id.txtHeaderRole);
        //ImageView imgLogo = headerView.findViewById(R.id.imgHeaderLogo);

        // Seteamos la información real
        if (nombreEquipo != null) {
            txtName.setText(nombreEquipo);
        }

        if (rolUsuario != null) {
            // Personalizamos el subtítulo con el Rol y el ID para Auditoría [cite: 2025-12-28]
            txtRole.setText(rolUsuario + " (ID: " + idRef + ")");
        }
    }
}