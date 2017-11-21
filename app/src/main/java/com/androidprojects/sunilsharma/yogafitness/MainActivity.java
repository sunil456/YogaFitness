package com.androidprojects.sunilsharma.yogafitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    FButton btnExercise;
    FButton btnSetting;
    FButton btnCalendar;

    ImageView btnTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExercise = (FButton) findViewById(R.id.btnExercises);
        btnSetting = (FButton) findViewById(R.id.btnSetting);
        btnTraining = (ImageView) findViewById(R.id.btnTraining);
        btnCalendar = (FButton) findViewById(R.id.btnCalendar);

        /** Here we have to add OnClickListener into btnExercise*/
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , ListExercises.class);
                startActivity(intent);
            }
        });

        /** Here we have to add OnClickListener into btnSetting*/
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , SettingPage.class);
                startActivity(intent);
            }
        });


        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , Calendar.class);
                startActivity(intent);
            }
        });


        /** Now Here M  Adding Event Click to
         * 'PLAY' Icon at Main Screen
         *  and we will start Daily Training
         *  */
        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , Daily_Training.class);
                startActivity(intent);
            }
        });


    }
}
