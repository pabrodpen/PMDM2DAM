package com.example.miapppablorodriguezpenhia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogLista extends DialogFragment implements AdapterView.OnItemClickListener {

    private OnTipoLugarSelectedListener tipoLugarSelectedListener;

    public interface OnTipoLugarSelectedListener {
        void onTipoLugarSelected(String tipoLugar);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Verificar que la actividad que contiene el fragmento implementa la interfaz
        try {
            tipoLugarSelectedListener = (OnTipoLugarSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnTipoLugarSelectedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configurar la lista de tipos de lugar desde el enum
        String[] listaTiposLugar = TipoLugar.getNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaTiposLugar);
        ListView listView = new ListView(requireContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        // Configurar el diálogo
        builder.setTitle("DIALOGO")
                .setView(listView)
                .setNegativeButton("DIALOGO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Obtener el tipo de lugar seleccionado
        String tipoLugar = TipoLugar.values()[position].getDisplayName();

        // Notificar al listener (asegúrate de que tipoLugarSelectedListener no sea nulo)
        if (tipoLugarSelectedListener != null) {
            tipoLugarSelectedListener.onTipoLugarSelected(tipoLugar);
        }

        // Cerrar el diálogo
        dismiss();
    }


}
