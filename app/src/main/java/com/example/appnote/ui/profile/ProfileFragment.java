package com.example.appnote.ui.profile;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnote.Prefs;
import com.example.appnote.R;

public class ProfileFragment extends Fragment {

    private ImageView imageView; // переделать ImageView
    private ActivityResultLauncher<String> addFromGallery;
    private TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // обязательно добавить при создании метода onCreateOptionsMenu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override // метод для создания меню (три точки)
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_fragment_menu, menu);
    }

    @Override // метод для обработки клика
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editText) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.editText2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textViewProfileFragment);
        addImage(view);

        // сохраняем имя в Share prefs, данные получаем по методу getData
        Prefs prefs = new Prefs(getContext());
        String s  = prefs.isEdit("edit");
        textView.setText(s);
        getData();

    }

    private void getData() {
        if (getArguments() != null) {
            String message = getArguments().getString("note");
            textView.setText(message);
        }
    }

    private void addImage(View view) {
        imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener((View v) -> {
            imageFromGallery();
        });
        addFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    imageView.setImageURI(uri);
                });
    }

    private void imageFromGallery() {
        addFromGallery.launch("image/*"); // фильтр что бы в галереи
        // открылись только фотографии, * означает все форматы фотографий
    }
}