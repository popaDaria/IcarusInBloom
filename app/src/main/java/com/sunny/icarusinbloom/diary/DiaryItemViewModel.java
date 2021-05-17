package com.sunny.icarusinbloom.diary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.persistance.DiaryRepository;
import com.sunny.icarusinbloom.persistance.PlantRepository;

import java.util.List;

public class DiaryItemViewModel extends AndroidViewModel {

    private final DiaryRepository repository;

    public DiaryItemViewModel(@NonNull Application application) {
        super(application);
        repository = DiaryRepository.getInstance(application);
    }

    public void insert(DiaryItem item){
        repository.insert(item);
    }


    public void update(DiaryItem item){
        repository.update(item);
    }


    public void delete(DiaryItem item){
        repository.delete(item);
    }

    public LiveData<List<DiaryItem>> getAllDiaryEntries(){
        return repository.getAllDiaryEntries();
    }

    public LiveData<List<DiaryItem>> getAllDiaryEntriesForUSer(int userId){
        return repository.getAllDiaryEntriesForUSer(userId);
    }
}
