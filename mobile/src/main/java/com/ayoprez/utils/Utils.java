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
import com.ayoprez.savedQuotes.SavedQuotes;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import deilyquote.UserQuotes;

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

	public static ArrayList<SavedQuotes> convertUserQuotesToSavedQuotes(List<UserQuotes> userQuotes){
		ArrayList<SavedQuotes> savedQuotes = new ArrayList<>();

		for (UserQuotes quote : userQuotes){
			savedQuotes.add(new SavedQuotes(quote.getId().intValue(), quote.getQuote(), quote.getAuthor(),
					quote.getPersonality(), quote.getPersonality2(), quote.getPersonality3(),
					quote.getLanguage()));
		}

		return savedQuotes;
	}

	public static ArrayList<UserQuotes> convertSavedQuotesToUserQuotes(List<SavedQuotes> savedQuotes){
		ArrayList<UserQuotes> userQuotes = new ArrayList<>();

		for (SavedQuotes quote : savedQuotes){
			userQuotes.add(new UserQuotes((long)quote.getId_Q(), quote.getLanguage(), quote.getPersonality(),
					quote.getPersonality2(), quote.getPersonality3(), quote.getQuote(),
					quote.getAuthor()));
		}

		return userQuotes;
	}
}