package com.example.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextClave;
    private CheckBox checkBoxRecordar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextClave = findViewById(R.id.editTextClave);
        checkBoxRecordar = findViewById(R.id.checkBoxRecordar);
        Button btnInicioSesion = findViewById(R.id.btnInicioSesion);

        // Obtener el objeto SharedPreferences, MODE_PRIVATE es usado para que el objeto obtenido
        // sea privado y únicamente se pueda usar en esta aplicación
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        // Verificar si se debe recordar la sesión, declaramos un booleano para usar en la checkbox
        //por defecto ponemos que no la recuerde
        boolean recordarSesion = sharedPreferences.getBoolean("recordar_sesion", false);
        checkBoxRecordar.setChecked(recordarSesion);

        // Si recordarSesion es verdadero (se hace clic en el checkbox)
        // se cargan las credenciales guardadas
        if (recordarSesion) {
            String usuarioGuardado = sharedPreferences.getString("usuario", "");
            String claveGuardada = sharedPreferences.getString("clave", "");
            editTextUsuario.setText(usuarioGuardado);
            editTextClave.setText(claveGuardada);
        }

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener las credenciales ingresadas
                String usuario = editTextUsuario.getText().toString();
                String clave = editTextClave.getText().toString();

                // Guardar las credenciales si el checkbox está marcado
                if (checkBoxRecordar.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("usuario", usuario);
                    editor.putString("clave", clave);
                    editor.putBoolean("recordar_sesion", true);
                    editor.apply();
                } else {
                    // Si no se marca el checkbox borrar las credenciales guardadas
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("usuario");
                    editor.remove("clave");
                    editor.putBoolean("recordar_sesion", false);
                    editor.apply();
                }

                // Mostrar mensaje de éxito y abrir intent éxito
                abrirLoginExito();
            }
        });
    }

    private void abrirLoginExito() {
        Intent intent = new Intent(this, SuccessActivity.class);
        startActivity(intent);
    }
}
