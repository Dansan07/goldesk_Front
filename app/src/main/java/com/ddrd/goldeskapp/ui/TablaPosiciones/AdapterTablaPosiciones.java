package com.ddrd.goldeskapp.ui.TablaPosiciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.TablaPosiciones.TablaPosicionesResponse;

import java.util.List;

public class AdapterTablaPosiciones extends RecyclerView.Adapter<AdapterTablaPosiciones.viewHolder> {

    private final Context context;
    private final List<TablaPosicionesResponse> tablaPosiciones;

    public AdapterTablaPosiciones(Context context, List<TablaPosicionesResponse> tablaPosiciones) {
        this.context = context;
        this.tablaPosiciones = tablaPosiciones;
    }

    @NonNull
    @Override
    public AdapterTablaPosiciones.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabla_posiciones, parent, false);
        return new AdapterTablaPosiciones.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTablaPosiciones.viewHolder holder, int position) {
        TablaPosicionesResponse tablaPosicion = tablaPosiciones.get(position);
        holder.tvPosicion.setText(String.valueOf(position+1));
        holder.tvNombreEquipo.setText(tablaPosicion.getNombreEquipo());
        holder.tvPartidosJugados.setText(String.valueOf(tablaPosicion.getPj()));
        holder.tvPartidosGanados.setText(String.valueOf(tablaPosicion.getPg()));
        holder.tvPartidosEmpatados.setText(String.valueOf(tablaPosicion.getPe()));
        holder.tvPartidosPerdidos.setText(String.valueOf(tablaPosicion.getPp()));
        holder.tvGolesAFavor.setText(String.valueOf(tablaPosicion.getGf()));
        holder.tvGolesEnContra.setText(String.valueOf(tablaPosicion.getGc()));
        holder.tvDiferenciaGoles.setText(String.valueOf(tablaPosicion.getDg()));
        holder.tvPuntos.setText(String.valueOf(tablaPosicion.getPts()));
    }

    @Override
    public int getItemCount() {
        return tablaPosiciones.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvPosicion, tvNombreEquipo, tvPartidosJugados, tvPartidosGanados,
                tvPartidosEmpatados, tvPartidosPerdidos, tvGolesAFavor, tvGolesEnContra,
                tvDiferenciaGoles, tvPuntos;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosicion = itemView.findViewById(R.id.tvPosicion);
            tvNombreEquipo = itemView.findViewById(R.id.tvNombreEquipo);
            tvPartidosJugados = itemView.findViewById(R.id.tvPartidosJugados);
            tvPartidosGanados = itemView.findViewById(R.id.tvPartidosGanados);
            tvPartidosEmpatados = itemView.findViewById(R.id.tvPartidosEmpatados);
            tvPartidosPerdidos = itemView.findViewById(R.id.tvPartidosPerdidos);
            tvGolesAFavor = itemView.findViewById(R.id.tvGolesAFavor);
            tvGolesEnContra = itemView.findViewById(R.id.golesEnContra);
            tvDiferenciaGoles = itemView.findViewById(R.id.diferenciaGoles);
            tvPuntos = itemView.findViewById(R.id.tvPuntos);

        }
    }
}
