package com.androidprojects.sunilsharma.yogafitness;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidprojects.sunilsharma.yogafitness.Database.YogaDB;

import java.util.Calendar;
import java.util.Date;

public class SettingPage extends AppCompatActivity
{
    FButton btnSave;
    RadioButton radioEasy;
    RadioButton radioMedium;
    RadioButton radioHard;
    RadioGroup radioGroup;
    YogaDB yogaDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        btnSave = (FButton) findViewById(R.id.btnSave);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioEasy = (RadioButton) findViewById(R.id.radioEasy);
        radioMedium = (RadioButton) findViewById(R.id.radioMedium);
        radioHard = (RadioButton) findViewById(R.id.radioHard);

        switchAlarm = (ToggleButton) findViewById(R.id.switchAlarm);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        yogaDB = new YogaDB(this);

        /**
         *  Now Here we have to get The Data Form the DB
         * and set radio button base on Data
         * */
        int mode = yogaDB.getSettingMOde();
        setRadioButton(mode);

        /** now m going to set The Eevent's*/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                /** now we have to Save the data to the Database*/
                saveWorkoutMode();

                /** Here m Going to Call this Method for Alarm Setting*/
                saveAlarm(switchAlarm.isChecked());



                Toast.makeText(SettingPage.this, "SAVED !!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }


    /** This Function is for Alarm Setting*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveAlarm(boolean checked)
    {
        if(checked)
        {
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent = new Intent(SettingPage.this , AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this , 0 , intent , 0);


            /** Here m Going to Set the Time To Alarm*/
            Calendar calendar = Calendar.getInstance();
            Date toDay = Calendar.getInstance().getTime();
            calendar.set(toDay.getYear() , toDay.getMonth() , toDay.getDay() ,
                    timePicker.getCurrentHour() , timePicker.getCurrentMinute());

            manager.setRepeating(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis() ,
                    AlarmManager.INTERVAL_DAY , pendingIntent);

            Log.d("DEBUG" , "Alarm will wake at: "
                    + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
        }
        else
        {
            /** Here m can Cancel Alarm*/
           Intent intent = new Intent(SettingPage.this , AlarmNotificationReceiver.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(this , 0 ,
                   intent , 0);

            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

            manager.cancel(pendingIntent);
        }
    }

    private void saveWorkoutMode()
    {
        int selectedID = radioGroup.getCheckedRadioButtonId();

        if(selectedID == radioEasy.getId())
            yogaDB.saveSettingMode(0);

        else if(selectedID == radioMedium.getId())
            yogaDB.saveSettingMode(1);

        else if(selectedID == radioHard.getId())
            yogaDB.saveSettingMode(2);
    }

    private void setRadioButton(int mode)
    {
        if(mode == 0)
            radioGroup.check(R.id.radioEasy);

        else if (mode == 1)
            radioGroup.check(R.id.radioMedium);

        else if(mode == 2)
            radioGroup.check(R.id.radioHard);
    }
}
