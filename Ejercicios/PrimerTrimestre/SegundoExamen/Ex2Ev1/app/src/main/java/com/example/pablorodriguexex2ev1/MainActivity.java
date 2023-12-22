package com.example.pablorodriguexex2ev1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bValidar;
    EditText t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=findViewById(R.id.editTextText);
        t2=findViewById(R.id.editTextText2);
        bValidar=findViewById(R.id.button);
        bValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity. this , Lista. class );
                Bundle b= new Bundle();
                intent.putExtra( "EtiquetaUsuario" ,t1.getText().toString());
                intent.putExtra("EtiquetaPassword",t2.getText().toString());
                startActivity(intent);
            }
        });





    }
}