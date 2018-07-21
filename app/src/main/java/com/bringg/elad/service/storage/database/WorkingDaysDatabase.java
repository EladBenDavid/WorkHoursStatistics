package com.bringg.elad.service.storage.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bringg.elad.service.model.WorkingDay;

import java.util.List;


/**
 * The Room database that contains the Users table
 */
@Database(entities = {WorkingDay.class}, version = 1)
public abstract class WorkingDaysDatabase extends RoomDatabase {
    private static final String TAG = WorkingDaysDatabase.class.getSimpleName();
    private static final String DATA_BASE_NAME = "WorkingDays.db";
    private static WorkingDaysDatabase instance;
    public abstract WorkingDaysDao workingDaysDao();
    private static final Object sLock = new Object();
    LiveData<List<WorkingDay>> workingDaysPages;
    public static WorkingDaysDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        WorkingDaysDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        workingDaysPages = workingDaysDao().getWorkingDays();
    }


    public void insertWorkingDay(WorkingDay workingDay) {
        workingDaysDao().insertWorkingDay(workingDay);
    }

    public LiveData<List<WorkingDay>> getWorkingDays() {
        return workingDaysPages;
    }
}
