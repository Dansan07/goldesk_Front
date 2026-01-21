package com.ddrd.goldeskapp.ui.planillaDigital;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.gol.GolCreate;
import com.ddrd.goldeskapp.data.model.jugador.JugadorPlanillaResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaCreate;

import java.util.List;

public class AdapterJugadoresPlanilla extends RecyclerView.Adapter<AdapterJugadoresPlanilla.JugadoresPlanillaViewHolder> {
    private final Context context;
    private final List<JugadorPlanillaResponse> jugadores;


    public AdapterJugadoresPlanilla(Context context, List<JugadorPlanillaResponse> jugadores) {
        this.context = context;
        this.jugadores = jugadores;
    }

    @NonNull
    @Override
    public AdapterJugadoresPlanilla.JugadoresPlanillaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_jugadores_planilla, null);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlanillaDigitalActivity)context).mostrarVentadaInsertEvent(
                        new GolCreate(jugador.getIdReferencia(),
                                null,
                                null),
                        new TarjetaCreate(jugador.getIdReferencia(),
                                null,null,null,
                                null, null),
                        String.valueOf(jugador.getDorsal()),
                        jugador.getNombreJugador()
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
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
