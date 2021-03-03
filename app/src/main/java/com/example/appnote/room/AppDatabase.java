package com.example.appnote.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appnote.models.Note;
@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
