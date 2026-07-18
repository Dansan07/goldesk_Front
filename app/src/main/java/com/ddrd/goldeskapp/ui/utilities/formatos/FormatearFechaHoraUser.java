package com.ddrd.goldeskapp.ui.utilities.formatos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String formatearFechaDesdeJSON(String fechaJSON) {
        if (fechaJSON == null || fechaJSON.isEmpty()) return "Sin fecha";

        try {
            // 1. Manejo para Android 8.0+ (API 26)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // LocalDateTime entiende el formato con 'T' automáticamente
                LocalDateTime fecha = LocalDateTime.parse(fechaJSON);
                DateTimeFormatter formatoDestino = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", new Locale("es", "ES"));
                return fecha.format(formatoDestino);
            }

            // 2. Manejo para teléfonos antiguos (API 24/25)
            // Cortamos el String antes de los nanosegundos y quitamos la 'T'
            String[] partes = fechaJSON.split("T");
            String fecha = partes[0];
            String hora = partes[1].split("\\.")[0];

            // Retornamos el formato deseado
            String fechaFormateada = formatearFechaUser(fecha);
            String horaFormateada = formatearHoraUser(hora);


            return fechaFormateada + " " + horaFormateada; // Retorna

        } catch (Exception e) {
            return fechaJSON; // Retorna el original si algo falla
        }
    }
}
