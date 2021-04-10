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

/*          plantList = new MutableLiveData<>();
            ArrayList<PlantItem> list = new ArrayList<>();
            PlantItem p1 = new PlantItem("Bobby", "null", "rose","null","null",1);
            list.add(p1);
            list.add(p1);
            PlantItem p2 = new PlantItem("Joanne", "Dhsfhsgdjds", "delilah","null","null",1);
            list.add(p2);
            list.add(p2);
            list.add(p2);
            list.add(p2);
            list.add(p2);
            list.add(p2);
            plantList.setValue(list);*/
    }


    public LiveData<List<PlantItem>> getPlantList() {
        return repository.getAllPlants();
        /*      LiveData<List<PlantItem>> list = repository.getAllPlants();
        setPlantList(list.getValue());
        return plantList;*/
    }

    public void addPlant(PlantItem plantItem){
        repository.insert(plantItem);
        /*        List<PlantItem> list = plantList.getValue();
        list.add(plantItem);
        plantList.setValue(list);*/
    }

    public void deletePlant(int position){
       /* ArrayList<PlantItem> list = plantList.getValue();
        list.remove(position);
        plantList.setValue(list);*/
    }

    public LiveData<PlantItem> getPlant(int id){
        return repository.getPlant(id);
    }

   /* public void setPlantList(List<PlantItem> plantList) {
        this.plantList.setValue(plantList);
    }*/

}
