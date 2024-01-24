package com.example.miapppablorodriguez;

public enum TipoLugar {
    CAFETERÍA("Cafetería"),
    GOURMET("Gourmet"),
    PESCADOS_Y_MARISCOS("Pescados Y Mariscos"),
    ASADOR("Asador"),

    TAPAS("Tapas");


    //display name para coger el string del nombre

    private final String displayName;

    TipoLugar(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Método estático para obtener una lista de nombres de tipo de lugar
    public static String[] getNames() {
        TipoLugar[] tipos = values();
        String[] names = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            names[i] = tipos[i].getDisplayName();
        }
        return names;
    }
}


