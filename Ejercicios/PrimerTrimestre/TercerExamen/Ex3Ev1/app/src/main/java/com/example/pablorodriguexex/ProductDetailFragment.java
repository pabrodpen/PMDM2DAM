package com.example.pablorodriguexex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProductDetailFragment extends Fragment {

    public static final String ARG_POSITION = "position";
    private int currentPosition = -1;
    Button bVolver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

       bVolver=view.findViewById(R.id.buttonVolver);
       bVolver.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
           }
       });
        return view;
    }

    public void updateProductDetail(int position) {
        TextView detailTextView = getView().findViewById(R.id.productDetailTextView);
        String[] details = getResources().getStringArray(R.array.product_details);

        detailTextView.setText(details[position]);
        currentPosition = position;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            // Actualizamos el detalle si hay argumentos (cuando se usa en teléfonos)
            updateProductDetail(args.getInt(ARG_POSITION));
        } else if (currentPosition != -1) {
            // Actualizamos el detalle si ya se ha seleccionado un producto (cuando se usa en tablets)
            updateProductDetail(currentPosition);
        }

    }
}


