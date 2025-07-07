package com.example.tarea2_jmp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2_jmp.databinding.TarjetaBinding;

import java.util.List;

public class BebidasAdapter extends RecyclerView.Adapter<BebidasAdapter.ViewHolder> {
    private final List<ProductoBebida> bebidas;
    private final BebidasAdapter.OnItemSelectedListener listener;

    // Constructor del adaptador
    BebidasAdapter(List<ProductoBebida> bebidas, BebidasAdapter.OnItemSelectedListener listener) {
        this.bebidas = bebidas;
        this.listener = listener;
    }

    // Interfaz para manejar la selección de bebidas
    public interface OnItemSelectedListener {
        void onSelect(ProductoBebida productoBebida);
    }

    @NonNull
    @Override
    public BebidasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista de la tarjeta utilizando el enlace generado
        return new BebidasAdapter.ViewHolder(
                TarjetaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BebidasAdapter.ViewHolder holder, int position) {
        // Obtener el objeto ProductoBebida en la posición actual
        ProductoBebida bebida = bebidas.get(position);

        // Configurar la vista de la tarjeta con los datos de la bebida actual
        holder.binding.ivFotoProducto.setImageResource(bebida.getFotoBebida());
        holder.binding.tvNombreProducto.setText(bebida.getNombreBebida());
        holder.binding.tvPrecioProducto.setText(bebida.getPrecioBebida() + " €");
        holder.binding.contador.setText(String.valueOf(bebida.getCantidadBebida()));

        // Configurar el listener para el botón de incrementar
        holder.binding.btnIncrementar.setOnClickListener(view -> {
            // Incrementar el contador y actualizar la vista
            bebida.setCantidadBebida(bebida.getCantidadBebida() + 1);
            holder.binding.contador.setText(String.valueOf(bebida.getCantidadBebida()));

            // Notificar al listener sobre el cambio
            listener.onSelect(bebida);
        });


    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int getItemCount() {
        // Devolver la cantidad de elementos en la lista de bebidas
        return bebidas.size();
    }

    // Clase interna ViewHolder que representa cada elemento de la lista
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TarjetaBinding binding;

        // Constructor de ViewHolder
        public ViewHolder(TarjetaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Configurar el listener para el clic en la tarjeta
            binding.btnIncrementar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // Obtener la posición de la bebida seleccionada y notificar al listener
            int posicion = getAdapterPosition();
            listener.onSelect((bebidas.get(posicion)));
        }

    }

}
