package com.ayoprez.deilyquote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayoprez.login.SessionManager;
import com.ayoprez.utils.Keys;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AyoPrez on 23/08/15.
 */
public abstract class AbstractBaseMainActivity extends AppCompatActivity{

    //TODO probar que funciona correctamente
    protected String DeviceLanguageISO = new DetectDeviceLanguage().getISO3Language();
    protected Locale DeviceLocale = new DetectDeviceLanguage().getLocale(DeviceLanguageISO);
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                Keys.TWITTER_KEY + getString(R.string.TW_K),
                Keys.TWITTER_SECRET + getString(R.string.TW_S));
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));

        this.sessionManager = new SessionManager(this);
    }

    protected int getUserId(){
        if(sessionManager != null){
            return Integer.getInteger(sessionManager.getUserDetails().get("id"));
        }else{
            return 0;
        }
    }

    protected String getUserName(){
        if(sessionManager.isLoggedIn()){
            return sessionManager.getUserDetails().get("name");
        }else{
            return null;
        }
    }

    protected void goToNewScreen(Class destinyClass){
        Intent i = new Intent(this, destinyClass);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
