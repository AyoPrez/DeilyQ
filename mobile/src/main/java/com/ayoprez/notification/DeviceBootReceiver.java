package com.ayoprez.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ayoprez.database.UserMomentsRepository;
import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.utils.Tests;

import junit.framework.Test;

import java.util.List;

import deilyquote.UserMoments;

public class DeviceBootReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = DeviceBootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
           reSchedule(context);
        }else{
            Log.e("", "It is not a reboot intent");
            ErrorHandle.getInstance().Error(LOG_TAG, new Exception("It is not a reboot intent"));
        }
    }

    public void reSchedule(Context context){

        List<UserMoments> dataFromDatabase = UserMomentsRepository.getAllMoments(context);
        try{
            for(UserMoments userMoments : dataFromDatabase){
                new StartAndCancelAlarmManager(context, userMoments).startAlarmManager(userMoments.getTime());
            }
        }catch(Exception e){
            new Tests().testNotificationError(context, e.toString());
            ErrorHandle.getInstance().Error(LOG_TAG, e);
        }
    }
}