package com.example.diseniosgraficos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup r = findViewById(R.id.radioGroup);
        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int a) {
                TextView t = (TextView) findViewById(R.id.textView);
                if (a == R.id.radioButton2) { // Talavera
                    t.setText("Buena elección!: El Talavera promete!!");
                } else if (a == R.id.radioButton3) { // Alcazar
                    t.setText("Gran equipo la gimnástica!!");
                } else if (a == R.id.radioButton1) { // Albacete
                    t.setText("El Albacete no es el mismo desde que se fue Iniesta");
                } else if (a == R.id.radioButton4) { // Otros
                    t.setText("El dinero no lo es todo....");
                }
            }
        });



        CheckBox c=findViewById(R.id.checkBox);

        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean c) {
                TextView t=(TextView)findViewById(R.id.textView2);

                if(c)
                    t.setText("Te gusta el fútbol!!");
                else
                    t.setText("No te gusta el fútbol?!??!!");
            }
        });



        ImageButton button2=findViewById(R.id.imageButton);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t=(TextView) findViewById(R.id.textView3);
                ImageView img=(ImageView) findViewById(R.id.imageView);

                t.setText("Llamando a Walter");
                img.setImageResource(R.drawable.walter2);
                button2.setImageResource(R.drawable.tfno2);
            }
        });


    }
}


