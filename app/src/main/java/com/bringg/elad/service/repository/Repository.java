package com.bringg.elad.service.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.bringg.elad.service.model.WorkingDay;
import com.bringg.elad.service.storage.database.WorkingDaysDatabase;

import java.util.List;

public class Repository {
    final private WorkingDaysDatabase database;

    public Repository(Context context) {
        database = WorkingDaysDatabase.getInstance(context.getApplicationContext());
    }

    public LiveData<List<WorkingDay>> getWorkingDays() {
        return database.getWorkingDays();
    }

    public void insertWorkingDay(WorkingDay workingDay) {
        database.insertWorkingDay(workingDay);
    }

}
