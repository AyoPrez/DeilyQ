package com.ayoprez.utils;

import android.content.Context;
import android.util.Log;

import com.ayoprez.notification.LaunchNotification;

import deilyquote.UserQuotes;

/**
 * Created by root on 12/09/15.
 */
public class Tests {
    public void testNotification(Context context){
        Long id = new Long(1);
        String quote = "La gente piensa que concentrarse significa decir sí a aquello en lo que te concentras, pero no es así. Significa decir no a otras cientos de ideas buenas que hay.";
        String author = "Steve Jobs";
        String language = "spanish";
        String personality = "Emprendedor";

        try {
            new LaunchNotification(context).launchNotification(context, new UserQuotes(id, language, personality, null, null, quote, author));
        } catch (Exception e) {
            Log.e("DeilyQuoteError", "NotificationButton  " + e);
            e.printStackTrace();
        }
    }

    public void testNotificationError(Context context, String error){
        Long id = new Long(1);
        String quote = "Error: " + error;
        String author = "Error";
        String language = "spanish";
        String personality = "Emprendedor";

        try {
            new LaunchNotification(context).launchNotification(context, new UserQuotes(id, language, personality, null, null, quote, author));
        } catch (Exception e) {
            Log.e("DeilyQuoteError", "NotificationButton  " + e);
            e.printStackTrace();
        }
    }
}
