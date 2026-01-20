package com.ddrd.goldeskapp.ui.historialPartidos;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.partido.PartidosHistorialResponse;
import com.ddrd.goldeskapp.ui.planillaDigital.PlanillaDigitalActivity;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraUser;

import java.util.List;

public class AdapterHistorialPartidos extends RecyclerView.Adapter<AdapterHistorialPartidos.PartidoViewHolder> {

    private final List<PartidosHistorialResponse> historialPartidos;
    private final Context context;
    private FormatearFechaHoraUser formatearFechaHoraUser = new FormatearFechaHoraUser();

    public AdapterHistorialPartidos(Context context, List<PartidosHistorialResponse> historialPartidos) {
        this.context = context;
        this.historialPartidos = historialPartidos;
    }

    @NonNull
    @Override
    public AdapterHistorialPartidos.PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_partido_historial, null);
        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorialPartidos.PartidoViewHolder holder, int position) {
        PartidosHistorialResponse partido = historialPartidos.get(position);
        holder.textViewLocalTeam.setText(partido.getNombreLocal());
        holder.textViewVisitorTeam.setText(partido.getNombreVisitante());
        holder.textViewLocalScore.setText(String.valueOf(partido.getGolesLocal()));
        holder.textViewVisitorScore.setText(String.valueOf(partido.getGolesVisitante()));
        holder.textViewDate.setText(
                formatearFechaHoraUser.formatearFechaUser(partido.getFechaPartido()));
        holder.textViewTime.setText(
                formatearFechaHoraUser.formatearHoraUser(partido.getHoraPartido()));
        holder.textViewLocation.setText(partido.getNombreCancha());
        holder.textViewStatus.setText(partido.getEstado());
        holder.textViewChampionship.setText(partido.getFaseTorneo());
        if (partido.getEstado().equals("FINALIZADO")){
            holder.textViewStatus.setBackgroundColor(
                    ContextCompat.getColor(
                            holder.itemView.getContext(),R.color.partido_finalizado));
            holder.btnEditMatch.setVisibility(View.GONE);
            holder.btnDeleteMatch.setVisibility(View.GONE);
        } else if (partido.getEstado().equals("PROGRAMADO")) {
            holder.textViewStatus.setBackgroundColor(
                    ContextCompat.getColor(
                            holder.itemView.getContext(),R.color.partido_programado));            
        } else if (partido.getEstado().equals("EN CURSO")) {
            holder.textViewStatus.setBackgroundColor(
                    ContextCompat.getColor(holder.itemView.getContext(),R.color.partido_en_curso));
            holder.btnDeleteMatch.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanillaDigitalActivity.class);
                intent.putExtra("idPartido", partido.getIdPartido());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historialPartidos.size();
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLocalTeam, textViewVisitorTeam,
                textViewLocalScore, textViewVisitorScore,
                textViewDate, textViewTime, textViewLocation,
                textViewStatus, textViewChampionship;
        Button btnEditMatch, btnDeleteMatch;

        public PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocalTeam = itemView.findViewById(R.id.textViewLocalTeam);
            textViewVisitorTeam = itemView.findViewById(R.id.textViewVisitorTeam);
            textViewLocalScore = itemView.findViewById(R.id.textViewLocalScore);
            textViewVisitorScore = itemView.findViewById(R.id.textViewVisitorScore);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewChampionship = itemView.findViewById(R.id.textViewChampionship);
            btnEditMatch = itemView.findViewById(R.id.btnEditMatch);
            btnDeleteMatch = itemView.findViewById(R.id.btnDeleteMatch);
        }
    }
}
