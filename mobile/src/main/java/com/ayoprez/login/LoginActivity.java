package com.ayoprez.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ayoprez.deilyquote.AbstractBaseMainActivity;
import com.ayoprez.deilyquote.AnswerHandle;
import com.ayoprez.deilyquote.MainActivity;
import com.ayoprez.deilyquote.R;
import com.ayoprez.utils.Utils;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AyoPrez on 22/05/15.
 */
public class LoginActivity extends AbstractBaseMainActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public TwitterLoginButton twitterLoginButton;
    private FacebookLogin facebookLogin = new FacebookLogin(this);
    private TwitterLogin twitterLogin = new TwitterLogin(this);
    private GoogleLogin googleLogin = new GoogleLogin(this);

    @OnClick(R.id.terms_link)
    void termsLink(){
        startActivity(new Intent(this, LegalActivity.class));
    }

    @OnClick(R.id.login_continue)
    void loginContinue(){
        AnswerHandle.Answer("Login", "Method", "Without login");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookLogin.initFacebook();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        try {
            if (getUserId() != 0) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        facebookLogin.facebookLogin();
        twitterLogin.twitterLogin();
        googleLogin.googleLogin();

        showDialogOnce();
    }

    private void showDialogOnce(){
        boolean firstrun = this.getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrunQ", true);
        if (firstrun){
            Utils.Create_Dialog_DoNotFinishActivity(this, getString(R.string.login_dialog_text),
                    "Ok", getString(R.string.login_dialog_title), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            // Save the state
            this.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrunQ", false)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

        facebookLogin.callbackManager.onActivityResult(requestCode, resultCode, data);

        googleLogin.activityResult(requestCode, data);
    }

    public void startSession(Context applicationContext, User user){
        new SessionManager(applicationContext).createLoginSession(user.getName(), String.valueOf(user.getId_U()));
        goToNewScreen(MainActivity.class);
    }
}