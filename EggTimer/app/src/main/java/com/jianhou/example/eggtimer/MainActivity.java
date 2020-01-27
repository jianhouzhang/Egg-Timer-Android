package com.jianhou.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;

    SeekBar timerSeekBar;

    Boolean counterIsActive = false;

    Button startButton;

    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        startButton.setText("Start!");
        counterIsActive = false;
    }

    public void buttonClicked(View view){
        Log.i("button pressed", "Nice");
        if (counterIsActive){
            resetTimer();

        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            startButton.setText("Stop!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    Log.i("finished", "timer all done");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.watchalarm);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }

    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String minuteString = Integer.toString(minutes);

        if (minutes <= 9 ){
            minuteString = "0" + minuteString;
        }

        String secondString = Integer.toString(seconds);

        if (seconds <= 9 ){
            secondString = "0" + secondString;
        } // show 0:0 as 00:00
        timerTextView.setText(minuteString+":"+secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar =  findViewById(R.id.timerSeekBar);
        timerTextView =  findViewById(R.id.countdownTextView);
        startButton = findViewById(R.id.startButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
