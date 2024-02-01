package com.example.miapppablorodriguezpenhia;


public class Lugar {
    private String nombre;
    private String direccion;
    private String tfno;
    private String url;
    private String fecha;
    private String tipo;
    private String rutaFoto;
    private float valoracion; // Nuevo atributo para la valoración
    GeoPunto latitud,longitud;

    public Lugar(String nombre, String direccion, String tfno, String url, String fecha, String tipo, String rutaFoto, float valoracion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tfno = tfno;
        this.url = url;
        this.fecha = fecha;
        this.tipo = tipo;
        this.rutaFoto = rutaFoto;
        this.valoracion = valoracion;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }






}