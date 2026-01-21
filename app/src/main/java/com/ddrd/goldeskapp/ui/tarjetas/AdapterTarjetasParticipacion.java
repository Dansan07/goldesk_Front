package com.ddrd.goldeskapp.ui.tarjetas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;

import java.util.List;

public class AdapterTarjetasParticipacion extends RecyclerView.Adapter<AdapterTarjetasParticipacion.ViewHolder> {

    private final Context context;
    private final List<TarjetasResponse> listaTarjetas;

    public AdapterTarjetasParticipacion(Context context, List<TarjetasResponse> listaTarjetas){
        this.context = context;
        this.listaTarjetas = listaTarjetas;
    }

    @NonNull
    @Override
    public AdapterTarjetasParticipacion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_estadistica, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTarjetasParticipacion.ViewHolder holder, int position) {
        TarjetasResponse tarjeta = listaTarjetas.get(position);

        String textTiempo = "Tarjeta al minuto "+tarjeta.getTiempoEvento();
        holder.tvTiempoEvento.setText(textTiempo);
        holder.tvPeridoPartido.setText(tarjeta.getPeriodoPartido());


    }

    @Override
    public int getItemCount() {
        return listaTarjetas.size();
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
