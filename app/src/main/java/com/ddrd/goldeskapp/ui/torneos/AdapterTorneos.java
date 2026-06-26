package com.ddrd.goldeskapp.ui.torneos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        holder.btnToggleEstadoTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lógica para inactivar torneo.
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
