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
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//creamos el toolbar

        // Obtener el color desde el recurso de colores de manera compatible
        int color = ContextCompat.getColor(this, R.color.blue);

        // Cambiar el color del Toolbar usando el recurso de colores
        toolbar.setBackgroundColor(color);

        mImageView=findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.img1);
        t=findViewById(R.id.textView2);

        //Funcionalidad 1: Actualizaciones en el Hilo Principal
        /*Actualiza la interfaz de usuario desde el hilo principal
        new Handler().post(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(getBaseContext(),"Hola desde un Handler",Toast.LENGTH_SHORT).show();
           }
       });*/

        /*POSTDELAYED-->QUITAMOS LA IMG A LOS 5 SEG

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageView.setImageResource(R.drawable.img2);
            }
        },5000);*/


         //POSTDELAYED-->CAMBIAMOS DE ACTIVIDAD

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent=new Intent(getBaseContext(),Main2.class);
               startActivity(intent);
            }
        },10000);*/



        //handler asociado al hilo principal
        //enviamos un mensaje desde el hilo principal
            Handler messageHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bundle datos=msg.getData();
                Toast.makeText(getApplicationContext(),datos.getString("palabra"),Toast.LENGTH_LONG).show();
            }
        };

        /*Message msg=new Message();
        Bundle datos=new Bundle();
        datos.putString("palabra","Enviando un mensaje desde un Handler");

        msg.setData(datos);
        messageHandler.sendMessage(msg);*/


            //si quisiesemos enviar un mensaje desde un hilo secundario:
        //nos ponemos en el caso de que hay una tarea que dura 7 segundos,
        // por lo que para hacer otra tarea distinta la programamos en el hilo
        // secundario,g por lo que a los 7 seg saldra el toast


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
    public boolean onCreateOptionsMenu(Menu menu) {//este metodo esta por defecto
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change) {//si pulsamos el boton +

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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}






