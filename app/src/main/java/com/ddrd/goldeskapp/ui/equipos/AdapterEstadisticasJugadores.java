package com.ddrd.goldeskapp.ui.equipos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.jugador.EstadisticasJugador;

import java.util.List;

public class AdapterEstadisticasJugadores extends RecyclerView.Adapter<AdapterEstadisticasJugadores.viewHolder> {

    private final Context context;
    private final List<EstadisticasJugador> listaEstadisticas;

    public AdapterEstadisticasJugadores(Context context, List<EstadisticasJugador> listaEstadisticas) {
        this.context = context;
        this.listaEstadisticas = listaEstadisticas;
    }

    @NonNull
    @Override
    public AdapterEstadisticasJugadores.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_estadisticas_jugador,parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstadisticasJugadores.viewHolder holder, int position) {
        EstadisticasJugador estadisticasJugador = listaEstadisticas.get(position);
        holder.textPlayerName.setText(estadisticasJugador.getNombreCompleto());
        holder.textMatchesPlayed.setText(String.valueOf(estadisticasJugador.getPartidosJugados()));
        holder.textGoals.setText(String.valueOf(estadisticasJugador.getGolesAnotados()));
        holder.textYellowCards.setText(String.valueOf(estadisticasJugador.getAmarillas()));
        holder.textBlueCards.setText(String.valueOf(estadisticasJugador.getAzules()));
        holder.textRedCards.setText(String.valueOf(estadisticasJugador.getRojas()));
    }

    @Override
    public int getItemCount() {
        return listaEstadisticas.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textPlayerName, textMatchesPlayed, textGoals, textYellowCards, textBlueCards, textRedCards;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textPlayerName = itemView.findViewById(R.id.textPlayerName);
            textMatchesPlayed = itemView.findViewById(R.id.textMatchesPlayed);
            textGoals = itemView.findViewById(R.id.textGoals);
            textYellowCards = itemView.findViewById(R.id.textYellowCards);
            textBlueCards = itemView.findViewById(R.id.textBlueCards);
            textRedCards = itemView.findViewById(R.id.textRedCards);
        }
    }
}
