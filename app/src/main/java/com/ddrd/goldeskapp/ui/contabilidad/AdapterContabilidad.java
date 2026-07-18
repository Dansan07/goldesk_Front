package com.ddrd.goldeskapp.ui.contabilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.Inscripcion.InscripcionTorneoResponse;
import com.ddrd.goldeskapp.data.model.tarjeta.TarjetaTorneoResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterContabilidad extends RecyclerView.Adapter<AdapterContabilidad.ViewHolderContabilidad> {

    private List<TarjetaTorneoResponse> tarjetasTorneo;
    private List<InscripcionTorneoResponse> inscripcionesTorneo;
    private Context context;
    public AdapterContabilidad(List<TarjetaTorneoResponse> tarjetasTorneo, Context context, List<InscripcionTorneoResponse> inscripcionesTorneo) {
        this.tarjetasTorneo = tarjetasTorneo;
        this.context = context;
        this.inscripcionesTorneo = inscripcionesTorneo;
    }

    @NonNull
    @Override
    public AdapterContabilidad.ViewHolderContabilidad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contabilidad, parent, false);
        return new ViewHolderContabilidad(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContabilidad.ViewHolderContabilidad holder, int position) {
        DecimalFormat df = new DecimalFormat("$ #,###");
        if (tarjetasTorneo==null){
            InscripcionTorneoResponse inscripcion = inscripcionesTorneo.get(position);
            holder.textViewNombreJugadorContabilidad.setText(inscripcion.getNombreEquipo());
            holder.textViewEquipoContabilidad.setText(df.format(inscripcion.getSaldoPendiente()));
            holder.textViewMontoContabilidad.setText(df.format(inscripcion.getMontoAbonado()));
            holder.itemView.setOnClickListener(view -> {
                ((ContabilidadActivity) context).abrirDialogDetallePagos(inscripcion, "Pago de Inscripción");
            });
        }else {
            TarjetaTorneoResponse tarjeta = tarjetasTorneo.get(position);
            holder.textViewNombreJugadorContabilidad.setText(tarjeta.getNombreCompletoJugador());
            holder.textViewEquipoContabilidad.setText(tarjeta.getNombreEquipo());
            holder.textViewMontoContabilidad.setText(df.format(tarjeta.getValorTarjeta()));
            String tipoTarjeta = tarjeta.getTipoTarjeta();
            holder.itemView.setOnClickListener(view -> {
                ((ContabilidadActivity) context).abrirDialogDetallePagos(tarjeta, "Tarjeta "+tipoTarjeta);
            });
            switch (tipoTarjeta) {
                case "amarilla":
                    holder.viewIndicadorEstadoPago.setBackgroundColor(context.getResources().getColor(R.color.yellowCard));
                    break;
                case "azul":
                    holder.viewIndicadorEstadoPago.setBackgroundColor(context.getResources().getColor(R.color.blueCard));
                    break;
                case "roja":
                    holder.viewIndicadorEstadoPago.setBackgroundColor(context.getResources().getColor(R.color.redCard));
                    break;
                default:
                    holder.viewIndicadorEstadoPago.setBackgroundColor(context.getResources().getColor(R.color.text_secondary));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return tarjetasTorneo==null?inscripcionesTorneo.size():tarjetasTorneo.size();
    }

    public static class ViewHolderContabilidad extends RecyclerView.ViewHolder {
        // Aquí irían los elementos de la vista
        private TextView textViewNombreJugadorContabilidad, textViewEquipoContabilidad, textViewMontoContabilidad;
        private View viewIndicadorEstadoPago;

        public ViewHolderContabilidad(@NonNull View itemView) {
            super(itemView);
            // Aquí irían los elementos de la vista
            textViewNombreJugadorContabilidad = itemView.findViewById(R.id.textViewNombreJugadorContabilidad);
            textViewEquipoContabilidad = itemView.findViewById(R.id.textViewEquipoContabilidad);
            textViewMontoContabilidad = itemView.findViewById(R.id.textViewMontoContabilidad);
            viewIndicadorEstadoPago = itemView.findViewById(R.id.viewIndicadorEstadoPago);
        }
    }
}
