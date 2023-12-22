package com.example.intentexplicitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createLlamada(View view) {

        Intent intent=new Intent(Intent.ACTION_DIAL);

        intent.setData(Uri.parse("tel:112"));

        if(intent.resolveActivity(getPackageManager())!=null){

            startActivity(intent);
        }
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com/")));
    }

    public void abrirContactos(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Si no se encuentra una aplicación de contactos en el dispositivo
            // aquí puedes manejar esa situación, por ejemplo, mostrando un mensaje.
        }
    }

    public void abrirAsistentePorVoz(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Si no se encuentra una aplicación de asistente de voz en el dispositivo
            // aquí puedes manejar esa situación, por ejemplo, mostrando un mensaje.
        }
    }
}