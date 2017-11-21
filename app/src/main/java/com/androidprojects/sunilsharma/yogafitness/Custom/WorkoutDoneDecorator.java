package com.androidprojects.sunilsharma.yogafitness.Custom;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

/**
 * Created by sunil sharma on 11/21/2017.
 */

public class WorkoutDoneDecorator implements DayViewDecorator
{

    HashSet<CalendarDay> list;
    ColorDrawable colorDrawable;


    public WorkoutDoneDecorator(HashSet<CalendarDay> list)
    {
        this.list = list;
        colorDrawable = new ColorDrawable(Color.parseColor("#e57373"));
    }

    /**
     * Determine if a specific day should be decorated
     *
     * @param day {@linkplain CalendarDay} to possibly decorate
     * @return true if this decorator should be applied to the provided day
     */
    @Override
    public boolean shouldDecorate(CalendarDay day)
    {
        return list.contains(day);
    }

    /**
     * Set decoration options onto a facade to be applied to all relevant days
     *
     * @param view View to decorate
     */
    @Override
    public void decorate(DayViewFacade view)
    {
        view.setBackgroundDrawable(colorDrawable);
    }
}
