package com.bringg.elad.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bringg.elad.R;
import com.bringg.elad.service.model.WorkingDay;
import com.bringg.elad.ui.view.viewholder.WorkingDayViewHolder;

import java.util.List;

/**
 * Created by Elad on 7/20/2018.
 */

public class WorkingDaysPageListAdapter extends RecyclerView.Adapter<WorkingDayViewHolder> {

    private static final String TAG = WorkingDaysPageListAdapter.class.getSimpleName();
    private List<WorkingDay> mWorkingDays;

    public void setList(List<WorkingDay> workingDays){
        mWorkingDays = workingDays;
        notifyDataSetChanged();
    }

    @Override
    public WorkingDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.woking_day_item, parent, false);
        WorkingDayViewHolder viewHolder = new WorkingDayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WorkingDayViewHolder holder, int position) {
        holder.bind(mWorkingDays.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.woking_day_item;
    }

    @Override
    public int getItemCount() {
        if(mWorkingDays != null) {
            return mWorkingDays.size();
        }
        return 0;
    }
}
