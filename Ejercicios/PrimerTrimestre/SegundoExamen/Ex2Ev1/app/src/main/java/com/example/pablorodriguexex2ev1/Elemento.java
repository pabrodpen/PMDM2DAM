package com.example.pablorodriguexex2ev1;

import androidx.annotation.NonNull;

public class Elemento {
    private String nombre;
    private int imagen;

    private int precio;

    public Elemento(String nombre, int imagen,int precio) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio=precio;
    }

    public String getNombre() {

        return nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public int getPrecio(){
        return precio;
    }



}
