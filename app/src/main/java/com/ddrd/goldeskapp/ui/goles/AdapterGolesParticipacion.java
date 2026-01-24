package com.ddrd.goldeskapp.ui.goles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.gol.GolResponse;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;

import java.util.List;

public class AdapterGolesParticipacion extends RecyclerView.Adapter<AdapterGolesParticipacion.ViewHolder> {

    private final Context context;
    private final List<GolResponse> listaGolesParticipacion;
    private DialogsResponse dialogsResponse;

    public AdapterGolesParticipacion(Context context, List<GolResponse> listaGolesParticipacion) {
        this.context = context;
        this.listaGolesParticipacion = listaGolesParticipacion;
    }

    @NonNull
    @Override
    public AdapterGolesParticipacion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goles_participacion,parent,false);
        return new AdapterGolesParticipacion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGolesParticipacion.ViewHolder holder, int position) {
        GolResponse gol = listaGolesParticipacion.get(position);

        String textTiempo = "Gol al minuto "+gol.getTiempoEvento();
        holder.tvTiempoEvento.setText(textTiempo);
        holder.tvPeridoPartido.setText(gol.getPeriodoPartido());

        holder.btnEliminarGoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsResponse = new DialogsResponse(context);
                dialogsResponse.mostrarDialogEliminarEvento(
                        "Eliminar Gol",
                        "¿Está seguro que desea eliminar el gol?",
                        holder.btnEliminarGoles.getId(),
                        gol.getIdGol()
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaGolesParticipacion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTiempoEvento, tvPeridoPartido;
        ImageButton btnEliminarGoles;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTiempoEvento = itemView.findViewById(R.id.tvTiempoEventoGoles);
            tvPeridoPartido = itemView.findViewById(R.id.tvPeridoPartidoGoles);
            btnEliminarGoles = itemView.findViewById(R.id.btnEliminarGoles);
        }
    }
}
