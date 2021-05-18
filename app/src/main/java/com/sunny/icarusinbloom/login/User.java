package com.sunny.icarusinbloom.login;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Users",
indices = {@Index(value = "email",unique = true)})
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String password;
    private String email;
    private String bday;
    private String photoPref;
    private String mailPref;

    public User(String name, String password, String email, String bday, String photoPref, String mailPref) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.bday = bday;
        this.photoPref = photoPref;
        this.mailPref=mailPref;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", bday='" + bday + '\'' +
                ", photoPref='" + photoPref + '\'' +
                ", mailPref='" + mailPref + '\'' +
                '}';
    }

    public String getMailPref() {
        return mailPref;
    }

    public void setMailPref(String mailPref) {
        this.mailPref = mailPref;
    }

    public String getPhotoPref() {
        return photoPref;
    }

    public void setPhotoPref(String photoPref) {
        this.photoPref = photoPref;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }
}
