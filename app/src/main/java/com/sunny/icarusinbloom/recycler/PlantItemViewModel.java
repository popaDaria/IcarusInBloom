package com.sunny.icarusinbloom.recycler;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sunny.icarusinbloom.persistance.PlantRepository;

import java.util.ArrayList;
import java.util.List;

public class PlantItemViewModel extends AndroidViewModel {

    private final PlantRepository repository;
    //private MutableLiveData<List<PlantItem>> plantList ;

    public PlantItemViewModel(Application application){
            super(application);
            repository = PlantRepository.getInstance(application);
    }


    public LiveData<List<PlantItem>> getPlantList() {
        return repository.getAllPlants();
    }

    public void addPlant(PlantItem plantItem){
        repository.insert(plantItem);
    }

    public void deletePlant(PlantItem item){
        repository.delete(item);
    }

    public LiveData<PlantItem> getPlant(int id){
        return repository.getPlant(id);
    }
    public LiveData<List<PlantItem>> getAllUserPlant(int userId){
        return repository.getAllUserPlant(userId);
    }

    public LiveData<List<PlantItem>> getAllPlantsByWatered(int userId){
        return repository.getAllPlantsByWatered(userId);
    }

    public void update(PlantItem item){
        repository.update(item);
    }

}
