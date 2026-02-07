package com.ddrd.goldeskapp.ui.goleadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.tablaGoleadores.TablaGoleadoresResponse;

import java.util.List;

public class AdapterGoleadores extends RecyclerView.Adapter<AdapterGoleadores.viewHolder> {

    private final Context context;
    private final List<TablaGoleadoresResponse> tablaGoleadores;

    public AdapterGoleadores(Context context, List<TablaGoleadoresResponse> tablaGoleadores) {
        this.context = context;
        this.tablaGoleadores = tablaGoleadores;
    }

    @NonNull
    @Override
    public AdapterGoleadores.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goleador, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGoleadores.viewHolder holder, int position) {
        TablaGoleadoresResponse goleador = tablaGoleadores.get(position);
        holder.txt_posicion.setText(String.valueOf(position+1));
        holder.txt_nombre.setText(goleador.getNombreJugador());
        String partidosJugados = (goleador.getPartidosJugados()+" partidos jugados");
        holder.txt_partidos.setText(partidosJugados);
        holder.txt_equipo.setText(goleador.getNombreEquipo());
        holder.txt_goles.setText(String.valueOf(goleador.getGoles()));
    }

    @Override
    public int getItemCount() {
        return tablaGoleadores.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView txt_posicion, txt_nombre, txt_partidos, txt_equipo, txt_goles;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txt_posicion = itemView.findViewById(R.id.txt_posicionGoleador);
            txt_nombre = itemView.findViewById(R.id.txt_nombreGoleador);
            txt_partidos = itemView.findViewById(R.id.txt_partidosGoleador);
            txt_equipo = itemView.findViewById(R.id.txt_equipoGoleador);
            txt_goles = itemView.findViewById(R.id.txt_golesGoleador);
        }
    }
}
