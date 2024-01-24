package com.example.miapppablorodriguez;

import java.io.Serializable;

public class Lugar implements Serializable {
    String nombre, direccion, url, comentario, valoracion, tfno, rutaFoto;
    GeoPunto longitud, latitud;
    TipoLugar tipo;
    String fecha;

    public Lugar() {
        // Constructor vacío
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTfno() {
        return tfno;
    }

    public void setTfno(String tfno) {
        this.tfno = tfno;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public GeoPunto getLongitud() {
        return longitud;
    }

    public void setLongitud(GeoPunto longitud) {
        this.longitud = longitud;
    }

    public GeoPunto getLatitud() {
        return latitud;
    }

    public void setLatitud(GeoPunto latitud) {
        this.latitud = latitud;
    }

    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public Lugar(String nombre, String direccion, String tfno, String url, String f, String rutaFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.url = url;
        this.tfno = tfno;
        this.fecha = f;
        // asignamos un tipo de lugar por defecto para que no dé error
        this.tipo = TipoLugar.CAFETERÍA;
        this.rutaFoto = rutaFoto;
    }

}