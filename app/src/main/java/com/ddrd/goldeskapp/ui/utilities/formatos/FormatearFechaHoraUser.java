package com.ddrd.goldeskapp.ui.utilities.formatos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatearFechaHoraUser {

    public FormatearFechaHoraUser() {
    }

    public String formatearFechaUser(String fecha){

        try {
            SimpleDateFormat sdfLectura = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("es", "ES"));
            SimpleDateFormat sdfEscritura = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return sdfLectura.format(sdfEscritura.parse(fecha));
        } catch (ParseException e) {
            // Auditoría: Registrar que el formato de entrada fue inválido
            return null;
        }

    }

    public String formatearHoraUser(String hora){

        try {
            // Formateadores para leer lo que hay en el EditText (Español)
            SimpleDateFormat hfLectura = new SimpleDateFormat("hh:mm a", new Locale("es", "ES"));
            // Formateadores para enviar al Servidor (Estándar ISO)
            SimpleDateFormat hfEscritura = new SimpleDateFormat("HH:mm:ss", Locale.US);
            // Realizamos la conversión
            return hfLectura.format(hfEscritura.parse(hora));
        }catch (ParseException e){
            // Auditoría: Registrar que el formato de entrada fue inválido
            return null;
        }

    }
}
