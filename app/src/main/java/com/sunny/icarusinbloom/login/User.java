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
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String bday;
    private String photoPref;
    private String mailPref;

    public User(String firstName, String lastName, String password, String email, String bday, String photoPref, String mailPref) {
        this.firstName = firstName;
        this.lastName = lastName;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }
}
