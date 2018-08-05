package com.bringg.elad.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bringg.elad.application.WorkHoursStatisticsApplication;
import com.bringg.elad.service.model.WorkingDay;
import com.bringg.elad.service.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class WorkingDaysViewModel extends AndroidViewModel {

    @Inject
    public Repository mRepository;

    public WorkingDaysViewModel(@NonNull Application application) {
        super(application);
        ((WorkHoursStatisticsApplication)application).getAppComponent().inject(this);
    }

    public LiveData<List<WorkingDay>> getWorkingDays() {
        return mRepository.getWorkingDays();
    }

}
