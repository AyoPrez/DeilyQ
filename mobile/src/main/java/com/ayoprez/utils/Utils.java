package com.ayoprez.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.deilyquote.ErrorMessage;
import com.ayoprez.deilyquote.MainActivity;

import de.greenrobot.event.EventBus;

public class Utils {
	private static final String LOG_TAG = Utils.class.getSimpleName();


	public static void Create_Dialog(final Context ctx, String message, String button_title, String dialog_title){

		try {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
			alertDialogBuilder.setTitle(dialog_title);
			alertDialogBuilder.setMessage(message);
			alertDialogBuilder.setPositiveButton(button_title, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(ctx, MainActivity.class);
					ctx.startActivity(i);
					((Activity) ctx).finish();
				}
			});

			alertDialogBuilder.show();
		}catch(Exception e){
			ErrorHandle.getInstance().Error(LOG_TAG, e);
		}
	}
	
	public static void Create_Dialog_DoNotFinishActivity(final Context ctx, String message,
         String button_title, String dialog_title, DialogInterface.OnClickListener onClickListener){

		try {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
			alertDialogBuilder.setTitle(dialog_title);
			alertDialogBuilder.setMessage(message);
			alertDialogBuilder.setPositiveButton(button_title, onClickListener);

			alertDialogBuilder.show();
		}catch(WindowManager.BadTokenException e){
			Log.e("BadToken", e.toString());
			EventBus.getDefault().post(new ErrorMessage(message));
		}catch(Exception e){
			ErrorHandle.getInstance().Error(LOG_TAG, e);
		}
	}

	public static String WithZero(int i){
		if(i < 10 && i >= 0){
			return 0+String.valueOf(i);
		}else{
			return String.valueOf(i);
		}
	}
	
	public static String TakeOutTimeDots(String s){
		int n = s.indexOf(':');
		return s.substring(0, n) + s.substring(n + 1);
	}

    public static String PutInTimeDots(String s) throws Exception{
        if(s.length() == 4) {
            return s.substring(0, 2) + ":" + s.substring(2, 4);
        } else {
            throw new Exception();
        }
    }
	
	public static int TakeHourFromTime(String time){
		String hour = time.substring(0, 2);
		return Integer.valueOf(hour);
	}
	
	public static int TakeMinuteFromTime(String time){
		String minute = time.substring(3, 5);
		return Integer.valueOf(minute);
	}
}