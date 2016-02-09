package com.ayoprez.utils;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by AyoPrez on 23/08/15.
 */
public class SpeakerHelper implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private Locale local;
    private boolean ready = false;
    private boolean allowed = false;

    public SpeakerHelper(Context context, Locale locale){
        this.tts = new TextToSpeech(context, this);
        this.local = locale;
    }

    public boolean isAllowed(){
        return allowed;
    }

    public void allow(boolean allowed){
        this.allowed = allowed;
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            tts.setLanguage(local);
            ready = true;
            EventBus.getDefault().post(ready);

        }else{
            ready = false;
            EventBus.getDefault().post(ready);
        }
    }

    public void speak(final String text){
        if(ready && allowed) {
            if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }else{
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }

    public void destroy(){
        tts.shutdown();
    }
}