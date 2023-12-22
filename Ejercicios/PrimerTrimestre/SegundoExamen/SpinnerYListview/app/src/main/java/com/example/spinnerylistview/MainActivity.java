package com.example.spinnerylistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Spinner cmbOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Crear un array con los elementos seleccionables
        String [] elementos={"Toledo","Ciudad Real",
                "Cuenca","Guadalajara","Albacete"};
        //Declaras un adaptador de Texto (String)
        ArrayAdapter<String> adaptador;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtienes una referencia a la lista
        ListView l=(ListView)findViewById(R.id.listView);
        //Creas el adaptador
        adaptador=new ArrayAdapter<String>(this,R.layout.fila,elementos);
        //Le das el adaptador a la lista
        l.setAdapter(adaptador);
        l.setOnItemClickListener(this);



        final String[] datos =new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
        adaptador =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, datos);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valores_array, android.R.layout.simple_spinner_item);




        cmbOpciones = (Spinner)findViewById(R.id.spinner);

        adaptador.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        cmbOpciones.setAdapter(adapter);

        ArrayAdapter <CharSequence> adaptador2 = ArrayAdapter.createFromResource (this,

                R.array.valores_array, android.R.layout.simple_spinner_item);

        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cmbOpciones . setAdapter (adaptador2);

        cmbOpciones.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,

                                               android.view.View v, int position, long id) {

                    }

                    public void onNothingSelected(AdapterView<?> parent) {


                    }

                });



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView t=findViewById(R.id.textView2);
        t.setText("Has elegido:"+parent.getItemAtPosition(position).toString());

    }
}