package com.example.intentimplicitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    String url;

    Button bPagina,bContactos,bLlamada,bBusqueda,bVoz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bPagina=findViewById(R.id.buttonPagina);
        bContactos=findViewById(R.id.buttonContactos);
        bLlamada=findViewById(R.id.buttonLlamada);
        bBusqueda=findViewById(R.id.buttonBusqueda);
        bVoz=findViewById(R.id.buttonVoz);

        bPagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="https://www.google.com/?hl=es";

                // Crea un Intent con la acción ACTION_VIEW y la URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


        bContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent con la acción ACTION_VIEW y el URI de los contactos
                Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent);
            }
        });

        bLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Número de teléfono al que deseas llamar
                String numeroTelefono = "123456789";

                // Crea un Intent con la acción ACTION_DIAL y el URI del número de teléfono
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numeroTelefono));
                startActivity(intent);
            }
        });

        bBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cadena de búsqueda en Google
                String busqueda = "Android programming";

                // Crea un Intent con la acción ACTION_WEB_SEARCH y la cadena de búsqueda
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra("query", busqueda);
                startActivity(intent);
            }
        });


        bVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent con la acción ACTION_RECOGNIZE_SPEECH
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                // Establece el modelo de lenguaje y el número de resultados a obtener
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivity(intent);

            }
        });


    }
}