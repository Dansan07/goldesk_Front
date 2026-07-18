package com.ddrd.goldeskapp.ui.torneos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.torneo.SpinnerTorneoResponse;

import java.util.List;

public class AdapterTorneos extends RecyclerView.Adapter<AdapterTorneos.ViewHolder> {

    private Context context;
    private List<SpinnerTorneoResponse> listaTorneos;
    public AdapterTorneos(Context context, List<SpinnerTorneoResponse> listaTorneos) {
        this.context = context;
        this.listaTorneos = listaTorneos;
    }

    @NonNull
    @Override
    public AdapterTorneos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_torneo_gestion, parent, false);
        return new AdapterTorneos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTorneos.ViewHolder holder, int position) {
        SpinnerTorneoResponse torneo = listaTorneos.get(position);
        holder.textViewNombreTorneo.setText(torneo.getNombreTorneo());
        holder.textViewEstadoTorneo.setText(torneo.getActivo()?"Activo":"Inactivo");
        holder.btnToggleEstadoTorneo.setText(torneo.getActivo()?"Desactivar":"Activar");
        holder.btnToggleEstadoTorneo.setTextColor(ContextCompat.getColor(context, torneo.getActivo()?R.color.error_red:R.color.clasificacion_directa));
        holder.btnToggleEstadoTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lógica para inactivar torneo.
                if (torneo.getActivo()){
                    torneo.setActivo(false);
                    holder.textViewEstadoTorneo.setText("Inactivo");
                    holder.btnToggleEstadoTorneo.setText("Activar");
                    holder.btnToggleEstadoTorneo.setTextColor(ContextCompat.getColor(context, R.color.clasificacion_directa));
                    ((TorneosActivity)context).desactivarTorneo(torneo.getIdTorneo());
                }else{
                    torneo.setActivo(true);
                    holder.textViewEstadoTorneo.setText("Activo");
                    holder.btnToggleEstadoTorneo.setText("Desactivar");
                    holder.btnToggleEstadoTorneo.setTextColor(ContextCompat.getColor(context, R.color.error_red));
                    ((TorneosActivity)context).activarTorneo(torneo.getIdTorneo());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return listaTorneos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombreTorneo, textViewEstadoTorneo;
        private Button btnToggleEstadoTorneo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreTorneo = itemView.findViewById(R.id.textViewNombreTorneoGestion);
            textViewEstadoTorneo = itemView.findViewById(R.id.textViewEstadoTorneoGestion);
            btnToggleEstadoTorneo = itemView.findViewById(R.id.btnToggleEstadoTorneo);
        }
    }
}
