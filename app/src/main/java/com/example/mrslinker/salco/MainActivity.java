package com.example.mrslinker.salco;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
                    mChronos = new Chronos(mContext);
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
