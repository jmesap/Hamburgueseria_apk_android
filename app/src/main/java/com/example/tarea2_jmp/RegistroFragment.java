package com.example.tarea2_jmp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tarea2_jmp.databinding.FragmentRegistroBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class RegistroFragment extends Fragment {

    // Constantes para los ítems del menú de la BottomNavigationView
    private static final int MENU_ATRAS = R.id.atras;
    private static final int MENU_LIMPIAR = R.id.limpiar;
    private static final int MENU_SIGUIENTE = R.id.siguiente;
    private FragmentActivity activity;
    private FragmentRegistroBinding binding;

    public RegistroFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento y obtener el binding
        activity = getActivity();
        return (binding = FragmentRegistroBinding.inflate(inflater, container, false)).getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración de la interfaz de usuario
        setupUI();

        // Configuración del listener para el Spinner de recogida
        binding.spnRecogida.setOnItemClickListener((parent, v, position, id) -> {
            String selectedOption = Objects.requireNonNull(parent.getItemAtPosition(position)).toString();
            showHideDireccionField(selectedOption);
        });
    }

    // Método para configurar la interfaz de usuario
    private void setupUI() {
        // Mostrar la BottomNavigationView y configurar la Toolbar
        activity.findViewById(R.id.bottomNavBar).setVisibility(View.VISIBLE);
        MaterialToolbar toolbar = activity.findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.registro_title);
        toolbar.getMenu().clear();

        // Obtener la BottomNavigationView desde la actividad
        BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).getBottomNavBar();

        // Configurar el listener para los elementos del menú
        bottomNavigationView.setOnItemSelectedListener(this::onBottomNavigationItemSelected);
    }

    // Método llamado al seleccionar un ítem del menú de la BottomNavigationView
    private boolean onBottomNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == MENU_ATRAS) {

            // Navegar hacia atrás
            NavHostFragment.findNavController(this).navigate(R.id.action_registroFragment_to_principalFragment);
            return true;
        }
        if (itemId == MENU_LIMPIAR) {

            // Limpiar el formulario
            clearForm();
            return true;
        } else if (itemId == MENU_SIGUIENTE) {

            // Validar los campos y navegar al siguiente fragmento si es válido
            if (validateFields()) {
                NavHostFragment.findNavController(this).navigate(R.id.action_registroFragment_to_hamburguesasFragment);
                return true;
            } else {
                // No navegar si la validación falla
                return false;
            }
        }

        return false;
    }

    // Método para limpiar el formulario
    private void clearForm() {
        binding.tvEtiNombre.getEditText().setText("");
        binding.tvEtiTelefono.getEditText().setText("");
        binding.spnRecogida.setText("");
        binding.tvDireccion.getEditText().setText("");
    }

    // Método para validar los campos del formulario
    private boolean validateFields() {

        if (binding.tvEtiNombre.getEditText().getText().toString().isEmpty()) {
            showErrorDialog(R.string.avisoNombre, R.string.avisoNombreTitulo);
            return false;
        }

        if (binding.tvEtiTelefono.getEditText().getText().toString().isEmpty() || binding.tvEtiTelefono.getEditText().length() != 9) {
            showErrorDialog(R.string.avisoTelefono, R.string.avisoTelefonoTitulo);
            return false;
        }

        String selectedOption = binding.spnRecogida.getText().toString();
        String[] opcionesRecogida = getResources().getStringArray(R.array.opciones_recogida);

        if (selectedOption.equals(opcionesRecogida[0]) && binding.tvDireccion.getEditText().getText().toString().isEmpty()) {
            showErrorDialog(R.string.avisoDireccion, R.string.avisoDireccionTitulo);
            return false;
        } else if (selectedOption.isEmpty()) {
            showErrorDialog(R.string.avisoRecogida, R.string.avisoDireccionTitulo);
            return false;
        }

        // Guardar los datos en el objeto Pedido
        saveDataToPedido();

        return true;
    }

    // Método para guardar datos en el objeto Pedido
    private void saveDataToPedido() {
        Pedido pedido = ((MainActivity) requireActivity()).getPedido();
        pedido.setNombre(binding.tvEtiNombre.getEditText().getText().toString().trim());
        pedido.setTelefono(binding.tvEtiTelefono.getEditText().getText().toString().trim());
        pedido.setRecogida(binding.spnRecogida.getText().toString());
        pedido.setDireccion(binding.tvDireccion.getEditText().getText().toString().trim());
    }

    // Método para mostrar un cuadro de diálogo de error
    private void showErrorDialog(int messageResId, int titleResId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        alertDialogBuilder.setMessage(getString(messageResId))
                .setTitle(getString(titleResId))
                .setPositiveButton(getString(R.string.avisoPositiveButton), (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Método para mostrar u ocultar el campo de dirección según la opción de recogida
    private void showHideDireccionField(String selectedOption) {
        // Obtener el valor de strings.xml para "Entrega a domicilio"
        String[] opcionesRecogida = getResources().getStringArray(R.array.opciones_recogida);

        // Mostrar u ocultar el campo de dirección según la opción de recogida
        if (selectedOption.equals(opcionesRecogida[0])) {
            binding.tvDireccion.setVisibility(View.VISIBLE);
        } else {
            binding.tvDireccion.setVisibility(View.INVISIBLE);
            binding.tvDireccion.getEditText().setText(""); // Limpiar el campo de dirección si no es necesario
        }
    }


}