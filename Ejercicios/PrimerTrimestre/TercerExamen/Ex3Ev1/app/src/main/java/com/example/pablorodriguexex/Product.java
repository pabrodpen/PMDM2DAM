package com.example.pablorodriguexex;


public class Product {
    private String nombre;
    private int precio;
    private int imagen;
    private String detalle;

    public Product(int imagen,String nombre, int precio) {
        this.imagen=imagen;
        this.nombre = nombre;
        this.precio = precio;

    }

    // Getter methods
    public String getNombre() {
        return nombre;
    }
    public int getImagen(){
        return imagen;
    }
    public int getPrecio() {
        return precio;
    }

    public String getDetalle() {
        return detalle;
    }
}
