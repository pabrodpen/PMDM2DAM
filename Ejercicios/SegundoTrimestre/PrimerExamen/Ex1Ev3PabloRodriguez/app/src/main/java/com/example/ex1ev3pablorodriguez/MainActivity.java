package com.example.ex1ev3pablorodriguez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListItem> itemList;
    private ArrayAdapter<ListItem> adapter;
    private ListView listView;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        itemList = new ArrayList<>();


        //itemList.add(new ListItem(R.drawable.im_, "android.resource://" + getPackageName() + "/" + R.raw.colibri));
        itemList.add(new ListItem(R.drawable.im_cuervo, "android.resource://" + getPackageName() + "/" + R.raw.cuervo));
        itemList.add(new ListItem(R.drawable.im_kiwi, "android.resource://" + getPackageName() + "/" + R.raw.kiwi));
        itemList.add(new ListItem(R.drawable.im_loro, "android.resource://" + getPackageName() + "/" + R.raw.loro));
        itemList.add(new ListItem(R.drawable.im_pavo, "android.resource://" + getPackageName() + "/" + R.raw.pavo));
        itemList.add(new ListItem(R.drawable.im_pinguino, "android.resource://" + getPackageName() + "/" + R.raw.pinguino));


        // Reemplaza la creación del adaptador con tu propio adaptador personalizado
        adapter = new SoundAdapter(this, itemList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                playSound(itemList.get(position).getSoundFilePath());            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                stopSound();
                return true;
            }
        });
    }

    private void playSound(String soundFilePath) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(soundFilePath));
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}