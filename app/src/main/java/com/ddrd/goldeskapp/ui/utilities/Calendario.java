package com.ddrd.goldeskapp.ui.utilities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Calendario {

    private final Context context;

    public Calendario(Context context) {
        this.context = context;
    }

    public void abrirCalendario(EditText editTextDate) {
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {
            Calendar calSeleccionada = Calendar.getInstance();
            calSeleccionada.set(year, monthOfYear, dayOfMonth);

            Locale localeEspanol = new Locale("es", "ES");

            // Formato: día de la semana, día/mes/año
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", localeEspanol);
            String fechaFormateada = sdf.format(calSeleccionada.getTime());

            editTextDate.setText(fechaFormateada);
        }, anio, mes, dia);

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public void abrirReloj(EditText editTextTime) {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);

        // El último parámetro 'false' activa el formato de 12 horas (AM/PM)
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            Calendar calHora = Calendar.getInstance();
            calHora.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calHora.set(Calendar.MINUTE, minute);

            Locale localeEspanol = new Locale("es", "ES");

            // Formato: hora:minutos am/pm
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", localeEspanol);
            String horaFormateada = sdf.format(calHora.getTime());

            editTextTime.setText(horaFormateada);
        }, hora, minuto, false);

        timePickerDialog.show();
    }
}
