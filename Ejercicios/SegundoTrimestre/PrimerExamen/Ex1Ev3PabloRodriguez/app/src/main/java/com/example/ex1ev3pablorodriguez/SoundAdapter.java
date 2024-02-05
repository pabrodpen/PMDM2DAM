package com.example.ex1ev3pablorodriguez;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SoundAdapter extends ArrayAdapter<ListItem> {

    public SoundAdapter(@NonNull Context context, @NonNull List<ListItem> items) {
        super(context, 0, items);
    }

    // En tu adaptador
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);

        ListItem item = getItem(position);

        if (item != null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), item.getImage());
            imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }

}
