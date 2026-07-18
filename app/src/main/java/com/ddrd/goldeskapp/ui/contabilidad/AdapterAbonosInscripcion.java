package com.ddrd.goldeskapp.ui.contabilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddrd.goldeskapp.R;
import com.ddrd.goldeskapp.data.model.Inscripcion.AbonoDetalleInscripcion;
import com.ddrd.goldeskapp.ui.utilities.formatos.FormatearFechaHoraUser;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterAbonosInscripcion extends RecyclerView.Adapter<AdapterAbonosInscripcion.viewHolderAbonosInscripcion> {
    private Context context;
    private List<AbonoDetalleInscripcion> listaAbonos;
    private FormatearFechaHoraUser formatearFechaHoraUser = new FormatearFechaHoraUser();

    public AdapterAbonosInscripcion(Context context, List<AbonoDetalleInscripcion> listaAbonos) {
        this.context = context;
        this.listaAbonos = listaAbonos;
    }

    @NonNull
    @Override
    public AdapterAbonosInscripcion.viewHolderAbonosInscripcion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_abonos_inscripcion, parent, false);
        return new viewHolderAbonosInscripcion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAbonosInscripcion.viewHolderAbonosInscripcion holder, int position) {
        AbonoDetalleInscripcion abono = listaAbonos.get(position);
        DecimalFormat df = new DecimalFormat("$ #,###");
        holder.textViewFechaAbono.setText(formatearFechaHoraUser.formatearFechaUser(abono.getFecha()));
        holder.textViewMontoAbono.setText(df.format(abono.getMonto()));
    }

    @Override
    public int getItemCount() {
        return listaAbonos.size();
    }

    public static class viewHolderAbonosInscripcion extends RecyclerView.ViewHolder {
        private TextView textViewFechaAbono, textViewMontoAbono;
        public viewHolderAbonosInscripcion(@NonNull View itemView) {
            super(itemView);
            textViewFechaAbono = itemView.findViewById(R.id.tvFechaAbono);
            textViewMontoAbono = itemView.findViewById(R.id.tvMontoAbono);
        }
    }
}
