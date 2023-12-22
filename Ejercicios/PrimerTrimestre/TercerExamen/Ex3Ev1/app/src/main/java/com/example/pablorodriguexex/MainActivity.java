package com.example.pablorodriguexex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ProductListFragment.OnProductClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            // Si estamos en una tablet, agregamos el fragmento de lista de productos
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new ProductListFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onProductSelected(int position) {
        ProductDetailFragment detailFragment = (ProductDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (detailFragment != null) {
            // Si estamos en una tablet, actualizamos el fragmento de detalle
            detailFragment.updateProductDetail(position);
        } else {
            // Si estamos en un teléfono móvil, reemplazamos el fragmento de lista con el de detalle
            ProductDetailFragment newDetailFragment = new ProductDetailFragment();
            Bundle args = new Bundle();
            args.putInt(ProductDetailFragment.ARG_POSITION, position);
            newDetailFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newDetailFragment);
            transaction.commit();
        }
    }
}
