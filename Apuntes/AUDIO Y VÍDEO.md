### REPRODUCIR VIDEO

**EJEMPLO QUE RERODUCE UN VIDEO**

![[cap.png]]
Usamos un VideoView y la clase MediaController:`MediaController` es una interfaz de usuario que proporciona controles multimedia, como botones de reproducción, pausa, avance rápido, retroceso rápido, entre otros, para facilitar la interacción del usuario con el video.

**MAIN JAVA**
![[Captura 3.png]]
sample es el video mp4 que guardamos en la carpeta raw
El Uri es una clase que sirve para identificar la ubicacion

~~~
mediaController = new MediaController(this); 
mediaController.setAnchorView(videoView); 
videoView.setMediaController(mediaController);
~~~

esto sirve para configurar y asociar un MediaController al VideoView
hacemos una instancia MediaController para dar controles sobre el vídeo
con el *setAnchor* hacemos que el MediaController se muestre encima del video
con el *setMediaController* hacemos que el mediaController se asocie al vídeo


![[java2.png]]
**Se podría pausar con .pause y reanudar con .start, pero vamos a hacerlo con un mini.menu**
![[java3.png]]

![[Capturahhgj.png]]



El primer botón muestra los controles(adelante,atras,pausa,final e inicio)
![[cap2 1.png]]

El segundo botón esconde los controles

El tercer botón detiene el vídeo

El cuarto botón adelanta el video a los segundos que se le pasa por parámetro(seg 15)

El quinto botón reinicia el video

Por último configuramos el MediaController para que se adelante o retroceda 5 segundos(las flechas)

**EN EL XML**
~~~
<VideoView  
    android:id="@+id/videoView"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent" />
~~~


### GRABACIÓN AUDIO

**Usamos las clases MediaRecorder y MediaPlayer**

DECLARAMOS:
~~~
 private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;

    private PlayButton   playButton = null;
    private MediaPlayer   player = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private int bufferSize;
~~~


Con esto vemos si se han otorgado los permisos

El valor `200` se usa como un identificador único para la solicitud de permisos. Luego, en el método `onRequestPermissionsResult`, se verifica si el código de solicitud coincide con este valor:
![[cap2 2.png]]
private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


![[cap 1.png]]


onRecord es para iniciar y detener la grabación mediante startRecording y stopRecording

onPlay es para iniciar y detener la reproducción mediante startPlaying y stopPlaying

![[cap3 2.png]]


~~~
private void startPlaying() {  
    player = new MediaPlayer();  
    try {  
        player.setDataSource(fileName);  
        player.prepare();  
        player.start();  
    } catch (IOException e) {  
        Log.e(LOG_TAG, "prepare() failed");  
    }  
}  
  
private void stopPlaying() {  
    player.release();  
    player = null;  
}  
  
private void startRecording() {  
    recorder = new MediaRecorder();  
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
    recorder.setOutputFile(fileName);  
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
    try {  
        recorder.prepare();  
    } catch (IOException e) {  
        Log.e(LOG_TAG, "prepare() failed");  
    }  
    recorder.start();  
}  
  
private void stopRecording() {  
    recorder.stop();  
    recorder.release();  
    recorder = null;  
}
~~~


**Usamos un Botón personalizado con la clase RecordButton para iniciar y detener la grabación**
~~~
class RecordButton extends androidx.appcompat.widget.AppCompatButton {  
    boolean mStartRecording = true;  
  
    OnClickListener clicker = new OnClickListener() {  
        public void onClick(View v) {  
            onRecord(mStartRecording);  
            if (mStartRecording) {  
                setText("Stop recording");  
            } else {  
                setText("Start recording");  
            }  
            mStartRecording = !mStartRecording;  
        }  
    };  
  
    public RecordButton(Context ctx) {  
        super(ctx);  
        setText("Start recording");  
        setOnClickListener(clicker);  
    }  
}
~~~

**Usamos un Botón personalizado con la clase RecordButton para iniciar y detener la reproducción**
~~~
class PlayButton extends androidx.appcompat.widget.AppCompatButton {  
    boolean mStartPlaying = true;  
  
    OnClickListener clicker = new OnClickListener() {  
        public void onClick(View v) {  
            onPlay(mStartPlaying);  
            if (mStartPlaying) {  
                setText("Stop playing");  
            } else {  
                setText("Start playing");  
            }  
            mStartPlaying = !mStartPlaying;  
        }  
    };  
  
    public PlayButton(Context ctx) {  
        super(ctx);  
        setText("Start playing");  
        setOnClickListener(clicker);  
    }  
}
~~~

**EN EL ONCREATE**
1º Creamos la ruta para crear el archivo de audio
~~~
// Record to the external cache directory for visibility  
fileName = getExternalCacheDir().getAbsolutePath();  
fileName += "/audiorecordtest.3gp";
~~~
2ºPonemos los permisos
~~~
ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
~~~
3ºCreamos un LinearLayout con los botones personalizados
~~~
LinearLayout ll = new LinearLayout(this);  
recordButton = new RecordButton(this);  
ll.addView(recordButton,  
        new LinearLayout.LayoutParams(  
                ViewGroup.LayoutParams.WRAP_CONTENT,  
                ViewGroup.LayoutParams.WRAP_CONTENT,  
                0));  
playButton = new PlayButton(this);  
ll.addView(playButton,  
        new LinearLayout.LayoutParams(  
                ViewGroup.LayoutParams.WRAP_CONTENT,  
                ViewGroup.LayoutParams.WRAP_CONTENT,  
                0));  
setContentView(ll);
~~~

**onStop:Este método se llama cuando la actividad se detiene (`onStop`). En este caso, libera los recursos del `MediaRecorder` y `MediaPlayer` si están en uso, asegurando que no haya recursos sin liberar después de que la actividad se detenga.**

~~~
@Override  
public void onStop() {  
    super.onStop();  
    if (recorder != null) {  
        recorder.release();  
        recorder = null;  
    }  
  
    if (player != null) {  
        player.release();  
        player = null;  
    }  
}
~~~


### GRABAR VIDEO
UNICA CLASE(MAIN JAVA)-->EN EL XML SOLO PONEMOS UNA IMAGEN DE LA CAMARA
~~~
package com.example.investigacioncamaraalternativa;  
  
import androidx.appcompat.app.AppCompatActivity;  
import androidx.core.app.ActivityCompat;  
import androidx.core.content.ContextCompat;  
import android.Manifest;  
import android.content.Intent;  
import android.content.pm.PackageManager;  
import android.os.Bundle;  
import android.provider.MediaStore;  
import android.view.View;  
  
public class MainActivity extends AppCompatActivity {  
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;  
  
    private static final int REQUEST_VIDEO_CAPTURE = 1;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        // Primero, verifica si ya tienes el permiso  
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)  
                != PackageManager.PERMISSION_GRANTED) {  
            // Si no tienes el permiso, solicítalo al usuario  
            ActivityCompat.requestPermissions(this,  
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},  
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);  
        }  
    }  
    public void grabarVideo(View view) {  
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);  
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {  
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);  
        }  
    }  
}
~~~


DECLARAMOS PERMISOS:

private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;  
  
private static final int REQUEST_VIDEO_CAPTURE = 1;


EN EL ONCREATE
~~~
if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)  
        != PackageManager.PERMISSION_GRANTED) {  
    // Si no tienes el permiso, solicítalo al usuario  
    ActivityCompat.requestPermissions(this,  
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},  
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);  
}
~~~


MÉTODO PARA GRABAR UN VÍDEO


- Este método se activa cuando el usuario hace clic en un botón en la interfaz de usuario (probablemente un botón para grabar un video).
- Crea un intent (`takeVideoIntent`) utilizando `MediaStore.ACTION_VIDEO_CAPTURE`, que es la acción predeterminada para grabar un video con la aplicación de la cámara del dispositivo.
- Verifica si hay una actividad disponible para manejar la acción de captura de video usando `resolveActivity`.
- Si hay una actividad disponible, inicia la actividad de captura de video mediante `startActivityForResult` con el código de solicitud `REQUEST_VIDEO_CAPTURE`.

~~~
public void grabarVideo(View view) {  
    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);  
    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {  
        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);  
    }  
}
~~~

EN EL XML
~~~
<ImageView  
    android:id="@+id/imgCamara"  
    android:layout_width="182dp"  
    android:layout_height="194dp"  
    android:onClick="grabarVideo"  
    app:layout_constraintBottom_toBottomOf="parent"  
    app:layout_constraintEnd_toEndOf="parent"  
    app:layout_constraintStart_toStartOf="parent"  
    app:layout_constraintTop_toTopOf="parent"  
    app:srcCompat="@android:drawable/ic_menu_camera" />
~~~


### REPRODUCIR AUDIO

CONFIGURAR LA CLASE MAEDIAPLAYER CON EL SONIDO
~~~
// Configurar el MediaPlayer con el sonido mediaPlayer = MediaPlayer.create(this, R.raw.sound); // Configurar clics de botones btnPlay.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { playSound(); } });
~~~

SE DECLARAN 3 BOTONES PARA REPRODUCIR,PAUSAR Y REINICIAR
~~~
// Configurar clics de botones btnPlay.setOnClickListener(new View.OnClickListener() { @Override 
public void onClick(View v) { 
playSound(); 
} 
}); 
btnPause.setOnClickListener(new View.OnClickListener() { 
@Override 
public void onClick(View v) { 
pauseSound(); 
} 
}); 
btnStop.setOnClickListener(new View.OnClickListener() { 
@Override 
public void onClick(View v) { 
stopSound(); 
} 
});
~~~

SUS MÉTODOS
~~~
private void playSound() {
if (mediaPlayer != null && !mediaPlayer.isPlaying()) { 
mediaPlayer.start(); 
} 
} 

private void pauseSound() { 
if (mediaPlayer != null && mediaPlayer.isPlaying()) { 
mediaPlayer.pause(); 
}
} 

private void stopSound() { 
if (mediaPlayer != null) { 
mediaPlayer.stop(); // Es importante resetear el MediaPlayer después de detenerlo mediaPlayer.reset(); 
mediaPlayer = MediaPlayer.create(this, R.raw.sound); 
} 
}
~~~

LIBERAMOS LOS RECURSOS DE MEDIAPLAYER AL CERRAR LA APP

~~~
@Override
protected void onDestroy() { 
super.onDestroy(); // Liberar recursos del MediaPlayer al cerrar la actividad 
if (mediaPlayer != null) { 
mediaPlayer.release(); 
mediaPlayer = null; 
} 
}
~~~
EN EL XML SOLO SE PONEN LOS 3 BOTONES