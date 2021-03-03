package com.example.appnote;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences; // класс который работает с Shared Prefs, у него есть
    // методы Put Get Remove

    public Prefs(Context context){ // можно создать несколько Prefs
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }


    public void saveIsShown() { // это показано (перевод)
        preferences.edit().putBoolean("isShown", true).apply(); // без apply не сохранит
    }

    public boolean isShown (){
        return preferences.getBoolean("isShown", false);
    }

    public void saveEdit(String key, String text){
        preferences.edit().putString(key, text).apply();
    }

    public String isEdit(String key){
        return preferences.getString(key, "");
    }
}

// Shared Preferences он подходит для сохранения некоторых состояний, простых значений или сохранить одно поле (имя пользов)
// во многих приложениях логин и пароль хранится в shared prefs (доступ только у нашего приложения)
// Internal Storage тут устанваливается осн часть приложения и хранит данные кеш
// External Storage данные которые можно увидить в галлерее (к примеру атом скачивание с вотсаппа)
