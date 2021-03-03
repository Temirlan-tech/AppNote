package com.example.appnote.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnote.Prefs;
import com.example.appnote.R;

public class FormEdit extends Fragment {
    SharedPreferences sharedPreferences;
    private EditText editTxt;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTxt = view.findViewById(R.id.editTextProfileFragment);
        btnSave = view.findViewById(R.id.btnSaveProfileFragment);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void save() {
        Prefs prefs = new Prefs(getContext());
        String text = editTxt.getText().toString().trim();

        Bundle bundle = new Bundle();
        bundle.putString("note", text);
        FormEdit f = new FormEdit();
        f.setArguments(bundle);
        prefs.saveEdit("edit", text);   // передача данных через бандл
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.profileFragment, bundle);
        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();



    }


}