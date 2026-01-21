package com.ddrd.goldeskapp.ui.utilities.dialogs;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.ui.programarPartidos.ProgramarPartidosActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogsResponse {

    private final Context context;

    public DialogsResponse(Context context) {
        this.context = context;
    }

    public void mostrarDialogoNoContentTorneos(String titulo, String mensaje, String accion) {
        AlertDialog dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Warning)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (d, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(this, CrearTorneoActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "Navegando a creación de Torneos...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (d, which) -> {
                    d.dismiss();
                })

                .create();
        configurarYMostrar(dialog);

    }
    public void mostrarDialogoNoContentEquipos(String titulo, String mensaje, String accion) {
        AlertDialog dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Warning)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (d, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(this, CrearTorneoActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "Navegando a creación de Equipos...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (d, which) -> {
                    d.dismiss();
                })
                .create();
        configurarYMostrar(dialog);

    }

    public void mostrarDialogoNoContentPartidos(String titulo, String mensaje, String accion) {
        AlertDialog dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Warning)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (d, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(this, CrearTorneoActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "Navegando a creación de Partidos...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (d, which) -> {
                    d.dismiss();
                })
                .create();
        configurarYMostrar(dialog);

    }

    public void mostrarDialogoError(String error) {
        // Verificación de seguridad profesional
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                return; // No intentamos mostrar nada si la actividad está muriendo
            }
        }

        AlertDialog dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Destructive)
                .setTitle("Error de Conexión")
                .setMessage("No pudimos conectar con el servidor: " + error)
                .setNegativeButton("Salir", (d, which) -> d.dismiss())
                .create();
        configurarYMostrar(dialog);

    }

    public void mostrarDialogoSuccess(String titulo, String mensaje) {
        AlertDialog dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Info)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton("Continuar", (d, which) -> {
                    d.dismiss();
                    ((Activity) context).finish();
                })
                .create();
        configurarYMostrar(dialog);

    }

    public void mostrarDialogoPartidoProgramado(String titulo, String mensaje, ProgramarPartidosActivity activity) {
        AlertDialog dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Info)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton("Continuar", (d, which) -> {
                    // Llamamos de nuevo al guardado pero con el flag en TRUE
                    activity.limpiarTablero();
                })
                .setNegativeButton("Salir", (d, which) -> {
                    d.dismiss();
                    activity.finish();
                })
                .create();
        configurarYMostrar(dialog);

    }

    public void mostrarDialogoInformacion(String titulo, String mensaje) {
        AlertDialog dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Info)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton("Entiendo", (d, which) -> {
                    // Llamamos de nuevo al guardado pero con el flag en TRUE
                })
                .create();
        configurarYMostrar(dialog);

    }

    public void mostrarDialogoDuplicado(String titulo, String mensaje, ProgramarPartidosActivity activity) {
        AlertDialog dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Warning)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("Sí, Programar", (d, which) -> {
                    activity.guardarPartido(true);
                })
                .setNegativeButton("No, revisar", (d, which) -> d.dismiss())
                .create(); // Cambiamos .show() por .create()

        // ESTA LÍNEA ES LA QUE HACE APARECER EL BORDE
        configurarYMostrar(dialog);


    }
    public void mostrarDialogoCancelar(String titulo, String mensaje, String accion) {
        AlertDialog dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme_Warning)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (d, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(context, CrearTorneoActivity.class);
                    // startActivity(intent);
                    //Toast.makeText(context, "Navegando a creación...", Toast.LENGTH_SHORT).show();
                    ((Activity) context).finish();
                    ((ProgramarPartidosActivity) context).limpiarTablero();
                })
                .setNegativeButton("No, continuar", (d, which) -> {
                    d.dismiss();
                })
                .create();

        configurarYMostrar(dialog);


    }

    private void configurarYMostrar(AlertDialog dialog) {
        dialog.show();
    }
}
