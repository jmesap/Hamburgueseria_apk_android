package com.example.tarea2_jmp;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tarea2_jmp.databinding.FragmentHamburguesasBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HamburguesasFragment extends Fragment implements HamburguesasAdapter.OnItemSelectedListener {

    private FragmentActivity activity;
    private FragmentHamburguesasBinding binding;
    private HamburguesasAdapter adapter;
    private List<ProductoHamburguesa> hamburguesas;
    Pedido pedido;

    public HamburguesasFragment() {
    }

    public static HamburguesasFragment newInstance() {
        return new HamburguesasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Uso de View Binding para inflar el layout
        binding = FragmentHamburguesasBinding.inflate(inflater, container, false);

        // Inicialización de la lista de hamburguesas
        hamburguesas = new ArrayList<>();

        // Se agregan las hamburguesas
        hamburguesas.add(new ProductoHamburguesa(R.drawable.burger1, getString(R.string.tv_Burguer1), 12.00, 0, new Button(requireContext()), 1, false));
        hamburguesas.add(new ProductoHamburguesa(R.drawable.burger2, getString(R.string.tv_Burguer2), 15.00, 0, new Button(requireContext()), 2, false));
        hamburguesas.add(new ProductoHamburguesa(R.drawable.burger3, getString(R.string.tv_Burguer3), 13.50, 0, new Button(requireContext()), 3, false));

        // Crear el adaptador y pasarlo a la RecyclerView
        adapter = new HamburguesasAdapter(hamburguesas, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización de la actividad
        activity = getActivity();

        // Configuración de la BottomNavigationView
        setupBottomNavigationBar();

        // Configuración de la barra de herramientas
        MaterialToolbar toolbar = activity.findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.burgers_title));

        // Configuración de la RecyclerView
        setupRecyclerView();
    }

    private void setupBottomNavigationBar() {
        // Se obtiene la BottomNavigationView desde la actividad
        BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).getBottomNavBar();

        // Manejar clic en los elementos del menú de la BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(this::onBottomNavigationItemSelected);
    }

    private void setupRecyclerView() {
        // Configuración de la cantidad de columnas en la RecyclerView en función de la orientación
        int orientation = getResources().getConfiguration().orientation;
        int spanCount = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 1;

        // Configuración del LayoutManager y Adapter para la RecyclerView
        binding.rvHamburguesas.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        binding.rvHamburguesas.setAdapter(adapter);
    }

    private boolean onBottomNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.atras) {

            // Navegar hacia atrás
            NavHostFragment.findNavController(this).navigate(R.id.action_hamburguesasFragment_to_registroFragment);
            return true;
        }
        if (itemId == R.id.limpiar) {

            // Limpiar valores en la lista de hamburguesas
            limpiarValores();
            return true;
        } else if (itemId == R.id.siguiente) {

            // Procesar la selección de hamburguesas y navegar al siguiente fragmento
            procesarSeleccionHamburguesas();
            if (pedido.getHamburguesasPedido().isEmpty()) {
                showErrorDialog(R.string.avisoEligeBurger, R.string.avisoEligeBurgerTitulo);
                return false;
            } else {
                NavHostFragment.findNavController(this).navigate(R.id.action_hamburguesasFragment_to_bebidasFragment);
                return true;
            }
        }
        return false;
    }


    private void limpiarValores() {
        for (int i = 0; i < hamburguesas.size(); i++) {
            hamburguesas.get(i).setCantidadHamburguesa(0);

            // Acceder al CheckBox usando View Binding
            View itemView = binding.rvHamburguesas.getChildAt(i);
            if (itemView != null) {
                CheckBox checkBox = itemView.findViewById(R.id.chkVegano);
                if (checkBox != null) {
                    // Deseleccionar el CheckBox
                    checkBox.setChecked(false);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    private void procesarSeleccionHamburguesas() {
        // Procesar la selección de hamburguesas y pasar a la siguiente pantalla
        pedido = ((MainActivity) requireActivity()).getPedido();

        // Filtrar las hamburguesas con cantidad mayor a 0
        List<ProductoHamburguesa> hamburguesasSeleccionadas = new ArrayList<>();
        for (ProductoHamburguesa hamburguesa : hamburguesas) {
            if (hamburguesa.getCantidadHamburguesa() > 0) {
                // Se añade "VEGAN" a las hamburguesas que se hayan seleccionado como veganas
                if (hamburguesa.isVegano()) {
                    hamburguesa.setNombreHamburguesa("VEGAN " + hamburguesa.getNombreHamburguesa());
                }
                hamburguesasSeleccionadas.add(hamburguesa);
            }
        }

        // Agregar las hamburguesas seleccionadas al pedido
        pedido.setHamburguesasPedido(new ArrayList<>(hamburguesasSeleccionadas));
    }

    private void showErrorDialog(int messageResId, int titleResId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        alertDialogBuilder.setMessage(getString(messageResId))
                .setTitle(getString(titleResId))
                .setPositiveButton(getString(R.string.avisoPositiveButton), (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onSelect(ProductoHamburguesa productoHamburguesa) {
    }
}
