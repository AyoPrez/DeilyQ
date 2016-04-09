package com.ayoprez.deilyquote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.ayoprez.database.CreateDatabase;
import com.ayoprez.login.LoginActivity;
import com.ayoprez.newMoment.NewMomentActivity;
import com.ayoprez.preferences.Preferences;
import com.ayoprez.savedQuotes.SavedQuotesScreen;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import de.greenrobot.event.EventBus;
import fr.nicolaspomepuy.discreetapprate.AppRate;

public class MainActivity extends AbstractBaseMainActivity {

    @Bind(R.id.reviewList)
    ListView reviewList;

    @OnClick(R.id.b_main)
    void mMomentsButton(){
        Intent newMomentIntent = new Intent(this, NewMomentActivity.class);
        startActivity(newMomentIntent);
        this.finish();
    }

    @OnItemLongClick(R.id.reviewList)
    boolean longItem(int position){
        new ReviewList(reviewList).showAlertDialogToDeleteItem(this, position);
        AnswerHandle.Answer("Long click in MainActivity");
        return true;
    }

    //Test
    //hide
//    @OnClick(R.id.buttonn)
//    void newNotification(){
//        new Tests().testNotification(this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initToolbar();

        new CreateDatabase(this);

        new ReviewList(reviewList).showReviewList(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_word_screen, menu);

        MenuItem loginItem = menu.findItem(R.id.action_signIn);

        loginItem.setTitle(sessionManager.isLoggedIn() ? getUserName() : getString(R.string.action_login));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signIn:
                AnswerHandle.Answer("SignIn");
                goToNewScreen(!sessionManager.isLoggedIn() ? LoginActivity.class : SavedQuotesScreen.class);
                return true;
            case R.id.action_settings:
                AnswerHandle.Answer("Settings");
                startActivity(new Intent(this, Preferences.class));
                return true;
        }
        return true;
    }

    public void onEvent(ErrorMessage message){
        ErrorHandle.getInstance().informUser(this, message.getMessage());
        EventBus.getDefault().unregister(this);
    }
}