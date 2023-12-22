package com.example.ex1ev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Juego extends AppCompatActivity {

    static GridLayout grid;
    static TextView t,tUsuario;
    static int contCasillas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);

        grid = findViewById(R.id.gridLayout);
        t = findViewById(R.id.textViewIntro);
        tUsuario=findViewById(R.id.textViewUsuario);
        contCasillas = 0;
        String usuario=getIntent().getStringExtra("NOMBRE");
        tUsuario.setText(usuario);

        for (int i = 0; i < 11; i++) {
            Button button = new Button(this);
            button.setLayoutParams(new ViewGroup.LayoutParams(210, 210));
            grid.addView(button, i);

            if (i == 9) {
                button.setText("Volver");
            } else if (i == 10) {
                button.setText("Reiniciar");
            }

            // Métodos de los botones
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("Volver".equals(button.getText())) {
                        Intent intent = new Intent(Juego.this, MainActivity.class);
                        startActivity(intent);
                    } else if ("Reiniciar".equals(button.getText())) {
                        reiniciar();
                    } else { // Botón normal
                        if(button.getText().equals("")){
                            if (contCasillas >= 9) {
                                t.setText("EMPATE");
                                desactivarBotones();
                            } else {
                                // Posición aleatoria para poner el O
                                int posAleatoria = (int) (Math.random() * 8);
                                Button b = (Button) grid.getChildAt(posAleatoria);
                                while (!"".equals(b.getText())) {
                                    // Si la casilla ya está ocupada, busca otra aleatoria
                                    posAleatoria = (int) (Math.random() * 8);
                                    b = (Button) grid.getChildAt(posAleatoria);
                                }

                                b.setText("O");
                                contCasillas++;
                                finJuego();
                            }
                        }
                        button.setText("X");
                        contCasillas++;
                        finJuego();


                    }
                }
            });
        }
    }

    static void finJuego() {
        boolean ganador = false, perdedor = false;
        for (int i = 0; i < 9 && !ganador && !perdedor; i++) {
            Button b = (Button) grid.getChildAt(i);
            if ("X".equals(b.getText())) {
                int fila = i / 3;
                int columna = i % 3;

                // Verificar combinaciones horizontales
                if (columna == 0 && "X".equals(((Button) grid.getChildAt(i + 1)).getText())
                        && "X".equals(((Button) grid.getChildAt(i + 2)).getText())) {
                    ganador = true;
                }

                // Verificar combinaciones verticales
                if (fila == 0 && "X".equals(((Button) grid.getChildAt(i + 3)).getText())
                        && "X".equals(((Button) grid.getChildAt(i + 6)).getText())) {
                    ganador = true;
                }

                // Verificar combinaciones diagonales
                if (i == 0 && "X".equals(((Button) grid.getChildAt(4)).getText())
                        && "X".equals(((Button) grid.getChildAt(8)).getText())) {
                    ganador = true;
                } else if (i == 2 && "X".equals(((Button) grid.getChildAt(4)).getText())
                        && "X".equals(((Button) grid.getChildAt(6)).getText())) {
                    ganador = true;
                }
            }else if ("O".equals(b.getText())) {
                int fila = i / 3;
                int columna = i % 3;

                // Verificar combinaciones horizontales
                if (columna == 0 && "O".equals(((Button) grid.getChildAt(i + 1)).getText())
                        && "O".equals(((Button) grid.getChildAt(i + 2)).getText())) {
                    perdedor = true;
                }

                // Verificar combinaciones verticales
                if (fila == 0 && "O".equals(((Button) grid.getChildAt(i + 3)).getText())
                        && "O".equals(((Button) grid.getChildAt(i + 6)).getText())) {
                    perdedor = true;
                }

                // Verificar combinaciones diagonales
                if (i == 0 && "O".equals(((Button) grid.getChildAt(4)).getText())
                        && "O".equals(((Button) grid.getChildAt(8)).getText())) {
                    perdedor = true;
                } else if (i == 2 && "O".equals(((Button) grid.getChildAt(4)).getText())
                        && "O".equals(((Button) grid.getChildAt(6)).getText())) {
                    perdedor = true;
                }
            }
        }

        if (ganador) {
            t.setText("HAS GANADO!");
            desactivarBotones();
        } else if (perdedor) {
            t.setText("HAS PERDIDO!");
            desactivarBotones();
        }
    }

    static void desactivarBotones() {
        for (int i = 0; i < 9; i++) {
            Button button = (Button) grid.getChildAt(i);
            button.setEnabled(false);
        }
    }

    static void reiniciar() {
        for (int i = 0; i < 9; i++) {
            Button button = (Button) grid.getChildAt(i);
            button.setEnabled(true);
            button.setText("");
        }

        t.setText(""); // Restablecer el mensaje de estado
        contCasillas = 0; // Restablecer el contador de casillas
    }
}
