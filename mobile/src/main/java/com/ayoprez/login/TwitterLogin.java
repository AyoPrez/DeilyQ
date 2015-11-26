package com.ayoprez.login;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.deilyquote.R;
import com.ayoprez.restfulservice.GetUser;
import com.ayoprez.utils.Keys;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

/**
 * Created by AyoPrez on 25/07/15.
 */
public class TwitterLogin {

    private static final String LOG_TAG = TwitterLogin.class.getSimpleName();

    private String TYPE_ID = "t";
    private Context context;
    private User user;

    public TwitterLogin(Context context){
        this.context = context;
    }

    public void initTwitter(){
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Keys.T_KEY + context.getString(R.string.T_K),
                Keys.T_SECRET + context.getString(R.string.T_S));
        Fabric.with(context, new Twitter(authConfig));

        twitterLogin();
    }

    private LoginActivity getLoginActivity(){
        return ((LoginActivity)context);
    }

    public void changeLoginButtonStyle(TwitterLoginButton twitterButton){
        float fbIconScale = 1.45F;

        Drawable drawable = getLoginActivity().getResources().getDrawable(com.twitter.sdk.android.R.drawable.tw__ic_logo_white);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*fbIconScale),(int)(drawable.getIntrinsicHeight() * fbIconScale));

        //Add the icon in the desired position
        twitterButton.setCompoundDrawables(null, null, null, null);
        twitterButton.setCompoundDrawablePadding(0);
        twitterButton.setPadding(10, 30, 10, 30);
    }

    public void logoutTwitter(){
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
    }

    public void loginTwitter(TwitterSession session){
        Twitter.getSessionManager().setActiveSession(session);
    }

    private void askForTwitterMail(final TwitterSession session){
        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback() {
            @Override
            public void success(Result result) {
                user = new User(session.getUserName(), result.data.toString());
            }

            @Override
            public void failure(TwitterException exception) {
                ErrorHandle.getInstance().informUser(context, context.getString(R.string.errorLogin));
                ErrorHandle.getInstance().Error(LOG_TAG, exception.toString());
            }
        });
    }

    public void twitterLogin(){
        getLoginActivity().twitterLoginButton = (TwitterLoginButton) getLoginActivity().findViewById(R.id.twitter_login_button);
        changeLoginButtonStyle(getLoginActivity().twitterLoginButton);
        getLoginActivity().twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                user = new User(result.data.getUserName(), String.valueOf(result.data.getUserId()));

                new GetUser(context).sendUserDataRequest(user.getSocial_Id(), TYPE_ID, user.getName());
                loginTwitter(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                ErrorHandle.getInstance().informUser(context, context.getString(R.string.errorLogin));
                ErrorHandle.getInstance().Error(LOG_TAG, exception.toString());
            }
        });
    }
}