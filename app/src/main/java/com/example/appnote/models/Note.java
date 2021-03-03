package com.example.appnote.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity // класс для команды базы данных что этот класс используется для хранения как таблица
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private long createdAt; // когда записываем новую запись, должны получить время.

    public Note() {
    }

    public Note(String title, long createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
