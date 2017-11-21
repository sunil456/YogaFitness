package com.androidprojects.sunilsharma.yogafitness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidprojects.sunilsharma.yogafitness.Custom.WorkoutDoneDecorator;
import com.androidprojects.sunilsharma.yogafitness.Database.YogaDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;
import java.util.List;


/** This Activity will Display the Calendar
 * and
 * what day of the month you Completed the Exercise*/

public class Calendar extends AppCompatActivity
{
    MaterialCalendarView materialCalendarView;
    HashSet<CalendarDay> list = new HashSet<>();

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        yogaDB = new YogaDB(this);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar);

        /**
         * Now I have to just Get all Workout Day From Database
         * and Convert it to HashSet ,
         * Then ,
         * Display The Decorator on The Days included in HashSet
         * */
        List<String> workoutDay = yogaDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList = new HashSet<>();

        for(String value : workoutDay)
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));

        materialCalendarView.addDecorator(new WorkoutDoneDecorator(convertedList));
    }
}
