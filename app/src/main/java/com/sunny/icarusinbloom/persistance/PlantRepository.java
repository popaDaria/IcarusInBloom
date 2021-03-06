package com.sunny.icarusinbloom.persistance;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.recycler_elem.PlantItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlantRepository {
    private static PlantRepository instance;
    private final PlantDao plantDao;
    private final LiveData<List<PlantItem>> allPlants;
    private final ExecutorService executorService;

    private PlantRepository(Application application){
        PlantDB db = PlantDB.getInstance(application);
        plantDao = db.plantDao();
        allPlants = plantDao.getAllPlants();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized PlantRepository getInstance(Application application){
        if(instance==null)
            instance= new PlantRepository(application);
        return instance;
    }

    public void insert(PlantItem item){
        executorService.execute(()-> {
            plantDao.insert(item);
        });
    }

    public LiveData<List<PlantItem>> getAllPlants(){
       return allPlants;
    }
    public LiveData<List<PlantItem>> getAllPlantsByWatered(int userId){
        return plantDao.getAllPlantsByWatered(userId);
    }

    public LiveData<PlantItem> getPlant(int id){
        return plantDao.getPlant(id);
    }

    public LiveData<List<PlantItem>> getAllUserPlant(int userId){
        return plantDao.getAllUserPlant(userId);
    }

    public void delete(PlantItem item){
        executorService.execute(()->{
            plantDao.delete(item);
        });
    }

    public void update(PlantItem item){
        executorService.execute(()->plantDao.update(item));
    }

}
