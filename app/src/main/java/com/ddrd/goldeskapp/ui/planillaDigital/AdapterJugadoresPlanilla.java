package com.ddrd.goldeskapp.ui.planillaDigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.gol.GolCreate;
import com.ddrd.goldeskapp.data.model.jugador.JugadorPlanillaResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;

import java.util.List;

public class AdapterJugadoresPlanilla extends RecyclerView.Adapter<AdapterJugadoresPlanilla.JugadoresPlanillaViewHolder> {
    private final Context context;
    private final List<JugadorPlanillaResponse> jugadores;
    private final String statusPartido;


    public AdapterJugadoresPlanilla(Context context, List<JugadorPlanillaResponse> jugadores, String statusPartido) {
        this.context = context;
        this.jugadores = jugadores;
        this.statusPartido = statusPartido;
    }

    @NonNull
    @Override
    public AdapterJugadoresPlanilla.JugadoresPlanillaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jugadores_planilla, parent, false);
        return new JugadoresPlanillaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJugadoresPlanilla.JugadoresPlanillaViewHolder holder, int position) {
        JugadorPlanillaResponse jugador = jugadores.get(position);
        holder.textViewPlayerNumber.setText(
                String.valueOf(jugador.getDorsal()==null?
                        "-":jugador.getDorsal()));
        holder.textViewPlayerName.setText(jugador.getNombreJugador());
        holder.textViewGoals.setText(String.valueOf(jugador.getCantGoles()));
        holder.textViewYellowCards.setText(String.valueOf(jugador.getCantAmarillas()));
        holder.textViewBlueCards.setText(String.valueOf(jugador.getCantAzules()));
        holder.textViewRedCards.setText(String.valueOf(jugador.getCantRojas()));
        /*//configurar click
        if (!jugador.getCantRojas().equals(0)){
            holder.itemView.setEnabled(false);
        }*/

        holder.itemView.setEnabled(statusPartido.equals("EN CURSO"));
        holder.textViewPlayerNumber.setEnabled(statusPartido.equals("EN CURSO"));

        holder.textViewPlayerNumber.setOnClickListener(v -> {
            mostrarVentadaDorsalJugador(jugador);
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView recyclerView= (RecyclerView) holder.itemView.getParent();
                Integer idRecicler = recyclerView.getId();
                ((PlanillaDigitalActivity)context).mostrarVentadaInsertEvent(
                        new GolCreate(jugador.getIdReferencia(),
                                null,
                                null),
                        new TarjetaCreate(jugador.getIdReferencia(),
                                null,null,null,
                                null, null),
                        String.valueOf(jugador.getDorsal()),
                        jugador.getNombreJugador(),
                        idRecicler
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    private void mostrarVentadaDorsalJugador(JugadorPlanillaResponse jugador){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_dorsal_jugador, null);

        //init elements
        TextView tvNombreJugador = view.findViewById(R.id.tvNombreJugador);
        NumberPicker numberPickerDorsal = view.findViewById(R.id.numberPickerDorsal);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);
        Button btnAplicar = view.findViewById(R.id.btnAplicar);

        //config numero Dorsal
        numberPickerDorsal.setMinValue(0);
        numberPickerDorsal.setMaxValue(100);
        numberPickerDorsal.setValue(10);

        //config nombre jugador
        tvNombreJugador.setText(jugador.getNombreJugador());

        builder.setView(view);
        AlertDialog dialog = builder.create();
        btnCancelar.setOnClickListener(v -> {dialog.dismiss();});
        btnAplicar.setOnClickListener(v -> {
            numberPickerDorsal.clearFocus();
            String numberjugador = String.valueOf(numberPickerDorsal.getValue());
            jugador.setDorsal(numberjugador);
            ((PlanillaDigitalActivity)context).actualizarDorsal(jugador);
            dialog.dismiss();
        });
        dialog.show();
    }

    public static class JugadoresPlanillaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlayerNumber, textViewPlayerName,
                textViewGoals, textViewYellowCards,
                textViewBlueCards, textViewRedCards;
        public JugadoresPlanillaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlayerNumber = itemView.findViewById(R.id.textViewPlayerNumber);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            textViewGoals = itemView.findViewById(R.id.textViewGoals);
            textViewYellowCards = itemView.findViewById(R.id.textViewYellowCards);
            textViewBlueCards = itemView.findViewById(R.id.textViewBlueCards);
            textViewRedCards = itemView.findViewById(R.id.textViewRedCards);
        }
    }
}
