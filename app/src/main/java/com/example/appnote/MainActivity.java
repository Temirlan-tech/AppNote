package com.example.appnote;

import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initNavController(navView);
        Prefs prefs = new Prefs(this);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){ // получаем текущего пользователя,
            // условие что бы узнать зареган ли пользователь или нет
            navController.navigate(R.id.phoneFragment);
        }
        if (!prefs.isShown()){ // метод если борд фрагмент не показывали то запускаем его, если показыали то ничего не будет
            navController.navigate(R.id.boardFragment);
        }
    }

    private void initNavController(BottomNavigationView navView) {
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavController это новый класс который работает с фрагментами
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // этот метод определяет какой фрагмент открыт в данный момент
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                ArrayList<Integer> list = new ArrayList<>();

                // логика где будет отражаться нижний таб а где скрывать
                list.add(R.id.navigation_home);
                list.add(R.id.navigation_dashboard);
                list.add(R.id.navigation_notifications);
                list.add(R.id.profileFragment);

                if (list.contains (destination.getId())){
                    navView.setVisibility(View.VISIBLE);
                } else  {
                    navView.setVisibility(View.GONE);
                }
                if (destination.getId() == R.id.boardFragment){
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() { // метод который подключает всем фрагментам кнопку назад
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}

// виды навигаций
// TAB верхний таб (пример вотс апп ЧАТЫ СТАТУСЫ КОНТАКТЫ) двигаются по свайпу
// Bottom navigation bar нижний таб (нельзя скролить и что бы перейти нужно нажимать на кнопку)
// Navigation drawer (шторка которая справа свайпом можно выдвинуть) или три точки
// Постарайтесь использовать одну навигацию
// Компоненты для реализации
// View pager это область где можно свайпом двигать налево и направо
// Menu это три точки
// для работы с  ROOM нужны основные компоненты DAO Entity Database
// Firebase это полноценный бэк енд, системанная отправка push уведомлений сделать можно только на firebase
// Cloud Firestore это хранение данных (String, int и т.д)
// Cloud Storage это хранение файлов (видео, картинки и т.д.)
