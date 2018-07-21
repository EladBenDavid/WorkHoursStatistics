package com.bringg.elad.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bringg.elad.R;
import com.bringg.elad.service.model.WorkingDay;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WorkingDayViewHolder extends RecyclerView.ViewHolder{


    private TextView date;
    private TextView duration;

    public WorkingDayViewHolder(View view) {
        super(view);
        date = view.findViewById(R.id.date);
        duration = view.findViewById(R.id.duration);
    }

    public void bind(WorkingDay workingDay) {
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(workingDay.getArrive()));
        date.setText(dateString);
        String durationString = DurationFormatUtils.formatPeriod(workingDay.getArrive() ,workingDay.getLeave(), "H 'Hours' m 'Minutes' s 'Seconds'");
        duration.setText(durationString);
    }

}
