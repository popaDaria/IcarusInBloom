package com.sunny.icarusinbloom.persistance;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeciesRepository {
    private static SpeciesRepository instance;
    private final SpeciesDao speciesDAO;
    private final ExecutorService executorService;

    private SpeciesRepository(Application application){
        SpeciesDB db = SpeciesDB.getInstance(application);
        speciesDAO = db.plantDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized SpeciesRepository getInstance(Application application){
        if(instance==null)
            instance= new SpeciesRepository(application);
        return instance;
    }

    public void insert(SpeciesInfo item){
        executorService.execute(()->speciesDAO.insert(item));
    }

    public void update(SpeciesInfo item){
        executorService.execute(()->speciesDAO.update(item));
    }

    public void delete(SpeciesInfo item){
        executorService.execute(()->speciesDAO.delete(item));
    }

    public LiveData<List<SpeciesInfo>> getAllSpecies(){
        return speciesDAO.getAllSpecies();
    }

    public LiveData<SpeciesInfo> getSpeciesById(int id){
        return speciesDAO.getSpeciesById(id);
    }

}
