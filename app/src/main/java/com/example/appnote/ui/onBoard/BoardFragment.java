package com.example.appnote.ui.onBoard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnote.OnItemClickListener;
import com.example.appnote.Prefs;
import com.example.appnote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.prefs.PreferenceChangeEvent;

public class BoardFragment extends Fragment {
    private Button btnSkip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // отвечает за вью элементы
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { //
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        BoardAdapter adapter = new BoardAdapter();
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2,(tab, position) -> {
            tab.setText((""));
        }).attach();
        adapter.setOnStartCkickLictener(() -> close());
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
            // метод при нажатии назад приложение закрывается
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        btnSkip = view.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(v -> {
            close();
            Log.e("TAG", "jaajajjaja:00 ");
        });
    };

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveIsShown();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

}