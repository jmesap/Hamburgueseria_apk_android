package com.example.tarea2_jmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tarea2_jmp.databinding.FragmentPrincipalBinding;
import com.google.android.material.appbar.MaterialToolbar;

import org.jetbrains.annotations.NotNull;


public class PrincipalFragment extends Fragment {
    private FragmentActivity activity;
    private FragmentPrincipalBinding binding;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle
                                 savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Obtener la actividad asociada al fragmento
        activity = getActivity();
        return (binding = FragmentPrincipalBinding.inflate(inflater, container, false)).getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración de visibilidad de la barra de navegación inferior y superior
        activity.findViewById(R.id.bottomNavBar).setVisibility(View.GONE);
        activity.findViewById(R.id.topAppBar).setVisibility(View.VISIBLE);

        // Configuración de la barra de herramientas
        MaterialToolbar toolbar = activity.findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.toolbar_menu);

        // Manejar clic en el botón de pedido
        binding.btnPedido.setOnClickListener(view1 -> NavHostFragment.findNavController(PrincipalFragment.this)
                .navigate(R.id.action_principalFragment_to_registroFragment));
    }


}
