package com.shpak.newyear;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView timerResult;
    private ImageView lights;
    private static final String TAG = "MainActivity";
    private AdView mAdView;
    int intAlpha = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        lights = findViewById(R.id.lights);

        timerResult = findViewById(R.id.timerResult);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "18450.ttf");
        timerResult.setTypeface(typeface);
        startNewYearTimer();

    }

    private void startNewYearTimer() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        formatter.setLenient(false);

        String newYearTime = "01.01." + (Calendar.getInstance().get(Calendar.YEAR) + 1) + ", 00:00:00";
        long milliseconds = 0;

        final CountDownTimer mCountDownTimer;

        Date endDate;
        try {
            endDate = formatter.parse(newYearTime);
            milliseconds = endDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        final long[] startTime = {System.currentTimeMillis()};

        mCountDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String curLang = Locale.getDefault().getLanguage();
                String finalTime;
                if (curLang.equals("ru") || curLang.equals("ua") || curLang.equals("by")) {
                    finalTime = "До Нового Года:\nосталось\n";
                }
                else {
                    //finalTime = "Until the\nNew Year\n" + (Calendar.getInstance().get(Calendar.YEAR) + 1) + " left:\n";
                    finalTime = "Until the\nNew Year left:\n";
                }
                startTime[0] = startTime[0] - 1;
                Long serverUptimeSeconds = (millisUntilFinished - startTime[0]) / 1000;
                if (String.format("%d", serverUptimeSeconds / 86400).length() == 1) finalTime += "0";
                finalTime += String.format("%d", serverUptimeSeconds / 86400) + ":";
                if (String.format("%d", (serverUptimeSeconds % 86400) / 3600).length() == 1) finalTime += "0";
                finalTime += String.format("%d", (serverUptimeSeconds % 86400) / 3600) + ":";
                if (String.format("%d", ((serverUptimeSeconds % 86400) % 3600) / 60).length() == 1) finalTime += "0";
                finalTime += String.format("%d", ((serverUptimeSeconds % 86400) % 3600) / 60) + ":";
                if (String.format("%d", ((serverUptimeSeconds % 86400) % 3600) % 60).length() == 1) finalTime += "0";
                finalTime += String.format("%d", ((serverUptimeSeconds % 86400) % 3600) % 60);
                timerResult.setText(finalTime);

                //lights.setAlpha(Math.abs(lights.getAlpha()-1.0f));
            }

            @Override
            public void onFinish() {
                //DO NOTHING
            }
        }.start();


    }
}

