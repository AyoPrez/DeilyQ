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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;

import com.ayoprez.database.FavQuotesRepository;
import com.ayoprez.deilyquote.AbstractBaseMainActivity;
import com.ayoprez.deilyquote.AnswerHandle;
import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.deilyquote.MainActivity;
import com.ayoprez.deilyquote.R;
import com.ayoprez.preferences.Preferences;
import com.ayoprez.restfulservice.QuoteGet;
import com.ayoprez.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by AyoPrez on 24/05/15.
 */
public class SavedQuotesScreen extends AbstractBaseMainActivity {
    private static final String LOG_TAG = SavedQuotesScreen.class.getSimpleName();

    protected Dialog loadDialog;
    protected RecyclerView savedQuotesRecyclerView;
    protected RecyclerView.Adapter recyclerViewAdapter;
    protected RecyclerView.LayoutManager recyclerViewLayoutManager;

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

        try {
            getSavedQuotes(this, getUserId());
        } catch (Exception e) {
            ErrorHandle.getInstance().Error(LOG_TAG, e);
            e.printStackTrace();
        }
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
        if(getDialog() != null) {
            getDialog().cancel();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_word_screen, menu);

        MenuItem loginItem = menu.findItem(R.id.action_signIn);
        if(sessionManager.isLoggedIn()){
            loginItem.setTitle(getString(R.string.action_logout));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signIn:
                AnswerHandle.Answer("Logout");
                sessionManager.logoutUser();
                finish();
                return true;
            case R.id.action_settings:
                AnswerHandle.Answer("Settings");
                Intent i = new Intent(this, Preferences.class);
                startActivity(i);
                return true;
        }
        return true;
    }

    @Override
    protected void initToolbar(){
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewScreen(MainActivity.class);
            }
        });
    }

    protected void getSavedQuotes(Context context, int userId){
        if(isNetworkAvailable()) {
            new QuoteGet().getUserQuotes(context, userId);
            createLoadDialog();
        }else{
            if(FavQuotesRepository.getRowsCount(this) > 0) {
                EventBus.getDefault().post(Utils.convertUserQuotesToSavedQuotes(FavQuotesRepository.getAllQuotes(this)));
            }else{
                viewStubNoInternet.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onEvent(ArrayList<SavedQuotes> savedQuotes){
        if(savedQuotes.size() > 0) {
            initRecyclerView(savedQuotes);
            cancelDialog();
        }else{
            viewStubNoInternet.setLayoutResource(R.layout.empty_list_layout);
            viewStubNoInternet.setVisibility(View.VISIBLE);
            cancelDialog();
        }
    }
}