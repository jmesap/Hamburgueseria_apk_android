package com.example.tarea2_jmp;

import android.widget.Button;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Clase que representa el pedido del usuario
class Pedido implements Serializable {
    private String nombre;
    private String telefono;
    private String recogida;
    private String direccion;
    private ArrayList<ProductoHamburguesa> hamburguesasPedido;
    private ArrayList<ProductoBebida> bebidasPedido;

    // Constructor con parámetros
    public Pedido(String nombre, String telefono, String recogida, String direccion, ArrayList<ProductoHamburguesa> hamburguesasPedido) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.recogida = recogida;
        this.direccion = direccion;
        this.hamburguesasPedido = hamburguesasPedido;
        this.bebidasPedido = new ArrayList<>();

    }

    // Constructor vacío
    public Pedido() {

    }

    // Métodos getter y setter para los atributos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRecogida() {
        return recogida;
    }

    public void setRecogida(String recogida) {
        this.recogida = recogida;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public ArrayList<ProductoHamburguesa> getHamburguesasPedido() {
        return hamburguesasPedido;
    }

    public void setHamburguesasPedido(ArrayList<ProductoHamburguesa> hamburguesasPedido) {
        this.hamburguesasPedido = hamburguesasPedido;
    }

    public ArrayList<ProductoBebida> getBebidasPedido() {
        return bebidasPedido;
    }

    public void setBebidasPedido(ArrayList<ProductoBebida> bebidasPedido) {
        this.bebidasPedido = bebidasPedido;
    }
}

// Clase que representa un producto de hamburguesa
class ProductoHamburguesa {

    private int fotoHamburguesa;
    private String nombreHamburguesa;
    private Double precioHamburguesa;
    private int cantidadHamburguesa;
    private Button anadirHamburguesa;
    private int idHamburguesa;
    private boolean vegano;

    // Constructor con parámetros
    public ProductoHamburguesa(int fotoHamburguesa, String nombreHamburguesa, Double precioHamburguesa, int cantidadHamburguesa, Button anadirHamburguesa, int idHamburguesa, boolean vegano) {
        this.fotoHamburguesa = fotoHamburguesa;
        this.nombreHamburguesa = nombreHamburguesa;
        this.precioHamburguesa = precioHamburguesa;
        this.cantidadHamburguesa = cantidadHamburguesa;
        this.anadirHamburguesa = anadirHamburguesa;
        this.idHamburguesa = idHamburguesa;
        this.vegano = false; // Por defecto, no es vegano
    }

    // Métodos getter y setter para los atributos
    public int getFotoHamburguesa() {
        return fotoHamburguesa;
    }

    public void setFotoHamburguesa(int fotoHamburguesa) {
        this.fotoHamburguesa = fotoHamburguesa;
    }

    public String getNombreHamburguesa() {
        return nombreHamburguesa;
    }

    public void setNombreHamburguesa(String nombreHamburguesa) {
        this.nombreHamburguesa = nombreHamburguesa;
    }

    public Double getPrecioHamburguesa() {
        return precioHamburguesa;
    }

    public void setPrecioHamburguesa(Double precioHamburguesa) {
        this.precioHamburguesa = precioHamburguesa;
    }

    public int getCantidadHamburguesa() {
        return cantidadHamburguesa;
    }

    public void setCantidadHamburguesa(int cantidadHamburguesa) {
        this.cantidadHamburguesa = cantidadHamburguesa;
    }

    public Button getAnadirHamburguesa() {
        return anadirHamburguesa;
    }

    public void setAnadirHamburguesa(Button anadirHamburguesa) {
        this.anadirHamburguesa = anadirHamburguesa;
    }

    public int getIdHamburguesa() {
        return idHamburguesa;
    }

    public void setIdHamburguesa(int idHamburguesa) {
        this.idHamburguesa = idHamburguesa;
    }

    public boolean isVegano() {
        return vegano;
    }

    // Setter para vegano
    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }

    // Método toString para representar el objeto como cadena de texto (al final no ha sido usado)
    @NonNull
    @Override
    public String toString() {
        return this.getCantidadHamburguesa() + "x " + this.getNombreHamburguesa();
    }
}

// Clase que representa un producto de bebida
class ProductoBebida {
    private int fotoBebida;
    private String nombreBebida;
    private Double precioBebida;
    private int cantidadBebida;
    private Button anadirBebida;
    private int idBebida;

    // Constructor con parámetros
    public ProductoBebida(int fotoBebida, String nombreBebida, Double precioBebida, int cantidadBebida, Button anadirBebida, int idBebida) {
        this.fotoBebida = fotoBebida;
        this.nombreBebida = nombreBebida;
        this.precioBebida = precioBebida;
        this.cantidadBebida = cantidadBebida;
        this.anadirBebida = anadirBebida;
        this.idBebida = idBebida;
    }

    // Métodos getter y setter para los atributos
    public int getFotoBebida() {
        return fotoBebida;
    }

    public void setFotoBebida(int fotoBebida) {
        this.fotoBebida = fotoBebida;
    }

    public String getNombreBebida() {
        return nombreBebida;
    }

    public void setNombreBebida(String nombreBebida) {
        this.nombreBebida = nombreBebida;
    }

    public Double getPrecioBebida() {
        return precioBebida;
    }

    public void setPrecioBebida(Double precioBebida) {
        this.precioBebida = precioBebida;
    }

    public int getCantidadBebida() {
        return cantidadBebida;
    }

    public void setCantidadBebida(int cantidadBebida) {
        this.cantidadBebida = cantidadBebida;
    }

    public Button getAnadirBebida() {
        return anadirBebida;
    }

    public void setAnadirBebida(Button anadirBebida) {
        this.anadirBebida = anadirBebida;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }
}



