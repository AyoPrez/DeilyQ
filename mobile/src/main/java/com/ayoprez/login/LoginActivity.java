package com.ayoprez.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ayoprez.deilyquote.AbstractBaseMainActivity;
import com.ayoprez.deilyquote.AnswerHandle;
import com.ayoprez.deilyquote.MainActivity;
import com.ayoprez.deilyquote.R;
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