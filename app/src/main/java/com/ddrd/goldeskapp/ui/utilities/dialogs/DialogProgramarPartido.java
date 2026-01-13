package com.ddrd.goldeskapp.ui.utilities.dialogs;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.ddrd.goldeskapp.ui.programarPartidos.ProgramarPartidosActivity;

public class DialogProgramarPartido {

    private Context context;

    public DialogProgramarPartido(Context context) {
        this.context = context;
    }

    public void mostrarDialogoNoContentTorneos(String titulo, String mensaje, String accion) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (dialog, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(this, CrearTorneoActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "Navegando a creación de Torneos...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
    public void mostrarDialogoNoContentEquipos(String titulo, String mensaje, String accion) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (dialog, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(this, CrearTorneoActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "Navegando a creación de Equipos...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    public void mostrarDialogoNoContentPartidos(String titulo, String mensaje, String accion) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (dialog, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(this, CrearTorneoActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "Navegando a creación de Partidos...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    public void mostrarDialogoError(String error) {
        // Verificación de seguridad profesional
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                return; // No intentamos mostrar nada si la actividad está muriendo
            }
        }

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Error de Conexión")
                .setMessage("No pudimos conectar con el servidor: " + error)
                .setNegativeButton("Salir", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public void mostrarDialogoInformacion(String titulo, String mensaje, ProgramarPartidosActivity activity) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton("Continuar", (dialog, which) -> {
                    // Llamamos de nuevo al guardado pero con el flag en TRUE
                    activity.limpiarTablero();
                })
                .setNegativeButton("Salir", (dialog, which) -> {
                    dialog.dismiss();
                    activity.finish();
                })
                .show();
    }

    public void mostrarDialogoDuplicado(String titulo, String mensaje, ProgramarPartidosActivity activity) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton("Sí, Programar", (dialog, which) -> {
                    // Llamamos de nuevo al guardado pero con el flag en TRUE
                    activity.guardarPartido(true);
                })
                .setNegativeButton("No, revisar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
    public void mostrarDialogoCancelar(String titulo, String mensaje, String accion) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false) // Obliga al usuario a elegir una opción
                .setPositiveButton(accion, (dialog, which) -> {
                    // Aquí pones la navegación a tu actividad de creación
                    // Intent intent = new Intent(context, CrearTorneoActivity.class);
                    // startActivity(intent);
                    //Toast.makeText(context, "Navegando a creación...", Toast.LENGTH_SHORT).show();
                    ((Activity) context).finish();
                    ((ProgramarPartidosActivity) context).limpiarTablero();
                })
                .setNegativeButton("No, continuar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
