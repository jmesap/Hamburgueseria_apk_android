package com.example.tarea2_jmp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tarea2_jmp.databinding.FragmentBebidasBinding;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class BebidasFragment extends Fragment implements BebidasAdapter.OnItemSelectedListener {
    private FragmentActivity activity;
    private FragmentBebidasBinding binding;
    private BebidasAdapter adapter;
    private List<ProductoBebida> bebidas;
    Pedido pedido;

    // Constructor por defecto requerido por Fragment
    public BebidasFragment() {
    }

    // Método estático para crear una nueva instancia del fragmento
    public static BebidasFragment newInstance() {
        return new BebidasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Uso de View Binding para inflar el layout
        binding = FragmentBebidasBinding.inflate(inflater, container, false);
        // Inicializar la lista de bebidas
        bebidas = new ArrayList<>();

        // Agregar las bebidas
        bebidas.add(new ProductoBebida(R.drawable.bebida1, getString(R.string.tv_Bebida1), 2.50, 0, new Button(requireContext()), 1));
        bebidas.add(new ProductoBebida(R.drawable.bebida2, getString(R.string.tv_Bebida2), 3.00, 0, new Button(requireContext()), 2));
        bebidas.add(new ProductoBebida(R.drawable.bebida3, getString(R.string.tv_Bebida3), 2.30, 0, new Button(requireContext()), 3));
        bebidas.add(new ProductoBebida(R.drawable.bebida4, getString(R.string.tv_Bebida4), 3.20, 0, new Button(requireContext()), 4));

        // Crear el adaptador y pasarlo a la RecyclerView
        adapter = new BebidasAdapter(bebidas, this);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicializar activity
        activity = getActivity();
        // Configuración de la BottomNavigationView
        setupBottomNavigationBar();

        // Configuración de la barra de herramientas
        MaterialToolbar toolbar = activity.findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.bebidas_title));

        // Configuración de la RecyclerView
        setupRecyclerView();
    }

    // Configuración de la BottomNavigationView
    private void setupBottomNavigationBar() {
        // Obtener la BottomNavigationView desde la actividad
        BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).getBottomNavBar();
        MenuItem siguienteItem = bottomNavigationView.getMenu().findItem(R.id.siguiente);
        if (siguienteItem != null) {
            siguienteItem.setTitle(R.string.btn_finalizar);
        }
        // Manejar clic en los elementos del menú de la BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(this::onBottomNavigationItemSelected);
    }

    private void setupRecyclerView() {
        // Configuración del LayoutManager y Adapter para la RecyclerView
        // En este caso, se utiliza un GridLayoutManager con 2 columnas
        binding.rvBebidas.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvBebidas.setAdapter(adapter);
    }

    // Manejar selección de elementos en la BottomNavigationView
    private boolean onBottomNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.atras) {
            // Lógica para el botón "atrás"
            NavHostFragment.findNavController(this).navigate(R.id.action_bebidasFragment_to_hamburguesasFragment);
            return true;
        }
        if (itemId == R.id.limpiar) {
            // Método para limpiar los valores de las bebidas
            limpiarValores();
            return true;
        } else if (itemId == R.id.siguiente) {
            // Lógica para el botón "Finalizar"
            // Procesar la selección de bebidas y navegar al siguiente fragmento
            procesarSeleccionBebidas();
            NavHostFragment.findNavController(this).navigate(R.id.action_bebidasFragment_to_informeFragment);
            item.setTitle(R.string.btn_siguiente);
            Toast.makeText(activity.getApplicationContext(), getString(R.string.toast_pedido_realizado), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public void onSelect(ProductoBebida productoBebida) {
    }

    // Limpiar los valores de cantidad de bebidas
    private void limpiarValores() {
        for (int i = 0; i < bebidas.size(); i++) {
            bebidas.get(i).setCantidadBebida(0);

            adapter.notifyDataSetChanged();
        }
    }

    // Procesar la selección de bebidas y actualizar el objeto Pedido
    private void procesarSeleccionBebidas() {
        // Procesar la selección de bebidas y pasar a la siguiente pantalla
        pedido = ((MainActivity) requireActivity()).getPedido();

        // Filtrar las bebidas con cantidad mayor a 0
        List<ProductoBebida> bebidasSeleccionadas = new ArrayList<>();
        for (ProductoBebida bebida : bebidas) {
            if (bebida.getCantidadBebida() > 0) {
                bebidasSeleccionadas.add(bebida);
            }
        }

        // Agregar las bebidas seleccionadas al pedido
        pedido.setBebidasPedido(new ArrayList<>(bebidasSeleccionadas));
    }
}