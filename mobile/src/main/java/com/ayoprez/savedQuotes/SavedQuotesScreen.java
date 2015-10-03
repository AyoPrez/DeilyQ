package com.ayoprez.savedQuotes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;

import com.ayoprez.deilyquote.AbstractBaseMainActivity;
import com.ayoprez.deilyquote.R;
import com.ayoprez.restfulservice.QuoteGet;
import com.ayoprez.userProfile.ProfileScreen;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by AyoPrez on 24/05/15.
 */
public class SavedQuotesScreen extends AbstractBaseMainActivity {

    //Probar a poner el context en el Abstract

    private Toolbar toolbar;
    protected Dialog loadDialog;
    private RecyclerView savedQuotesRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Bind(R.id.viewStub_no_internet)
    ViewStub viewStubNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedquotes_screen);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        initToolbar();

        if(isNetworkAvailable()) {
            getSavedQuotes(getUserId());
        }else{
            viewStubNoInternet.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToProfileIntent();
            }
        });
    }

    private void backToProfileIntent(){
        Intent intent = new Intent(this, ProfileScreen.class);
        startActivity(intent);
        finish();
    }

    protected int getUserId(){
        return Integer.valueOf(sessionManager.getUserDetails().get("id"));
    }

    protected void getSavedQuotes(int userId){
        new QuoteGet().getUserQuotes(userId);
        createLoadDialog();
    }

    public void onEvent(ArrayList<SavedQuotes> savedQuotes){
        initRecyclerView(savedQuotes);
        cancelDialog();
        EventBus.getDefault().unregister(this);
    }
}