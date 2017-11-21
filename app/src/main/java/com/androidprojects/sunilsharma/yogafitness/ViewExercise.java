package com.androidprojects.sunilsharma.yogafitness;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.sunilsharma.yogafitness.Database.YogaDB;
import com.androidprojects.sunilsharma.yogafitness.Utils.Common;

public class ViewExercise extends AppCompatActivity
{
    int image_id;
    String name;

    TextView title;
    TextView timer;
    ImageView detail_image;
    FButton btnStart;

    YogaDB yogaDB;


    /**
     *  Because button Start have to states: START AND STOP ,
     * So we need check state of this button
     * */
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);


        yogaDB = new YogaDB(this);

        timer = (TextView) findViewById(R.id.timer);
        title = (TextView) findViewById(R.id.title);
        detail_image = (ImageView) findViewById(R.id.detail_image);
        btnStart = (FButton) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                 /** Now here At button Start ,
                  * M going to Start CountDownTimer to Count down from 20 Seconds
                  * (This is Limit time to Training)
                  * */

                 if(!isRunning)
                 {
                     btnStart.setText("DONE");

                     int timeLimit = 0;

                     if(yogaDB.getSettingMOde() == 0)
                         timeLimit = Common.TIME_LIMIT_EASY;

                     else if(yogaDB.getSettingMOde() == 1)
                         timeLimit = Common.TIME_LIMIT_MEDIUM;

                     else if(yogaDB.getSettingMOde() == 2)
                         timeLimit = Common.TIME_LIMIT_HARD;

                     new CountDownTimer(timeLimit, 1000) {
                         /**
                          * Callback fired on regular interval.
                          *
                          * @param millisUntilFinished The amount of time until finished.
                          */
                         @Override
                         public void onTick(long millisUntilFinished)
                         {
                             timer.setText(""+millisUntilFinished/1000);
                         }

                         /**
                          * Callback fired when the time is up.
                          */
                         @Override
                         public void onFinish()
                         {
                             Toast.makeText(ViewExercise.this,
                                     "FINISH!!!!",
                                     Toast.LENGTH_SHORT).show();

                             finish();
                         }
                     }.start();
                 }
                 else
                 {
                     Toast.makeText(ViewExercise.this,
                             "FINISH!!!!",
                             Toast.LENGTH_SHORT).show();

                     finish();
                 }

                 isRunning = !isRunning;
            }
        });

        timer.setText("");

        if(getIntent() != null)
        {
            image_id = getIntent().getIntExtra("image_id", -1);
            name = getIntent().getStringExtra("name");

            detail_image.setImageResource(image_id);
            title.setText(name);
        }
    }
}
