package com.example.tarea2_jmp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tarea2_jmp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    private static final int MENU_ACERCA_DE = R.id.acerca_de;
    private static final int MENU_AYUDA = R.id.ayuda;
    ActivityMainBinding binding;
    private NavController navController;
    private List<ProductoHamburguesa> hamburguesasList;
    private List<ProductoBebida> bebidasList;
    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleConfig.setLocaleFromPrefs(this);

        // Inflar el layout utilizando View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar la barra de herramientas
        setSupportActionBar(binding.topAppBar);

        // Obtener el controlador de navegación desde el fragmento de navegación
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment)).getNavController();

        // Configurar la barra de herramientas para la navegación
        NavigationUI.setupWithNavController(binding.topAppBar, navController);

        // Inicializar listas y objeto Pedido
        hamburguesasList = new ArrayList<>();
        bebidasList = new ArrayList<>();
        pedido = new Pedido();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // Obtener la vista de la barra de navegación inferior
    public BottomNavigationView getBottomNavBar() {
        return binding.bottomNavBar;
    }

    // Obtener la lista de hamburguesas (finalmente no se ha usado)
    public List<ProductoHamburguesa> getHamburguesasList() {
        return hamburguesasList;
    }

    // Obtener el objeto Pedido
    public Pedido getPedido() {
        return pedido;
    }

    // Obtener la lista de bebidas (finalmente no se ha usado)
    public List<ProductoBebida> getBebidasList() {
        return bebidasList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        // Manejar eventos de los elementos del menú
        if (itemId == MENU_ACERCA_DE) {
            navController.navigate(R.id.action_principalFragment_to_acercaFragment);
            return true;
        }
        if (itemId == MENU_AYUDA) {

            navController.navigate(R.id.action_principalFragment_to_ayudaFragment);
            return true;
        }
        return false;
    }


}

