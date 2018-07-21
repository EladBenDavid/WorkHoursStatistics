package com.bringg.elad.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.bringg.elad.application.WorkHoursStatisticsApplication;
import com.bringg.elad.service.model.WorkingDay;
import com.bringg.elad.service.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class WorkingDaysViewModel extends ViewModel {

    @Inject
    public Repository mRepository;

    public void initRepository(Context context){
        ((WorkHoursStatisticsApplication)context.getApplicationContext()).getAppComponent().inject(this);
    }
    public LiveData<List<WorkingDay>> getWorkingDays() {
        return mRepository.getWorkingDays();
    }

}
