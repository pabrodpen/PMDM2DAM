package com.example.pablorodriguexex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductListFragment extends Fragment {

    OnProductClickListener callback;
    RecyclerView recyclerView;
    ArrayList<Product> itemList;
    ProductAdapter adapter;

    // Interfaz para comunicarse con la actividad principal
    public interface OnProductClickListener {
        void onProductSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnProductClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnProductClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        recyclerView = view.findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemList = new ArrayList<>();
        adapter = new ProductAdapter(getActivity(), itemList);
        recyclerView.setAdapter(adapter);

        itemList.add(new Product(R.drawable.portatil, "Portatil", 600));
        itemList.add(new Product(R.drawable.altavoz, "Altavoz", 33));
        itemList.add(new Product(R.drawable.raton, "Raton", 20));
        itemList.add(new Product(R.drawable.mousepad, "Alfombrilla", 10));
        itemList.add(new Product(R.drawable.microfono, "Microfono", 14));
        itemList.add(new Product(R.drawable.usb, "USB", 70));

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product producto) {
                // Reemplaza el fragmento actual con el fragmento de detalle
                ProductDetailFragment detailFragment = new ProductDetailFragment();
                Bundle args = new Bundle();
                args.putInt("position", itemList.indexOf(producto));
                detailFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
