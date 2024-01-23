package com.example.miapppablorodriguezpenhia;

import android.media.Image;

import java.io.Serializable;
import java.time.LocalDate;

public class Lugar implements Serializable {
    String nombre,direccion,url,comentario,valoracion,tfno,rutaFoto;
    GeoPunto longitud,latitud;
    TipoLugar tipo;
    String fecha,hora;

    public Lugar() {

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

    /*public Lugar(String nombre, String direccion, String url, String comentario, String valoracion,
                 GeoPunto longitud, GeoPunto latitud, TipoLugar tipo, Image foto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.url = url;
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.tipo = tipo;
        this.foto = foto;
    }*/

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Lugar(String nombre, String direccion, String tfno, String url,String f,String h,String rutaFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.url = url;
        this.tfno=tfno;
        this.fecha=f;
        this.hora=h;
        //asignamos un tipo de lugar por defecto para que no de error
        this.tipo=TipoLugar.CAFETERÍA;
        this.rutaFoto=rutaFoto;
    }

    @Override
    public String toString() {
        return "nombre:" + nombre + '\'' +
                ", direccion:" + direccion + '\'' +
                ", url:" + url+ '\''+",telefono:"+tfno;
    }


}


//    nombre: texto corto, dirección: texto largo, punto geográfico (clase GeoPunto): longitud y latitud,
//    foto (imagen), url, comentario, fecha, valoración, un enumerado TipoLugar.
