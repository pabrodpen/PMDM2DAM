package com.example.reproduccionsonido;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnPlay, btnPause, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        // Configurar el MediaPlayer con el sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        // Configurar clics de botones
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
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
    }

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
            mediaPlayer.stop();
            // Es importante resetear el MediaPlayer después de detenerlo
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del MediaPlayer al cerrar la actividad
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
