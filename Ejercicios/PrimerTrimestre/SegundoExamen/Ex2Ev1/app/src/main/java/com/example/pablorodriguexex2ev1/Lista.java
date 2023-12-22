package com.example.pablorodriguexex2ev1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {
    ListView l;
    ArrayList<Elemento> itemList;
    ArrayAdapter<Elemento> adapter;
    TextView tUsario,tPassw;
    Button bV;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        String usuario=getIntent().getStringExtra( "EtiquetaUsuario" );
        String password=getIntent().getStringExtra("EtiquetaPassword");
        tUsario=findViewById(R.id.textViewNombre);
        tPassw=findViewById(R.id.textViewPass);
        tUsario.setText(usuario);
        tPassw.setText(password);



        l=findViewById(R.id.listView);
        itemList=new ArrayList<>();
        adapter = new ElementoAdapter(this,itemList);
        l.setAdapter(adapter);

        itemList.add(new Elemento("Portatil",R.drawable.portatil,600));
        itemList.add(new Elemento("Altavoz",R.drawable.altavoz,33));
        itemList.add(new Elemento("Raton",R.drawable.raton,20));
        itemList.add(new Elemento("Alfombrilla",R.drawable.mousepad,10));
        itemList.add(new Elemento("Microfono",R.drawable.microfono,14));
        itemList.add(new Elemento("USB",R.drawable.usb,70));



        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemList.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        bV=findViewById(R.id.buttonVolver);
        bV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Lista.this, MainActivity. class );
                startActivity(intent);
            }
        });

    }


}
