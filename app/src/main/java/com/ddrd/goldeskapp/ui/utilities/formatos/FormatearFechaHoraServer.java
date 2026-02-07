package com.ddrd.goldeskapp.ui.utilities.formatos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;

public class FormatearFechaHoraServer {

    public FormatearFechaHoraServer() {
    }

    public String formatearFechaServer(String fecha){

        try {
            SimpleDateFormat sdfLectura = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("es", "ES"));
            SimpleDateFormat sdfEscritura = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return sdfEscritura.format(sdfLectura.parse(fecha));
        } catch (ParseException e) {
            // Auditoría: Registrar que el formato de entrada fue inválido
            return null;
        }

    }

    public String formatearHoraServer(String hora){

        try {
            // Formateadores para leer lo que hay en el EditText (Español)
            SimpleDateFormat hfLectura = new SimpleDateFormat("hh:mm a", new Locale("es", "ES"));
            // Formateadores para enviar al Servidor (Estándar ISO)
            SimpleDateFormat hfEscritura = new SimpleDateFormat("HH:mm:ss", Locale.US);
            // Realizamos la conversión
            return hfEscritura.format(hfLectura.parse(hora));
        }catch (ParseException e){
            // Auditoría: Registrar que el formato de entrada fue inválido
            return null;
        }

    }
}
