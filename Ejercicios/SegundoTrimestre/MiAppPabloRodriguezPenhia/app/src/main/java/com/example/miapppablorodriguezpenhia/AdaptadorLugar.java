package com.example.miapppablorodriguezpenhia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdaptadorLugar extends ArrayAdapter<Lugar> {
    private final LayoutInflater inflater;
    private Lugar lugar;
    public AdaptadorLugar(@NonNull Context context, List<Lugar> lugares) {
        super(context, 0,lugares);
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=convertView;
        if(itemView==null){
            itemView=inflater.inflate(R.layout.lugar,parent,false);
        }
        Lugar lugar=getItem(position);

        return itemView;
    }


}
