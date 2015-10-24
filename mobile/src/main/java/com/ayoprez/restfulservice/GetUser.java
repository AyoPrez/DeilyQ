package com.ayoprez.restfulservice;

import android.content.Context;

import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.login.LoginActivity;
import com.ayoprez.login.User;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AyoPrez on 8/08/15.
 */
public class GetUser {
    public static final String ENDPOINT = "http://deilyquote.ayoprez.com/api/index.php/";

    private static final String LOG_TAG = GetUser.class.getSimpleName();

    private UserAPI userAPI;
    private Context context;

    public GetUser(Context context){
        this.context = context;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        userAPI = restAdapter.create(UserAPI.class);
    }

    public void sendUserDataRequest(final String social_id, final String type_id, final String userName){

        userAPI.getUserLogin(social_id, type_id, new Callback<User>() {

            @Override
            public void success(User user, Response response) {
                if (user != null && user.getId_U() != 0) {

                    ((LoginActivity)context).startSession(context, new User(userName, user.getId_U()));

                } else {
                    ErrorHandle.getInstance().Error(LOG_TAG, "GetUser = null");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorHandle.getInstance().Error(LOG_TAG, error.toString());
            }
        });
    }
}