package com.example.ejrepaso1;

import androidx.annotation.NonNull;

public class Elemento {
    private String nombre;
    private int imagen;

    public Elemento(String nombre, int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {

        return nombre;
    }

    public int getImagen() {
        return imagen;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }


}
