package com.ddrd.goldeskapp.ui.historialPartidos;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;

public class AdapterHistorialPartidos extends RecyclerView.Adapter<AdapterHistorialPartidos.PartidoViewHolder> {
    @NonNull
    @Override
    public AdapterHistorialPartidos.PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_partido_historial, null);
        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorialPartidos.PartidoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder {
        public PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
