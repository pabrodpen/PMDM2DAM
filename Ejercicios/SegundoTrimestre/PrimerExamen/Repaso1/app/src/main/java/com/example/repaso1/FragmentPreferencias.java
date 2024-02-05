package com.example.repaso1;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentPreferencias extends Fragment {
    TextView user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_preferencias,container,false);

        user=view.findViewById(R.id.usernameTextView);

        showCurrentUsername();

        return view;

    }

    private void showCurrentUsername() {
        boolean isFeatureEnabled = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("key_oscuro", false);

        if (isFeatureEnabled) {

            String username = PreferenceManager.getDefaultSharedPreferences(this)
                    .getString("key_user_name", "");
            user.setText("Usuario: " + username);
        } else {

            user.setText("               ");
        }
    }
}
