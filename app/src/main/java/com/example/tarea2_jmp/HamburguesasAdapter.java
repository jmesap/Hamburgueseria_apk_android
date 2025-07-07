package com.example.tarea2_jmp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea2_jmp.databinding.TarjetaBinding;

import java.util.List;

public class HamburguesasAdapter extends RecyclerView.Adapter<HamburguesasAdapter.ViewHolder> {

    private final List<ProductoHamburguesa> hamburguesas;
    private final OnItemSelectedListener listener;

    HamburguesasAdapter(List<ProductoHamburguesa> hamburguesas, OnItemSelectedListener listener) {
        this.hamburguesas = hamburguesas;
        this.listener = listener;
    }

    public interface OnItemSelectedListener {
        void onSelect(ProductoHamburguesa productoHamburguesa);
    }

    @NonNull
    @Override
    public HamburguesasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista de la tarjeta utilizando el enlace generado
        return new HamburguesasAdapter.ViewHolder(
                TarjetaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HamburguesasAdapter.ViewHolder holder, int position) {
        // Obtener el objeto ProductoHamburguesa en la posición actual
        ProductoHamburguesa hamburguesa = hamburguesas.get(position);

        // Configurar la vista de la tarjeta con los datos de la hamburguesa
        holder.binding.ivFotoProducto.setImageResource(hamburguesa.getFotoHamburguesa());
        holder.binding.tvNombreProducto.setText(hamburguesa.getNombreHamburguesa());
        holder.binding.tvPrecioProducto.setText(hamburguesa.getPrecioHamburguesa() + " €");
        holder.binding.contador.setText(String.valueOf(hamburguesa.getCantidadHamburguesa()));
        holder.binding.btnIncrementar.setOnClickListener(view -> {

            // Incrementar el contador y actualizar la vista
            hamburguesa.setCantidadHamburguesa(hamburguesa.getCantidadHamburguesa() + 1);
            holder.binding.contador.setText(String.valueOf(hamburguesa.getCantidadHamburguesa()));

            // Notificar al listener sobre el cambio
            listener.onSelect(hamburguesa);


        });

        // Obtener el contexto de la vista asociada al CheckBox
        Context context = holder.itemView.getContext();

        // Configurar el listener para el CheckBox Vegano
        holder.binding.chkVegano.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Mostrar cuadro de diálogo
                showConfirmationDialog(context, hamburguesas.get(position), holder.binding.chkVegano);
            }
        });

    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        // Devolver la cantidad de elementos en la lista de hamburguesas
        return hamburguesas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TarjetaBinding binding;

        // Constructor de ViewHolder
        public ViewHolder(TarjetaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            CheckBox chkVegano = itemView.findViewById(R.id.chkVegano);
            chkVegano.setVisibility(View.VISIBLE);

            // Configurar el listener para el clic en la tarjeta
            binding.btnIncrementar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Obtener la posición de la hamburguesa y notificar al listener
            int posicion = getAdapterPosition();
            listener.onSelect((hamburguesas.get(posicion)));
        }

    }

    // Método para mostrar el cuadro de diálogo de confirmación para la hamburguesa vegana
    private void showConfirmationDialog(Context context, ProductoHamburguesa hamburguesa, CheckBox chkVegano) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.tituloDialogoVegana)
                .setMessage(R.string.avisoHamburguesaSeleccionada)
                .setPositiveButton(R.string.botonSI, (dialog, which) -> {
                    // Acción si el usuario hace clic en "Sí"
                    Toast.makeText(context, R.string.toast_hamburguesa_vegana_seleccionada, Toast.LENGTH_SHORT).show();
                    hamburguesa.setVegano(true);
                })
                .setNegativeButton(R.string.botonNo, (dialog, which) -> {
                    // Acción si el usuario hace clic en "No"
                    Toast.makeText(context, R.string.toast_operacion_cancelada, Toast.LENGTH_SHORT).show();
                    chkVegano.setChecked(false);
                    hamburguesa.setVegano(false);
                })
                .show();
    }
}
