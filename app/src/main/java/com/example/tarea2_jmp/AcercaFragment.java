package com.example.tarea2_jmp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarea2_jmp.databinding.FragmentAcercaBinding;


public class AcercaFragment extends Fragment {

    private FragmentActivity activity;

    private FragmentAcercaBinding binding;


    public AcercaFragment() {
    }


    public static AcercaFragment newInstance() { return new AcercaFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño de fragmento Acerca usando el enlace generado
        activity = getActivity();

        return (binding = FragmentAcercaBinding.inflate(inflater, container, false)).getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ocultar elementos de la interfaz de usuario al cargar el fragmento
        activity.findViewById(R.id.bottomNavBar).setVisibility(View.GONE);
        activity.findViewById(R.id.topAppBar).setVisibility(View.GONE);

        // Configurar un listener para el botón atrás
        binding.btnAtras.setOnClickListener(view1 -> {
            // Navegar al fragmento principal al hacer clic en el botón
            NavHostFragment.findNavController(AcercaFragment.this)
                    .navigate(R.id.action_acercaFragment_to_principalFragment);
        });


    }
}