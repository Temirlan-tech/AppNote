package com.example.appnote.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll(); // метод который при вызове возвращает все записи из таблицы нот, в виде листа

    @Query("SELECT * FROM `note` ORDER BY title ASC") // метод сортировки
    List<Note> sortAll();

    @Delete
    int delete (Note note); // удаление из базы данных

    @Insert
    void insert(Note note); // метод который принимает модель Нот и если вызвать этот метод то он выполнит запись в базу данных

    @Update
    int update (Note note);

}
