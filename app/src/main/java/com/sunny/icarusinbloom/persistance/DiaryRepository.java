package com.sunny.icarusinbloom.persistance;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sunny.icarusinbloom.recycler_elem.DiaryItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiaryRepository {

    private static DiaryRepository instance;
    private final DiaryDao diaryDao;
    private final ExecutorService executorService;

    private DiaryRepository(Application application){
        DiaryDB db = DiaryDB.getInstance(application);
        diaryDao = db.diaryDAO();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized DiaryRepository getInstance(Application application){
        if(instance==null)
            instance= new DiaryRepository(application);
        return instance;
    }

    public void insert(DiaryItem item){
        executorService.execute(()->diaryDao.insert(item));
    }


   public void update(DiaryItem item){
        executorService.execute(()->diaryDao.update(item));
    }


    public void delete(DiaryItem item){
        executorService.execute(()->diaryDao.delete(item));
    }

    public LiveData<List<DiaryItem>> getAllDiaryEntries(){
        return diaryDao.getAllDiaryEntries();
    }

    public LiveData<List<DiaryItem>> getAllDiaryEntriesForUSer(int userId){
        return diaryDao.getAllDiaryEntriesForUSer(userId);
    }

}
