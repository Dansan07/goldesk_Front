package com.ddrd.goldeskapp.ui.utilities;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

public class ProgressBarGoldesk {

    private AlertDialog loadingDialog;
    private final Context context;

    public ProgressBarGoldesk(Context context) {
        this.context = context;
        setupDialog();
    }
    private void setupDialog() {
        android.widget.ProgressBar pb = new android.widget.ProgressBar(context);

        // Layout para centrar el progress bar
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        layout.setGravity(android.view.Gravity.CENTER);
        layout.addView(pb);

        loadingDialog = new AlertDialog.Builder(context)
                .setTitle("Procesando")
                .setMessage("Por favor, espere...")
                .setCancelable(false)
                .setView(layout) // Usamos el layout centrado
                .create();
    }

    public void mostrarCargando(boolean mostrar) {
        if (mostrar) {
            if (!loadingDialog.isShowing()) loadingDialog.show();
        } else {
            if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        }
    }
}
