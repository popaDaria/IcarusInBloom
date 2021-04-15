package com.sunny.icarusinbloom.recycler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.persistance.SpeciesRepository;
import com.sunny.icarusinbloom.webservice.SpeciesInfo;

import java.util.List;

public class SpeciesViewModel extends AndroidViewModel {
    private final SpeciesRepository repository;
    public SpeciesViewModel(@NonNull Application application) {
        super(application);
        repository = SpeciesRepository.getInstance(application);
    }

    public void insert(SpeciesInfo item){
        repository.insert(item);
    }

    public void update(SpeciesInfo item){
        repository.update(item);
    }

    public void delete(SpeciesInfo item){
        repository.delete(item);
    }

    public LiveData<List<SpeciesInfo>> getAllSpecies(){
        return repository.getAllSpecies();
    }

    public LiveData<List<SpeciesInfo>> getSpeciesById(int id){
        return repository.getSpeciesById(id);
    }
}
