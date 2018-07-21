package com.bringg.elad.repository;

import android.content.Context;

import com.bringg.elad.service.model.WorkingDay;
import com.bringg.elad.service.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class FakeRepository extends Repository {

    public final static List<WorkingDay> dataBase = new ArrayList<>();

    public FakeRepository(Context context) {
        super(context);
    }

    @Override
    public void insertWorkingDay(WorkingDay workingDay){
        dataBase.add(workingDay);
    }
}
