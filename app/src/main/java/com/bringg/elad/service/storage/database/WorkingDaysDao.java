package com.bringg.elad.service.storage.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.bringg.elad.service.model.WorkingDay;

import java.util.List;

/**
 * Data Access Object for the working days table.
 */
@Dao
public interface WorkingDaysDao {

    /**
     * Get the working days from the table.
     *
     * @return the working days from the table
     */
    @Query("SELECT * FROM working_days")
    LiveData<List<WorkingDay>> getWorkingDays();

    /**
     * Insert a working day in the database. If exists, replace it.
     *
     * @param workingDay the working day to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkingDay(WorkingDay workingDay);
}
