package com.ayoprez.deilyquote;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.CustomEvent;

/**
 * Created by ayo on 23.12.15.
 */
public class AnswerHandle {

    public static void Answer(String event){
        Crashlytics.getInstance().answers.logCustom(new CustomEvent(event));
    }

    public static void Answer(String event, String key, String attribute){
        Crashlytics.getInstance().answers.logCustom(new CustomEvent(event).putCustomAttribute(key, attribute));
    }
}
