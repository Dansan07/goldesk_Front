package com.ddrd.goldeskapp.ui.tarjetas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetasResponse;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;

import java.util.List;

public class AdapterTarjetasParticipacion extends RecyclerView.Adapter<AdapterTarjetasParticipacion.ViewHolder> {

    private final Context context;
    private final List<TarjetasResponse> listaTarjetas;
    private DialogsResponse dialogsResponse;

    public AdapterTarjetasParticipacion(Context context, List<TarjetasResponse> listaTarjetas){
        this.context = context;
        this.listaTarjetas = listaTarjetas;
    }

    @NonNull
    @Override
    public AdapterTarjetasParticipacion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tarjetas_participacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTarjetasParticipacion.ViewHolder holder, int position) {
        TarjetasResponse tarjeta = listaTarjetas.get(position);

        String textTiempo = "Tarjeta al minuto "+tarjeta.getTiempoEvento();
        holder.tvTiempoEvento.setText(textTiempo);
        holder.tvPeridoPartido.setText(tarjeta.getPeriodoPartido());

        if (tarjeta.getTipoTarjeta().equals("amarilla")){
            holder.viewIndicadorTarjetas.setBackgroundColor(ContextCompat.getColor(context,R.color.yellowCard));
        } else if (tarjeta.getTipoTarjeta().equals("azul")) {
            holder.viewIndicadorTarjetas.setBackgroundColor(ContextCompat.getColor(context,R.color.blueCard));
        } else if (tarjeta.getTipoTarjeta().equals("roja")) {
            holder.viewIndicadorTarjetas.setBackgroundColor(ContextCompat.getColor(context,R.color.redCard));
        }

        holder.btnEliminarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsResponse = new DialogsResponse(context);
                dialogsResponse.mostrarDialogEliminarEvento(
                        "Eliminar Tarjeta",
                        "¿Está seguro que desea eliminar la tarjeta?",
                        holder.btnEliminarTarjeta.getId(),
                        tarjeta.getIdTarjeta()
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaTarjetas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTiempoEvento, tvPeridoPartido;
        ImageButton btnEliminarTarjeta;
        View viewIndicadorTarjetas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTiempoEvento = itemView.findViewById(R.id.tvTiempoEventoTarjetas);
            tvPeridoPartido = itemView.findViewById(R.id.tvPeridoPartidoTarjetas);
            btnEliminarTarjeta = itemView.findViewById(R.id.btnEliminarTarjeta);
            viewIndicadorTarjetas = itemView.findViewById(R.id.viewIndicadorTarjetas);
        }
    }
}
