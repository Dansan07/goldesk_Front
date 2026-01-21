package com.ddrd.goldeskapp.ui.goles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.gol.GolResponse;

import java.util.List;

public class AdapterGolesParticipacion extends RecyclerView.Adapter<AdapterGolesParticipacion.ViewHolder> {

    private final Context context;
    private final List<GolResponse> listaGolesParticipacion;

    public AdapterGolesParticipacion(Context context, List<GolResponse> listaGolesParticipacion) {
        this.context = context;
        this.listaGolesParticipacion = listaGolesParticipacion;
    }

    @NonNull
    @Override
    public AdapterGolesParticipacion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_estadistica, null);
        return new AdapterGolesParticipacion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGolesParticipacion.ViewHolder holder, int position) {
        GolResponse gol = listaGolesParticipacion.get(position);

        String textTiempo = "Gol al minuto "+gol.getTiempoEvento();
        holder.tvTiempoEvento.setText(textTiempo);
        holder.tvPeridoPartido.setText(gol.getPeriodoPartido());


    }

    @Override
    public int getItemCount() {
        return listaGolesParticipacion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTiempoEvento, tvPeridoPartido;
        ImageButton btnEliminar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTiempoEvento = itemView.findViewById(R.id.tvTiempoEvento);
            tvPeridoPartido = itemView.findViewById(R.id.tvPeridoPartido);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
