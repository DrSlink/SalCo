package com.example.mrslinker.salco;

import android.content.Context;

/**
 * Created by Mr.Slinker on 11.03.2018.
 */

public class Chronos implements Runnable {

    private static final long MILLIS_TO_MINUTES = 60000;
    private static final long MILLS_TO_HOURS = 3600000;
    public static final long SALARY_PER_HOUR = 300;

    private Context mContext;
    private long mStartTime;
    private boolean mIsRunning;
    private long mSumData;
    private long mOldSumData;

    public Chronos(Context context) {
        mContext = context;
        mSumData = 0;
        mOldSumData = 0;
    }


    public Chronos(Context context, long startTime) {
        this(context);
        mStartTime = startTime;
    }

    public void start() {
        mStartTime = System.currentTimeMillis();
        mIsRunning = true;
    }

    public void stop() {
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public long getStartTime() {
        return mStartTime;
    }

    @Override
    public void run() {
        while (mIsRunning) {

            long since = System.currentTimeMillis() - mStartTime;
            mSumData = mOldSumData + since;

            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLS_TO_HOURS)));
            int millis = (int) since % 1000;

            int cents = (int) ((since * 300 * 100 / MILLS_TO_HOURS));
            int dollars = cents / 100;
            int thousands = dollars / 1000;

            cents = cents % 100;
            dollars = dollars % 1000;

            int sumSeconds = (int) (mSumData / 1000) % 60;
            int sumMinutes = (int) ((mSumData / (MILLIS_TO_MINUTES)) % 60);
            int sumHours = (int) ((mSumData / (MILLS_TO_HOURS)));
            /*int sumMillis = (int) mSumData % 1000;*/

            int sumCents = (int) ((mSumData * 300 * 100 / MILLS_TO_HOURS));
            int sumDollars = sumCents / 100;
            int sumThousands = sumDollars / 1000;

            sumCents = sumCents % 100;
            sumDollars = sumDollars % 1000;


            ((MainActivity) mContext).updateTimerText(String.format("%02dч %02dм %02dс %03dмс"
                    , hours, minutes, seconds, millis));

            ((MainActivity) mContext).updateSalaryText(String.format("%03d,%03dp %02dк"
                    , thousands, dollars, cents));

            ((MainActivity) mContext).updateSumTimerText(String.format("%03dч %02dм %02dс"
                    , sumHours, sumMinutes, sumSeconds));

            ((MainActivity) mContext).updateSumSalaryText(String.format("%03d,%03dp %02dк"
                    , sumThousands, sumDollars, sumCents));

            //Sleep the thread for a short amount, to prevent high CPU usage!
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mOldSumData = mSumData;
    }
}
