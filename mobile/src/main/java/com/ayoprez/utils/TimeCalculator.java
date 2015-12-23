package com.ayoprez.utils;

import android.app.AlarmManager;

import java.util.Calendar;

/**
 * Created by Ayoze on 23/02/15.
 */
public class TimeCalculator {

    public long getTimeFromNowUntilUserChoiceTime(String time) throws Exception{

        if(time.length() != 5){
            time = Utils.PutInTimeDots(time);
        }

        int hour = Integer.valueOf(time.substring(0, 2));
        int minutes = Integer.valueOf(time.substring(3, 5));
        long finalTime = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        if(c.getTimeInMillis() <= Calendar.getInstance().getTimeInMillis())
            finalTime = (c.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1)) - System.currentTimeMillis();
        else
            finalTime = c.getTimeInMillis() - System.currentTimeMillis();
        return finalTime;
    }
}
