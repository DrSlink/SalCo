package com.example.mrslinker.salco;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView TVTimeCount;
    private TextView TVSalaryCount;
    private TextView TVTimeCountS;
    private TextView TVSalaryCountS;
    private Button BStartWork;

    private Context mContext;
    private Chronos mChronos;
    private Thread mThreadChrono;
    private static final String SUM_DATA = "SUM";
    private static final String IS_RUNNING = "RUNNING";
    private static final String START_TIME = "TIME";
    private static boolean was_started = false;
    private static long sum_data = 0;


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mChronos != null) {
            was_started = true;
            outState.putLong(SUM_DATA, mChronos.getOldSumData());
            outState.putLong(START_TIME, mChronos.getStartTime());
            outState.putBoolean(IS_RUNNING, mChronos.isRunning());
            Log.d("salco", mChronos.getStartTime() + "");
        }
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (was_started) {
            sum_data = savedInstanceState.getLong(SUM_DATA);
            mChronos = new Chronos(mContext, sum_data);
            mThreadChrono = new Thread(mChronos);
            if (savedInstanceState.getBoolean(IS_RUNNING)) {
                mThreadChrono.start();
                mChronos.start(savedInstanceState.getLong(START_TIME));
                updateButtonText("  END WORKING  ");
            } else {
                mChronos.updateUI();
            }
            was_started = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_menu));

        mContext = this;

        TVTimeCount = findViewById(R.id.time_counter);
        TVSalaryCount = findViewById(R.id.salary_counter);
        TVTimeCountS = findViewById(R.id.time_counter_sum);
        TVSalaryCountS = findViewById(R.id.salary_counter_sum);
        BStartWork = findViewById(R.id.Start_Working);

        BStartWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChronos == null) {
                    mChronos = new Chronos(mContext, sum_data);
                    mThreadChrono = new Thread(mChronos);
                    mThreadChrono.start();
                    mChronos.start();
                    updateButtonText("  END WORKING  ");
                } else {
                    if (mChronos.isRunning()) {
                        mChronos.stop();
                        mThreadChrono.interrupt();
                        mThreadChrono = null;
                        updateButtonText("  START WORKING  ");
                    } else if (!mChronos.isRunning()) {
                        mThreadChrono = new Thread(mChronos);
                        mThreadChrono.start();
                        mChronos.start();
                        updateButtonText("  END WORKING  ");
                    }
                }
            }
        });
    }

    public void updateButtonText(final String buttonText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BStartWork.setText(buttonText);
            }
        });

    }

    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TVTimeCount.setText(timeAsText);
            }
        });

    }

    public void updateSalaryText(final String salaryAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TVSalaryCount.setText(salaryAsText);
            }
        });

    }

    public void updateSumTimerText(final String timeSumAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TVTimeCountS.setText(timeSumAsText);
            }
        });

    }

    public void updateSumSalaryText(final String salarySumAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TVSalaryCountS.setText(salarySumAsText);
            }
        });

    }


}
