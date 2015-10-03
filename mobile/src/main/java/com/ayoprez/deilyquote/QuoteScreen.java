package com.ayoprez.deilyquote;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ayoprez.login.SessionManager;
import com.ayoprez.restfulservice.QuoteSet;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuoteScreen extends AbstractBaseMainActivity {

    //Probar a poner el context en el Abstract

    private static final String QUOTE_ID_KEY = "quoteId";

    private Context context;
    private Locale locale;
    private String quoteFromTables;
    private String authorFromTables;
    private Bundle bundle;
    private SpeakerHelper speak;

    @Bind(R.id.textView_QuoteScreen)
    TextView mTVQuoteScreen;
    @Bind(R.id.textView_author_QuoteScreen)
    TextView mTVAuthorQuoteScreen;
    @Bind(R.id.buttonSave_QuoteScreen)
    Button mBSaveQuoteScreen;

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
        int id_user = Integer.valueOf(new SessionManager(context).getUserDetails().get("id"));

        new QuoteSet(this).sendUserQuote(id_user, quoteId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ButterKnife.bind(this);

        this.context = this;

        getDataFromBundle();

        speak = new SpeakerHelper(this, locale);

        mTVQuoteScreen.setText(quoteFromTables);
        mTVAuthorQuoteScreen.setText(authorFromTables);

        if(!new SessionManager(this).isLoggedIn()){
            mBSaveQuoteScreen.setVisibility(View.GONE);
        }
    }

    private void getDataFromBundle(){
        bundle = getIntent().getExtras();
        quoteFromTables = bundle.getString("quote");
        authorFromTables = bundle.getString("author");
        locale = DeviceLocale;

        if(bundle.getString("saved") != null){
            mBSaveQuoteScreen.setVisibility(View.GONE);
        }
    }

}