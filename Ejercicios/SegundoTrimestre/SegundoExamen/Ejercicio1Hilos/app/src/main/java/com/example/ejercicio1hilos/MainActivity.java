package com.example.ejercicio1hilos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // Inicializar el controlador para manejar los mensajes de los hilos
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // Obtener los datos del mensaje
                Bundle bundle = msg.getData();
                int i = bundle.getInt("i");
                String threadName = bundle.getString("thread");

                // Mostrar el mensaje en el TextView
                textView.append("Hilo " + threadName + ": " + i + "\n");

                return true;
            }
        });

        // Crear y ejecutar los hilos
        Hilo hilo1 = new Hilo(10, 100);
        Hilo hilo2 = new Hilo(15, 200);

        // Modificar la prioridad del hilo2
        hilo2.setPriority(Thread.MAX_PRIORITY);

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
    }

    // Clase Hilo
    class Hilo extends Thread {
        private int maximo;
        private int tiempo;

        public Hilo(int maximo, int tiempo) {
            this.maximo = maximo;
            this.tiempo = tiempo;
        }

        @Override
        public void run() {
            for (int i = 0; i < maximo; i++) {
                try {
                    Thread.sleep(tiempo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Construir el mensaje para enviar al controlador handler
                Message msg = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("i", i);
                b.putString("thread", currentThread().getName());
                msg.setData(b);

                // Enviar el mensaje al controlador
                handler.sendMessage(msg);
            }
        }
    }
}
