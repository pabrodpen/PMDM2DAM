package com.example.miapppablorodriguezpenhia;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    private FeedReaderContract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "lugares";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_DIRECCION = "direccion";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_DATE = "fecha";
        public static final String COLUMN_NAME_TFNO = "telefono";
        public static final String COLUMN_NAME_TIPO = "tipo";
        public static final String COLUMN_NAME_RUTA_FOTO = "ruta_foto";
        public static final String COLUMN_NAME_VALORACION = "valoracion";

        // Agrega esta línea
        public static final String COLUMN_NAME_ID = _ID;  // Esto utilizará el valor de BaseColumns._ID
    }

}
