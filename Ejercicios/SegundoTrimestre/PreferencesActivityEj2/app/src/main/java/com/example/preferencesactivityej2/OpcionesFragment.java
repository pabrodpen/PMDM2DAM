package com.example.preferencesactivityej2;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class OpcionesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.opciones);
    }
}

