package com.example.repaso1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.prefs.Preferences;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_preferencias);

        addPreferencesFromResource(R.xml.preferences); // Agregado para cargar las preferencias desde XML
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("key_username")){
           mostrarUser();
        }else if(s.equals("key_oscuro")){
            oscuro();
        }
    }

    private void mostrarUser(){
        Preference usuarioPreference=findPreference("key_user_name");
        usuarioPreference.setSummary(PreferenceManager.getDefaultSharedPreferences(this)
                .getString("key_usern_name",""));
    }

    private void oscuro(){
        boolean esOscuro=PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("key_oscuro",false);

        if(esOscuro){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }
}
