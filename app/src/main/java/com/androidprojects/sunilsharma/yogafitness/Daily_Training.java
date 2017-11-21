package com.androidprojects.sunilsharma.yogafitness;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidprojects.sunilsharma.yogafitness.Database.YogaDB;
import com.androidprojects.sunilsharma.yogafitness.Model.Exercise;
import com.androidprojects.sunilsharma.yogafitness.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


/** This Activity has a Function of Showing The Yoga moves to the practitioner*/
public class Daily_Training extends AppCompatActivity
{

    FButton btnStart;
    ImageView ex_image;
    TextView txtGetReady;
    TextView txtCountdown;
    TextView txtTimer;
    TextView ex_name;
    MaterialProgressBar progressBar;
    LinearLayout layoutGetReady;


    int ex_id = 0;
    int limit_time = 0;

    YogaDB yogaDB;
    List<Exercise> list = new ArrayList<>();

    /** After each exercise we will be 10 minutes break.
     * At the end of the Exercise will show the end of day
     * and save the data to the database*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        yogaDB = new YogaDB(this);

        /**
         * we will get config Difficult from Database and
         * set Time to count down (Easy = 10 Sec , Medium = 20 Sec..)
         * */
        /*if(yogaDB.getSettingMOde() == 0)
            limit_time = Common.TIME_LIMIT_EASY;

        else if(yogaDB.getSettingMOde() == 1)
            limit_time = Common.TIME_LIMIT_MEDIUM;

        else if(yogaDB.getSettingMOde() == 0)
            limit_time = Common.TIME_LIMIT_HARD;*/


        btnStart = (FButton) findViewById(R.id.btnStart);
        ex_image = (ImageView) findViewById(R.id.detail_image);
        txtCountdown = (TextView) findViewById(R.id.txtCountdown);
        txtGetReady = (TextView) findViewById(R.id.txtGetReady);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        ex_name = (TextView) findViewById(R.id.title);

        layoutGetReady = (LinearLayout) findViewById(R.id.layout_get_ready);

        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);



        /** here m going to Set Max Value for ProgressBar is size of List Exercise*/
        progressBar.setMax(list.size());

        /** Calling Method Here*/
        setExerciseInformation(ex_id);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /**
                 * If User Click on Start Button then m going to Show get Ready
                 *  and After 5 Seconds, we will show Exercise
                 *  */
                if(btnStart.getText().toString().toLowerCase().equals("start"))
                {
                    showGetReady();
                    btnStart.setText("Done");
                }

                else if(btnStart.getText().toString().toLowerCase().equals("Done"))
                {
                    /**
                     *  Now Here If User Click Done while Exercise is running ,
                     *  M Just Cancel Count Down and show Rest Time
                     *  */
                    if(yogaDB.getSettingMOde() == 0)
                        exercisesEasyModeCountDown.cancel();

                    else if(yogaDB.getSettingMOde() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if(yogaDB.getSettingMOde() == 2)
                        exercisesHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if(ex_id < list.size())
                    {
                        showRestTime();

                        /** here m going to Set Next Exercise*/
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();
                }
                else
                {
                    if(yogaDB.getSettingMOde() == 0)
                        exercisesEasyModeCountDown.cancel();

                    else if(yogaDB.getSettingMOde() == 1)
                        exercisesMediumModeCountDown.cancel();
                    else if(yogaDB.getSettingMOde() == 2)
                        exercisesHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if(ex_id < list.size())
                        setExerciseInformation(ex_id);
                    else
                        showFinished();


                }
            }
        });
    }

    private void showRestTime()
    {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountDown.start();

        txtGetReady.setText("REST TIME");
    }

    private void showGetReady()
    {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");

        new CountDownTimer(6000 , 1000)
        {

            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished)
            {
                txtCountdown.setText("" + (millisUntilFinished - 1000)/1000);
            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish()
            {
                showExercises();
            }
        }.start();
    }

    private void showExercises()
    {
        if(ex_id < list.size())
        {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if(yogaDB.getSettingMOde() == 0)
                exercisesEasyModeCountDown.start();

            else if(yogaDB.getSettingMOde() == 1)
                exercisesMediumModeCountDown.start();
            else if(yogaDB.getSettingMOde() == 2)
                exercisesHardModeCountDown.start();

            /** Here set name and Image for Exercise*/
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());
        }
        else
            showFinished();
    }

    private void showFinished()
    {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("FINISHED !!!");
        txtCountdown.setText("Congratulation ! \nYou're done with today exercises");
        txtCountdown.setTextSize(20);

        /** After Finished , we have to save this date to Database*/
        yogaDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    /** Countdown*/
    CountDownTimer exercisesEasyModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY , 1000) {
        @Override
        public void onTick(long millisUntilFinished)
        {
            txtTimer.setText(""+(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish()
        {
            if(ex_id < list.size() - 1)
            {
                /**
                 * when Count Down is finish ,
                 * if ex_id still lower list size ,
                 * we just go to next Exercise
                 * */
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else
            {
                showFinished();
            }
        }
    };

    CountDownTimer exercisesMediumModeCountDown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM , 1000) {
        @Override
        public void onTick(long millisUntilFinished)
        {
            txtTimer.setText(""+(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish()
        {
            if(ex_id < list.size() - 1)
            {
                /**
                 * when Count Down is finish ,
                 * if ex_id still lower list size ,
                 * we just go to next Exercise
                 * */
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else
            {
                showFinished();
            }
        }
    };

    CountDownTimer exercisesHardModeCountDown = new CountDownTimer(Common.TIME_LIMIT_HARD , 1000) {
        @Override
        public void onTick(long millisUntilFinished)
        {
            txtTimer.setText(""+(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish()
        {
            if(ex_id < list.size() - 1)
            {
                /**
                 * when Count Down is finish ,
                 * if ex_id still lower list size ,
                 * we just go to next Exercise
                 * */
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else
            {
                showFinished();
            }
        }
    };


    CountDownTimer restTimeCountDown = new CountDownTimer(10000 , 1000) {
        @Override
        public void onTick(long millisUntilFinished)
        {
            txtCountdown.setText(""+(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish()
        {
            /**
             * Here After  a Break of 10 Seconds ,
             * we Start the New Exercise
             * */

            setExerciseInformation(ex_id);
            showExercises();
            /*if(ex_id < list.size())
            {
                *//**
                 * when Count Down is finish ,
                 * if ex_id still lower list size ,
                 * we just go to next Exercise
                 * *//*
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }*/
        }
    };

    /** Write Method set Information for Exercise : Name , Image*/
    private void setExerciseInformation(int id)
    {
        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");


        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }


    private void initData()
    {
        list.add(new Exercise(R.drawable.easy_pose , "Easy Pose") );
        list.add(new Exercise(R.drawable.cobra_pose , "Cobra Pose"));
        list.add(new Exercise(R.drawable.downward_facing_dog , "Downward Facing Dog"));
        list.add(new Exercise(R.drawable.boat_pose , "Boat Pose"));
        list.add(new Exercise(R.drawable.half_pigeon , "Half Pigeon"));
        list.add(new Exercise(R.drawable.low_lunge , "Low Lunge"));
        list.add(new Exercise(R.drawable.upward_bow , "Upward Pose"));
        list.add(new Exercise(R.drawable.crescent_lunge , "Crescent Lunge"));
        list.add(new Exercise(R.drawable.warrior_pose , "Warrior Pose"));
        list.add(new Exercise(R.drawable.bow_pose , "Bow Pose"));
        list.add(new Exercise(R.drawable.warrior_pose_2 , "Warrior Pose 2"));
    }
}
