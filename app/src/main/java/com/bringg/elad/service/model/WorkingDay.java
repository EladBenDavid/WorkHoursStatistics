package com.bringg.elad.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "working_days")
public class WorkingDay {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") private Integer mId;
    @ColumnInfo(name = "arrive") private final long mArrive;
    @ColumnInfo(name = "leave") private final long  mLeave;

    public WorkingDay(long arrive, long leave) {
        mArrive = arrive;
        mLeave = leave;
    }

    public Integer getId() {
        return mId;
    }
    public long getArrive() {
        return mArrive;
    }

    public long getLeave() {
        return mLeave;
    }
    public void setId(Integer mId) {
        this.mId = mId;
    }
}
