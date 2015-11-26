package com.ayoprez.deilyquote;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayoprez.login.SessionManager;
import com.ayoprez.restfulservice.QuoteSet;
import com.ayoprez.utils.SpeakerHelper;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class QuoteScreen extends AbstractBaseMainActivity {

    private static final String LOG_TAG = QuoteScreen.class.getSimpleName();
    private static final String QUOTE_ID_KEY = "quoteId";

    private Locale locale;
    private String quoteFromTables;
    private String authorFromTables;
    private Bundle bundle;
    private SpeakerHelper speak;

    @Bind(R.id.textView_QuoteScreen)
    TextView tvQuoteScreen;
    @Bind(R.id.textView_author_QuoteScreen)
    TextView tvAuthorQuoteScreen;
    @Bind(R.id.buttonSave_QuoteScreen)
    Button bSaveQuoteScreen;
    @Bind(R.id.imageButton_QuoteScreen)
    ImageButton ibSpeaker;
    @Bind(R.id.progressBar_QuoteScreen)
    ProgressBar pbSpeaker;

    @OnClick(R.id.imageButton_QuoteScreen)
    void buttonSpeaker(){
        speak.allow(true);
        speak.speak(quoteFromTables);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speak.destroy();
    }

    //Facebook doesnt work
    @OnClick(R.id.buttonShare_QuoteScreen) void buttonShare(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, this.getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\"" + quoteFromTables + "\"" +
                " - " + authorFromTables + " #" + this.getString(R.string.app_name));
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.buttonShare)));
    }

    @OnClick(R.id.buttonSave_QuoteScreen) void buttonSave(){
        Integer quoteId = bundle.getInt(QUOTE_ID_KEY);
        int id_user = Integer.valueOf(new SessionManager(this).getUserDetails().get("id"));

        new QuoteSet(this).sendUserQuote(id_user, quoteId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        getDataFromBundle();

        speak = new SpeakerHelper(this, locale);

        tvQuoteScreen.setText(quoteFromTables);
        tvAuthorQuoteScreen.setText(authorFromTables);

        if(!new SessionManager(this).isLoggedIn()){
            bSaveQuoteScreen.setVisibility(View.GONE);
        }
    }

    private void getDataFromBundle(){
        bundle = getIntent().getExtras();
        quoteFromTables = bundle.getString("quote");
        authorFromTables = bundle.getString("author");
        locale = DetectDeviceLanguage.getLocaleFromString(bundle.getString("language"));

        if(bundle.getString("saved") != null){
            bSaveQuoteScreen.setVisibility(View.GONE);
        }
    }

    //TODO Fix the progress bar. It stops
    public void onEvent(final Boolean ready){

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    ErrorHandle.getInstance().Error(LOG_TAG, e.toString());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(ready){
                            ibSpeaker.setVisibility(View.VISIBLE);
                            pbSpeaker.setVisibility(View.GONE);
                        }else{
                            ibSpeaker.setVisibility(View.GONE);
                            pbSpeaker.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        };

        thread.run();
    }
}