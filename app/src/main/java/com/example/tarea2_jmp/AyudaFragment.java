package com.example.tarea2_jmp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarea2_jmp.databinding.FragmentAyudaBinding;
import com.google.android.material.appbar.MaterialToolbar;


public class AyudaFragment extends Fragment {

    private FragmentActivity activity;
    private FragmentAyudaBinding binding;


    public AyudaFragment() {
        // Constructor vacío
    }


    public static AyudaFragment newInstance() {
        return new AyudaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño de fragmento Ayuda usando el enlace generado
        activity = getActivity();
        return (binding = FragmentAyudaBinding.inflate(inflater, container, false)).getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ocultar la barra de navegación inferior y mostrar la barra de la parte superior
        activity.findViewById(R.id.bottomNavBar).setVisibility(View.GONE);
        activity.findViewById(R.id.topAppBar).setVisibility(View.VISIBLE);


        // Configuración de la barra de herramientas
        MaterialToolbar toolbar = activity.findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.tv_etiAyuda));
        toolbar.getMenu().clear();

        // Configurar un listener para el botón atrás
        binding.btnAtras.setOnClickListener(view1 -> {
            // Navegar al fragmento principal al hacer clic en el botón
            NavHostFragment.findNavController(AyudaFragment.this)
                    .navigate(R.id.action_ayudaFragment_to_principalFragment);
        });


    }
}