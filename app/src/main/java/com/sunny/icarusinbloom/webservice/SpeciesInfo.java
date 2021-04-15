package com.sunny.icarusinbloom.webservice;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Species")
public class SpeciesInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int speciesId;
    private String scientificName;
    private boolean isEdible;
    private String growthHabit;
    private String growthRate;
    private int soilLevel;
    private int minimumPe;
    private int maximumPe;
    private int minimumTemp;

    public SpeciesInfo(int speciesId, String scientificName, boolean isEdible, String growthHabit, String growthRate, int soilLevel, int minimumPe, int maximumPe, int minimumTemp) {
        this.speciesId = speciesId;
        this.scientificName = scientificName;
        this.isEdible = isEdible;
        this.growthHabit = growthHabit;
        this.growthRate = growthRate;
        this.soilLevel = soilLevel;
        this.minimumPe = minimumPe;
        this.maximumPe = maximumPe;
        this.minimumTemp = minimumTemp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public void setEdible(boolean edible) {
        isEdible = edible;
    }

    public String getGrowthHabit() {
        return growthHabit;
    }

    public void setGrowthHabit(String growthHabit) {
        this.growthHabit = growthHabit;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public int getSoilLevel() {
        return soilLevel;
    }

    public void setSoilLevel(int soilLevel) {
        this.soilLevel = soilLevel;
    }

    public int getMinimumPe() {
        return minimumPe;
    }

    public void setMinimumPe(int minimumPe) {
        this.minimumPe = minimumPe;
    }

    public int getMaximumPe() {
        return maximumPe;
    }

    public void setMaximumPe(int maximumPe) {
        this.maximumPe = maximumPe;
    }

    public int getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(int minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    @Override
    public String toString() {
        return "SpeciesInfo{" +
                "speciesId=" + speciesId +
                ", scientificName='" + scientificName + '\'' +
                ", isEdible=" + isEdible +
                ", growthHabit='" + growthHabit + '\'' +
                ", growthRate='" + growthRate + '\'' +
                ", soilLevel=" + soilLevel +
                ", minimumPe=" + minimumPe +
                ", maximumPe=" + maximumPe +
                ", minimumTemp=" + minimumTemp +
                '}';
    }
}
