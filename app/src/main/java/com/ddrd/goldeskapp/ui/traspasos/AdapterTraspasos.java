package com.ddrd.goldeskapp.ui.traspasos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoResponse;
import com.ddrd.goldeskapp.data.model.traspasos.TraspasoUpdate;
import com.ddrd.goldeskapp.ui.utilities.dialogs.DialogsResponse;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraUser;

import java.util.List;

public class AdapterTraspasos extends RecyclerView.Adapter<AdapterTraspasos.viewHolder> {

    private final Context context;
    private final List<TraspasoResponse> listaTraspasos;
    private final FormatearFechaHoraUser formatearFechaHoraUser = new FormatearFechaHoraUser();

    public AdapterTraspasos(Context context, List<TraspasoResponse> listaTraspasos) {
        this.context = context;
        this.listaTraspasos = listaTraspasos;

    }

    @NonNull
    @Override
    public AdapterTraspasos.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_traspaso, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTraspasos.viewHolder holder, int position) {
        TraspasoResponse traspaso = listaTraspasos.get(position);
        holder.txtNombreJugador.setText(traspaso.getNombreJugador());
        holder.txtCedulaJugador.setText(traspaso.getCedulaJugador());
        holder.txtEquipoOrigen.setText(traspaso.getEquipoOrigen());
        holder.txtEquipoDestino.setText(traspaso.getEquipoDestino());
        holder.txtAsunto.setText(traspaso.getAsunto());
        holder.txtEstado.setText(traspaso.getEstado());
        String fechaSolicitud = formatearFechaHoraUser.formatearFechaDesdeJSON(traspaso.getFechaSolicitud());
        holder.txtFechaSolicitud.setText(fechaSolicitud);
        String fechaRespuesta = formatearFechaHoraUser.formatearFechaDesdeJSON(traspaso.getFechaRespuesta());
        holder.txtFechaRespuesta.setText(fechaRespuesta);
        if (traspaso.getEstado().equals("PENDIENTE")) {
            holder.btnRechazar.setVisibility(View.VISIBLE);
            holder.btnAprobar.setVisibility(View.VISIBLE);
        } else{
            holder.btnRechazar.setVisibility(View.GONE);
            holder.btnAprobar.setVisibility(View.GONE);
        }
        holder.btnRechazar.setOnClickListener(v -> {
            TraspasoUpdate traspasoUpdate = new TraspasoUpdate();
            traspasoUpdate.setIdTraspaso(traspaso.getIdTraspaso());
            ((TraspasosActivity) context).mostrarVentanaRechazo(traspasoUpdate);
        });
        holder.btnAprobar.setOnClickListener(v -> {
            DialogsResponse dialogsResponse = new DialogsResponse(context);
            dialogsResponse.mostrarVentanaComprobacion(
                    "Aprobar Solicitud",
                    "¿Está seguro que desea aprobar esta solicitud?",
                    "Aprobar",
                    () -> { // Sin parámetros, porque así es tu interfaz
                        if (context instanceof TraspasosActivity) {
                            ((TraspasosActivity) context).aprobarSolicitud(traspaso.getIdTraspaso());
                        }
                    }
            );
        });
    }

    @Override
    public int getItemCount() {
        return listaTraspasos.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreJugador, txtCedulaJugador, txtEquipoOrigen,
                txtEquipoDestino, txtAsunto, txtEstado, txtFechaSolicitud, txtFechaRespuesta;
        Button btnRechazar, btnAprobar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreJugador = itemView.findViewById(R.id.textViewNombreJugador);
            txtCedulaJugador = itemView.findViewById(R.id.textViewCedulaJugador);
            txtEquipoOrigen = itemView.findViewById(R.id.textViewEquipoOrigen);
            txtEquipoDestino = itemView.findViewById(R.id.textViewEquipoDestino);
            txtAsunto = itemView.findViewById(R.id.textViewAsunto);
            txtEstado = itemView.findViewById(R.id.textViewEstado);
            txtFechaSolicitud = itemView.findViewById(R.id.textViewFechaSolicitud);
            txtFechaRespuesta = itemView.findViewById(R.id.textViewFechaRespuesta);
            btnRechazar = itemView.findViewById(R.id.btnRechazar);
            btnAprobar = itemView.findViewById(R.id.btnAprobar);
        }
    }
}
