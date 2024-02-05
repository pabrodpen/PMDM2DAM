package com.example.imagenes2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);

        infoTextView = findViewById(R.id.infoTextView);

        // Configurando listeners para los botones
        configureImageButton(imageButton1, "ILLOJUAN");
        configureImageButton(imageButton2, "KNEKRO");
        configureImageButton(imageButton3, "IBAI");
        configureImageButton(imageButton4, "DJ MARIIO");
    }

    private void configureImageButton(final ImageButton imageButton, final String info) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoTextView.setText(info);
            }
        });
    }
}
