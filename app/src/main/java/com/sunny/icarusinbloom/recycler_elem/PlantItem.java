package com.sunny.icarusinbloom.recycler_elem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Plants")
public class PlantItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int plantId;
    private int speciesId;
    private int ownerId;

    private String name;
    private String info;
    private String species;
    private String image;
    private String bday;
    private String water_type;
    private int water_interval;
    private String lastWatered;

    public PlantItem(String name, String info, String species, String image, String bday, int ownerId,
                     int speciesId, int water_interval, String water_type,String lastWatered) {
        this.name = name;
        this.info = info;
        this.species = species;
        this.image = image;
        this.bday=bday;
        this.ownerId=ownerId;
        this.speciesId=speciesId;
        this.water_interval=water_interval;
        this.water_type=water_type;
        this.lastWatered=lastWatered;
    }

    public String getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(String lastWatered) {
        this.lastWatered = lastWatered;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getWater_type() {
        return water_type;
    }

    public void setWater_type(String water_type) {
        this.water_type = water_type;
    }

    public int getWater_interval() {
        return water_interval;
    }

    public void setWater_interval(int water_interval) {
        this.water_interval = water_interval;
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
                ", speciesId=" + speciesId +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", species='" + species + '\'' +
                ", image='" + image + '\'' +
                ", bday='" + bday + '\'' +
                ", water_type='" + water_type + '\'' +
                ", water_interval=" + water_interval +
                ", lastWatered='" + lastWatered + '\'' +
                '}';
    }
}
