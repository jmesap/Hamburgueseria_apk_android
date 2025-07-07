package com.example.tarea2_jmp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tarea2_jmp.databinding.FragmentInformeBinding;
import com.google.android.material.appbar.MaterialToolbar;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class InformeFragment extends Fragment {
    private FragmentActivity activity;
    private FragmentInformeBinding binding;

    private Pedido pedido;
    private TextView subtotal;
    String nombre;
    String telefono;
    Double envio = 0.00;

    public InformeFragment() {
    }


    public static InformeFragment newInstance() {
        return new InformeFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle
                                 savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedido = ((MainActivity) requireActivity()).getPedido();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        return (binding = FragmentInformeBinding.inflate(inflater, container, false)).getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.findViewById(R.id.bottomNavBar).setVisibility(View.GONE);

        // Configuración de la barra de herramientas
        MaterialToolbar toolbar = activity.findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.informe_title));

        // Configura la tabla con los elementos de hamburguesas
        setupTable();

        binding.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InformeFragment.this)
                        .navigate(R.id.action_informeFragment_to_principalFragment);
            }

        });
    }

    private void setupTable() {
        TableLayout tableLayout = binding.tableLayout;

        // Obtener los datos del pedido desde MainActivity
        Pedido pedido = ((MainActivity) requireActivity()).getPedido();

        String nombre = pedido.getNombre();
        String telefono = pedido.getTelefono();
        String recogida = pedido.getRecogida();
        binding.tvNombreRegistro.setText(nombre);
        binding.tvTelefonoRegistro.setText(telefono);
        binding.tvEntrega.setText(recogida);


        // Encuentra las vistas de las filas de hamburguesas, bebidas y entrega
        TableRow rowHamburguesas = (TableRow) tableLayout.findViewById(R.id.rowHamburguesas);
        TableRow rowBebidas = (TableRow) tableLayout.findViewById(R.id.rowBebidas);

        if (!pedido.getDireccion().isEmpty()) {
            binding.rowDomicilio.setVisibility(getView().VISIBLE);
            binding.tvDomicilio.setText(pedido.getDireccion());

            envio = 2.99;
        }


        // Agrega las hamburguesas
        for (int i = 0; i < pedido.getHamburguesasPedido().size(); i++) {
            ProductoHamburguesa hamburguesa = pedido.getHamburguesasPedido().get(i);
            if (i == 0) {
                // Si es la primera hamburguesa con cantidad mayor a 0, reemplaza la rowHamburguesas existente
                agregarHamburguesaARow(hamburguesa, rowHamburguesas);
            } else {
                // Si no es la primera hamburguesa, crea una nueva row y agrégala justo debajo de rowHamburguesas
                TableRow newRow = createNewRow();
                agregarHamburguesaARow(hamburguesa, newRow);
                tableLayout.addView(newRow, tableLayout.indexOfChild(rowHamburguesas) + i);
            }
        }

        // Agrega las bebidas
        for (int i = 0; i < pedido.getBebidasPedido().size(); i++) {
            ProductoBebida bebida = pedido.getBebidasPedido().get(i);

            if (i == 0) {
                // Si es la primera bebida con cantidad mayor a 0, reemplaza la rowBebidas existente
                agregarBebidaARow(bebida, rowBebidas);
            } else {
                // Si no es la primera bebida, crea una nueva row y agrégala justo debajo de rowBebidas
                TableRow newRow = createNewRow();
                agregarBebidaARow(bebida, newRow);
                tableLayout.addView(newRow, tableLayout.indexOfChild(rowBebidas) + i);
            }

        }
        // Fecha y hora
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = formatoFecha.format(new Date());
        binding.tvFecha.setText(fecha);

        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String hora = formatoHora.format(new Date());
        binding.tvHora.setText(hora);

        // Calcular el total de hamburguesas
        double totalHamburguesas = 0.0;
        for (ProductoHamburguesa hamburguesa : pedido.getHamburguesasPedido()) {
            totalHamburguesas += hamburguesa.getCantidadHamburguesa() * hamburguesa.getPrecioHamburguesa();
        }

        double totalBebidas = 0.0;
        for (ProductoBebida bebida : pedido.getBebidasPedido()) {
            totalBebidas += bebida.getCantidadBebida() * bebida.getPrecioBebida();
        }
        double neto = totalBebidas + totalHamburguesas;
        double iva = neto * 0.07;
        double importeTotal = neto + iva + envio;

        binding.tvEnvio.setText(String.format("%.2f €", envio));
        binding.tvIVA.setText(String.format("%.2f €", iva));
        binding.tvTotal.setText(String.format("%.2f €", importeTotal));

    }


    // Función para crear una nueva instancia de TableRow
    private TableRow createNewRow() {
        return new TableRow(requireContext());
    }


    private void agregarBebidaARow(ProductoBebida bebida, TableRow row) {

        TextView tvCantidad = new TextView(requireContext());
        tvCantidad.setText(bebida.getCantidadBebida() + "x");
        row.addView(tvCantidad, 0); // Agrega en la primera posición

        // Configura la celda para el nombre de la hamburguesa
        TextView tvNombre = new TextView(requireContext());
        tvNombre.setText(bebida.getNombreBebida());
        row.addView(tvNombre, 1); // Agrega en la primera posición

        // Configura la celda para el precio unitario
        TextView tvPrecioUnitario = new TextView(requireContext());
        tvPrecioUnitario.setText(String.format("%.2f €", bebida.getPrecioBebida()));
        tvPrecioUnitario.setGravity(Gravity.END); // Alinea a la derecha
        row.addView(tvPrecioUnitario, 2); // Agrega en la segunda posición

        // Configura la celda para el subtotal
        TextView tvSubtotal = new TextView(requireContext());
        double subtotal = bebida.getCantidadBebida() * bebida.getPrecioBebida();
        tvSubtotal.setText(String.format("%.2f €", subtotal));
        tvSubtotal.setGravity(Gravity.END); // Alinea a la derecha
        row.addView(tvSubtotal, 3); // Agrega en la tercera posición
    }

    private void agregarHamburguesaARow(ProductoHamburguesa hamburguesa, TableRow row) {
        // Configura la celda para el nombre de la hamburguesa
        TextView tvCantidad = new TextView(requireContext());
        tvCantidad.setText(hamburguesa.getCantidadHamburguesa() + "x");
        row.addView(tvCantidad, 0); // Agrega en la primera posición

        TextView tvNombre = new TextView(requireContext());
        tvNombre.setText(hamburguesa.getNombreHamburguesa());
        row.addView(tvNombre, 1); // Agrega en la primera posición

        // Configura la celda para el precio unitario
        TextView tvPrecioUnitario = new TextView(requireContext());
        tvPrecioUnitario.setText(String.format("%.2f €", hamburguesa.getPrecioHamburguesa()));
        tvPrecioUnitario.setGravity(Gravity.END); // Alinea a la derecha
        row.addView(tvPrecioUnitario, 2); // Agrega en la segunda posición

        // Configura la celda para el subtotal
        TextView tvSubtotal = new TextView(requireContext());
        double subtotal = hamburguesa.getCantidadHamburguesa() * hamburguesa.getPrecioHamburguesa();
        tvSubtotal.setText(String.format("%.2f €", subtotal));
        tvSubtotal.setGravity(Gravity.END); // Alinea a la derecha
        row.addView(tvSubtotal, 3); // Agrega en la tercera posición
    }

}

