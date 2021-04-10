package com.sunny.icarusinbloom.recycler;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.sunny.icarusinbloom.login.User;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity(tableName = "Plants",foreignKeys = {
        @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "ownerId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)})
public class PlantItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int plantId;
    private String name;
    private String info;
    private String species;
    private String image;
    private String bday;
    private int ownerId;

    public PlantItem(String name, String info, String species, String image, String bday, int ownerId) {
        this.name = name;
        this.info = info;
        this.species = species;
        this.image = image;
        this.bday=bday;
        this.ownerId=ownerId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "PlantItem{" +
                "plantId=" + plantId +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", species='" + species + '\'' +
                ", image='" + image + '\'' +
                ", bday='" + bday + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
