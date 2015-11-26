
package com.ayoprez.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.deilyquote.R;
import com.ayoprez.restfulservice.QuoteGet;

public class AlarmReceiver extends BroadcastReceiver{

    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();
    public Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        try {
            getQuote(intent);
        } catch (Exception e) {
            ErrorHandle.getInstance().informUser(context, context.getString(R.string.errorDefault));
            ErrorHandle.getInstance().Error(LOG_TAG, e.toString());
        }
    }

    private void getQuote(Intent intent)throws Exception{
        String personality = PersonalityTranslation.getInstance().translatePersonality(intent.getStringExtra("personality"));
        String language = LanguageTranslation.getInstance().translateLanguage(intent.getStringExtra("language"));
        int id_u = intent.getIntExtra("id_u", 0);

        if(personality != null){
            if(personality.equals("all")) {
                new QuoteGet().getQuoteWithoutPersonality(context, language, id_u);
            }else {
                new QuoteGet().getQuoteWithPersonality(context, personality, language, id_u);
            }
        }
    }
}