package com.sunny.icarusinbloom.recycler_elem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "DiaryEntries")
public class DiaryItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String date;
    private String image;
    private String content;
    private int user_id;

    public DiaryItem(String date, String image, String content, int user_id) {
        this.date = date;
        this.image = image;
        this.content = content;
        this.user_id=user_id;
    }

    @Override
    public String toString() {
        return "DiaryItem{" +
                "Id=" + Id +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
