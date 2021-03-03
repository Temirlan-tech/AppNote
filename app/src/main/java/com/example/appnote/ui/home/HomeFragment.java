package com.example.appnote.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.App;
import com.example.appnote.OnItemClickListener;
import com.example.appnote.R;
import com.example.appnote.models.Note;
import com.example.appnote.models.NoteAdapter;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // запускается один раз
        adapter = new NoteAdapter();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // обязательно добавить при создании метода onCreateOptionsMenu
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { // постоянно запускается
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(v -> openForm());
        setFragmentListener();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    // метод при нажатии назад приложение закрывается
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    @Override  // метод для создания меню (три точки)
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_fragment_menu, menu);
    }

    @Override // метод для обработки клика
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sorting) {
            adapter.sortData(App.getDatabase().noteDao().sortAll());
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentListener() { // слушатель тут он получает Бандл через Result (проверка на лог)
        getParentFragmentManager().setFragmentResultListener("rk_note",
                getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("note");
                    adapter.addItem(note);
            }
        });
        initList();
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter.setClick(new OnItemClickListener() {
            @Override
            public void onClick(int position) { // когда мы нажимаем на элемент срабатывает метод getitem
                Note note = adapter.getItem(position);
                openForm(note);
            }

            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                        .setTitle("Data")
                        .setMessage("\n" +
                                "Do you want to delete this data? ?")
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                            }
                        })
                        .setPositiveButton("deleted", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                App.getDatabase().noteDao().delete(adapter.list.get(position));
                                adapter.delete(position);
                                Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.create().show();
            }
        });
    }

    private void openForm() { // работает при нажатии кнопки который открывает новый фрагмент
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.noteFragment);
    }

    private void openForm(Note note) { // работает при клике на элемент и передает данные с элемента в новый фрагмент
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        navController.navigate(R.id.noteFragment, bundle);
    }



}