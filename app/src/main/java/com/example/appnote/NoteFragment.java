package com.example.appnote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnote.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class NoteFragment extends Fragment {

    private Note note;
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            note = (Note) getArguments().getSerializable("note");
        editText = view.findViewById(R.id.editText);
        if (note != null)
            editText.setText(note.getTitle()); // тут он получает данные с Home fragment,
            // на основе метода Open Form (Note note)
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                close();
            }
        });        
    }

    private void save() { // метод который получает значения их поля и сохраняет его
        String text = editText.getText().toString().trim();
        if (note == null) { // если в edit text нет ничего то идет добавление в базу данных
            long time = System.currentTimeMillis();
            note = new Note(text, time);
            App.getDatabase().noteDao().insert(note); // метод который записывает данные в базу данных
            Toast.makeText(getContext(), "added data", Toast.LENGTH_SHORT).show();
        } else {
            note.setTitle(text);
            App.getDatabase().noteDao().update(note); // метод который обновляет данные в базе данных
            Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();

        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        getParentFragmentManager().setFragmentResult("rk_note", bundle); // передача данных через бандл
    }

    private void close() { // метод который закроет фрагмент программно при нажатии кнопки SAVE
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}