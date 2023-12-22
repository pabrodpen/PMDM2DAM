package com.example.pablorodriguexex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductoViewHolder> {
    private List<Product> listaProductos;
    private Product producto;
    private OnItemClickListener onItemClickListener;
    private final LayoutInflater inflater;


    public ProductAdapter(Context context, List<Product> elementos) {
        inflater = LayoutInflater.from(context);
        this.listaProductos = elementos;
    }


    @Override
    public ProductoViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        Product producto = listaProductos.get(position);

        holder.nombreTextView.setText(producto.getNombre());
        holder.precioTextView.setText(String.format(Locale.getDefault(), "$%d", producto.getPrecio()));
        holder.imageimageview.setImageResource(producto.getImagen());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(producto));
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(Product producto);
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        TextView precioTextView;
        ImageView imageimageview;

        public ProductoViewHolder( View itemView) {
            super(itemView);
            imageimageview=itemView.findViewById(R.id.imageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
        }
    }
}
