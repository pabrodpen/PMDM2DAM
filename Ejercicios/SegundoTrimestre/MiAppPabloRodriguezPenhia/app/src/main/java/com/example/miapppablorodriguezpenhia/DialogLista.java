package com.example.miapppablorodriguezpenhia;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.miapppablorodriguezpenhia.TipoLugar;

public class DialogLista extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona un tipo de lugar")
                .setItems(TipoLugar.getNames(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tipoLugar = TipoLugar.values()[which].name();
                        // Notificar la selección al listener en SecondFragment
                        ((OnTipoLugarSelectedListener) getTargetFragment()).onTipoLugarSelected(tipoLugar);
                    }
                });
        return builder.create();
    }

    public interface OnTipoLugarSelectedListener {
        void onTipoLugarSelected(String tipoLugar);
    }
}
