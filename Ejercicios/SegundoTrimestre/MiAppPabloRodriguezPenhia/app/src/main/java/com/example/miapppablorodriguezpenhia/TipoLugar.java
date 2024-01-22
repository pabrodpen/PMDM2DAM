package com.example.miapppablorodriguezpenhia;

public enum TipoLugar {
    CAFETERÍA("Cafetería"),
    RESTAURANTE("Restaurante"),
    HOTEL("Hotel"),
    PARQUE("Parque");

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


