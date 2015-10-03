package com.ayoprez.deilyquote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.ayoprez.database.CreateDatabase;
import com.ayoprez.database.UserMomentsRepository;
import com.ayoprez.login.LoginActivity;
import com.ayoprez.newMoment.NewMomentActivity;
import com.ayoprez.notification.StartAndCancelAlarmManager;
import com.ayoprez.preferences.Preferences;
import com.ayoprez.userProfile.ProfileScreen;
import com.ayoprez.utils.Tests;
import com.ayoprez.utils.Utils;
import com.crashlytics.android.Crashlytics;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import deilyquote.UserMoments;

public class MainActivity extends AbstractBaseMainActivity {

    @Bind(R.id.reviewList) ListView mReviewList;

    @OnClick(R.id.b_main) void mMomentsButton(){
        Intent newMomentIntent = new Intent(this, NewMomentActivity.class);
        startActivity(newMomentIntent);
        this.finish();
    }

    @OnItemLongClick(R.id.reviewList) boolean longItem(int position){
        showAlertDialogToDeleteItem(this, position);
        return true;
    }

    //Test
    //TODO hide
    @OnClick(R.id.buttonn) void newNotification(){
        new Tests().testNotification(this);
    }

    private ReviewAdapter mReviewAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        new CreateDatabase(this);

        showReviewList(this);
    }

    public List<UserMoments> getDataFromDatabaseToListView(Context context) {
        return new UserMomentsRepository().getAllMoments(context);
    }

    private void showReviewList(Context context) {
        mReviewAdapter = new ReviewAdapter(context, getDataFromDatabaseToListView(context));
        mReviewList.setAdapter(mReviewAdapter);
    }

    public void showAlertDialogToDeleteItem(final Context context, final int selectedItem) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    deleteItemFromDatabase(context, selectedItem);
                    showReviewList(context);
                } catch (Exception e) {
                    //TODO Change for Snackbar
                    Toast.makeText(context, getString(R.string.errorDeletingMoment), Toast.LENGTH_LONG).show();
                }
            }
        };

        new Utils().Create_Dialog_DoNotFinishActivity(this, getString(R.string.deleteMomentDialog),
                getString(android.R.string.yes), getString(R.string.deleteMomentDialogTitle), onClickListener);

    }

    private void deleteItemFromDatabase(Context context, int selectedItem) {
        try {
            new StartAndCancelAlarmManager(context, getDataFromDatabaseToListView(context).get(selectedItem)).cancelAlarmManager();
            new UserMomentsRepository().deleteSelectedMoment(context, selectedItem);
        }catch(Exception e){
            //TODO Crashlytics
            Crashlytics.getInstance();
            Log.e("DeilyLang", "Error deleting: " + e);
        }
    }

    public ListView getListView(){
        return mReviewList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_word_screen, menu);

        MenuItem loginItem = menu.findItem(R.id.action_signIn);

        if(sessionManager.isLoggedIn()) {
            loginItem.setTitle(getUserName());
        }else{
            loginItem.setTitle(getString(R.string.action_login));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signIn:
                if(!sessionManager.isLoggedIn()){
                    goToNewScreen(LoginActivity.class);
                }else{
                    goToNewScreen(ProfileScreen.class);
                }

                return true;
            case R.id.action_settings:
                Intent i = new Intent(this, Preferences.class);
                startActivity(i);
                return true;
        }
        return true;
    }
}