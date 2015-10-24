package com.ayoprez.savedQuotes;

import android.app.Dialog;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.ayoprez.deilyquote.AbstractBaseMainActivity;
import com.ayoprez.deilyquote.R;
import com.ayoprez.restfulservice.QuoteGet;
import com.ayoprez.userProfile.ProfileScreen;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by AyoPrez on 24/05/15.
 */
public class SavedQuotesScreen extends AbstractBaseMainActivity {

    protected Dialog loadDialog;
    protected RecyclerView savedQuotesRecyclerView;
    protected RecyclerView.Adapter recyclerViewAdapter;
    protected RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedquotes_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        EventBus.getDefault().register(this);

        initToolbar();

        getSavedQuotes(getUserId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void createLoadDialog(){
        loadDialog = new Dialog(this);
        loadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadDialog.setContentView(R.layout.dialog_load);
        loadDialog.show();
    }

    public Dialog getDialog(){
        return loadDialog;
    }

    private void cancelDialog(){
        getDialog().cancel();
    }

    private void initRecyclerView(ArrayList<SavedQuotes> savedQuotes){
        savedQuotesRecyclerView = (RecyclerView) findViewById(R.id.savedQuotesRecyclerView);

        savedQuotesRecyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        savedQuotesRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new SavedQuotesRecyclerViewAdapter(this, savedQuotes);
        savedQuotesRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void initToolbar(){
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewScreen(ProfileScreen.class);
            }
        });
    }

    protected void getSavedQuotes(int userId){
        new QuoteGet().getUserQuotes(userId);
        createLoadDialog();
    }

    public void onEvent(ArrayList<SavedQuotes> savedQuotes){
        initRecyclerView(savedQuotes);
        cancelDialog();
    }
}