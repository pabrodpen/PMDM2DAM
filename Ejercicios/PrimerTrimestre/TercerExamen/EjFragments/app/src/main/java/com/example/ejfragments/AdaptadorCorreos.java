package com.example.ejfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ejfragments.Correo;
import com.example.ejfragments.R;

import java.util.List;

public class AdaptadorCorreos extends RecyclerView.Adapter<AdaptadorCorreos.CorreosViewHolder> {

    private List<Correo> datos;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Correo correo);
    }

    public AdaptadorCorreos(List<Correo> datos, OnItemClickListener listener) {
        this.datos = datos;
        this.clickListener = listener;
    }

    public class CorreosViewHolder extends RecyclerView.ViewHolder {
        TextView lblDe;
        TextView lblAsunto;

        public CorreosViewHolder(View itemView) {
            super(itemView);
            lblDe = itemView.findViewById(R.id.lblDe);
            lblAsunto = itemView.findViewById(R.id.lblAsunto);
        }

        public void bindCorreo(Correo correo) {
            lblDe.setText(correo.getDe());
            lblAsunto.setText(correo.getAsunto());
        }
    }


    public CorreosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new CorreosViewHolder(item);
    }


    public void onBindViewHolder(CorreosViewHolder holder, int position) {
        final Correo correo = datos.get(position);
        holder.bindCorreo(correo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(correo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}
