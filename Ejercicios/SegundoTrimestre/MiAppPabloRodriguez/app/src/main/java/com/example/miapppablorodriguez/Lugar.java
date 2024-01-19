package com.example.miapppablorodriguez;

import android.media.Image;
import android.widget.ImageView;

public class Lugar {
    String nombre,direccion,url,comentario,fotoId,valoracionId;
    GeoPunto longitud,latitud;
    TipoLugar tipo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getValoracionId() {
        return valoracionId;
    }

    public void setValoracion(String valoracionId) {
        this.valoracionId = valoracionId;
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

    public String getFotoId() {
        return fotoId;
    }

    public void setFotoId(String fotoId) {this.fotoId = fotoId;}

    public Lugar(String nombre, String direccion, String url, String comentario, String valoracionId,
                 GeoPunto longitud, GeoPunto latitud, TipoLugar tipo, String fotoId) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.url = url;
        this.comentario = comentario;
        this.valoracionId = valoracionId;
        this.longitud = longitud;
        this.latitud = latitud;
        this.tipo = tipo;
        this.fotoId = fotoId;
    }


}


//    nombre: texto corto, dirección: texto largo, punto geográfico (clase GeoPunto): longitud y latitud,
//    foto (imagen), url, comentario, fecha, valoración, un enumerado TipoLugar.
