package com.example.glypha_primer_parcial_giardina_saracco.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glypha_primer_parcial_giardina_saracco.data.model.Fuente;
import com.example.glypha_primer_parcial_giardina_saracco.R;

import java.util.List;

public class FuentesAdapter extends RecyclerView.Adapter<FuentesAdapter.FuenteViewHolder> {

    private List<Fuente> listaFuentes;
    private OnFavoriteClickListener favoriteClickListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Fuente fuente);
    }

    public FuentesAdapter(List<Fuente> listaFuentes, OnFavoriteClickListener listener) {
        this.listaFuentes = listaFuentes;
        this.favoriteClickListener = listener;
    }

    @NonNull
    @Override
    public FuenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fuente, parent, false);
        return new FuenteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FuenteViewHolder holder, int position) {
        Fuente fuente = listaFuentes.get(position);
        holder.tvNombreFuente.setText(fuente.getNombre());

        if (favoriteClickListener != null) {
            holder.btnFavorito.setVisibility(View.VISIBLE);
            holder.btnFavorito.setOnClickListener(v -> {
                favoriteClickListener.onFavoriteClick(fuente);
            });
        } else {
            holder.btnFavorito.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listaFuentes.size();
    }

    // Método para actualizar la lista filtrada
    public void filterList(List<Fuente> filteredList) {
        listaFuentes = filteredList;
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos cambiaron
    }

    public static class FuenteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreFuente;
        ImageButton btnFavorito;

        public FuenteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreFuente = itemView.findViewById(R.id.tv_nombre_fuente);
            btnFavorito = itemView.findViewById(R.id.btn_favorito);
        }
    }
}