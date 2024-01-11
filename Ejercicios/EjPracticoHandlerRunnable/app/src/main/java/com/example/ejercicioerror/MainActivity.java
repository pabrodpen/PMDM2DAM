package com.example.ejercicioerror;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        mImageView = findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.img1);
        t = findViewById(R.id.textView2);

        // Funcionalidad 1: Actualizaciones en el Hilo Principal
        //showToastFromHandler("Hola desde un Handler");

        // POSTDELAYED-->QUITAMOS LA IMG A LOS 5 SEG
        //updateImageDelayed();

        // POSTDELAYED-->CAMBIAMOS DE ACTIVIDAD
        //switchActivityDelayed();

        // Handler asociado al hilo principal
        // Enviamos un mensaje desde el hilo principal
        //sendMessageFromHandler("Enviando un mensaje desde un Handler");

        // Si quisieramos enviar un mensaje desde un hilo secundario:
        // Nos ponemos en el caso de que hay una tarea que dura 7 segundos,
        // por lo que para hacer otra tarea distinta la programamos en el hilo
        // secundario, por lo que a los 7 seg saldra el toast
        //simulateTaskAndSendMessage();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int color = ContextCompat.getColor(this, R.color.blue);
        toolbar.setBackgroundColor(color);
    }

    private void showToastFromHandler(String message) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateImageDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageView.setImageResource(R.drawable.img2);
            }
        },5000);
    }

    private void switchActivityDelayed() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getBaseContext(), Main2.class);
            startActivity(intent);
        }, 10000);
    }

    private void sendMessageFromHandler(String message) {
        Handler messageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                Toast.makeText(getApplicationContext(), data.getString("palabra"), Toast.LENGTH_LONG).show();
            }
        };

        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("palabra", message);
        msg.setData(data);
        messageHandler.sendMessage(msg);
    }

    private void simulateTaskAndSendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Simular una tarea
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Enviar un mensaje al hilo principal
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        Bundle datos=new Bundle();
                        datos.putString("palabra","Enviando un mensaje desde un Handler");

                        msg.setData(datos);
                        messageHandler.sendMessage(msg);

                    }
                });

            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_change) {
            performBackgroundTask();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void performBackgroundTask() {
        new Thread(new Runnable() {
            Handler handler = new Handler(Looper.getMainLooper());
            @Override
            public void run() {
                // Tarea secundaria


                // Intenta actualizar la interfaz de usuario directamente desde el hilo secundario
                // Esto provocará un error
                //t.setText("Tarea completada en hilo secundario");

                // Utiliza Handler y Runnable para actualizar la interfaz de usuario desde el hilo principal
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        t.setText("TAREA COMPLETADA EN EL HILO PRINCIPAL");
                    }
                });
            }
        }).start();
    }
}
