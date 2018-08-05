package com.bringg.elad.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bringg.elad.R;
import com.bringg.elad.ui.adapter.WorkingDaysPageListAdapter;
import com.bringg.elad.ui.viewmodel.WorkingDaysViewModel;

import java.io.IOException;

public class WorkingDaysFragment extends Fragment {

    protected WorkingDaysViewModel viewModel;
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_working_days, container, false);
        recyclerView = view.findViewById(R.id.workingDaysRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(WorkingDaysViewModel.class);
        observersRegisters();
        return view;
    }
    // Every time the database will update we get call and update the list.
    //---------------------------------------------------------------------
    private void observersRegisters() {
        final WorkingDaysPageListAdapter pageListAdapter = new WorkingDaysPageListAdapter();
        viewModel.getWorkingDays().observe(this, workingDays -> pageListAdapter.setList(workingDays));
        recyclerView.setAdapter(pageListAdapter);
    }
}
