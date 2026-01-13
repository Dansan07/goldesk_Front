package com.ddrd.goldeskapp.ui.utilities.spinnersContent;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;

import java.util.List;

public class SpinnerTorneo {

    public SpinnerTorneo() {
    }

    public void actualizarSpinnerTorneos(List<SpinnerTorneoResponse> torneos, Context context, Spinner spinnerChampionship) {
        ArrayAdapter<SpinnerTorneoResponse> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                torneos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChampionship.setAdapter(adapter);
    }
}
