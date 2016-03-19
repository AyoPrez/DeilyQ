package com.ayoprez.restfulservice;

import android.content.Context;

import com.ayoprez.deilyquote.ErrorHandle;
import com.ayoprez.deilyquote.R;
import com.ayoprez.utils.Utils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AyoPrez on 19/08/15.
 */
public class QuoteSet {
    public static final String ENDPOINT = "http://deilyquote.ayoprez.com/api/index.php/";

    private static final String LOG_TAG = QuoteSet.class.getSimpleName();

    private UserAPI userAPI;
    private Context context;

    public QuoteSet(Context context){
        this.context = context;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        userAPI = restAdapter.create(UserAPI.class);
    }

    public void sendUserQuote(final int id_u, final int id_word){

        userAPI.postUserQuote(id_u, id_word, new Callback<Integer>() {

            @Override
            public void success(Integer bool, Response response) {

                if (bool == 1) {
                    getConfirmedDialog();
                } else {
                    ErrorHandle.getInstance().informUser(context, context.getString(R.string.errorDefault));
                    ErrorHandle.getInstance().Error(LOG_TAG, new Exception("GetWord = Null"));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //FIXME Temporal fix. In the future, replace retrofit for okhttp (or last retrofit version)
                if(error.getCause().toString().equals("java.io.EOFException")) {
                    userAPI.postUserQuote(id_u, id_word, this);
                }else{
                    ErrorHandle.getInstance().informUser(context, context.getString(R.string.errorDefault));
                    ErrorHandle.getInstance().Error(LOG_TAG, error);
                }
            }
        });
    }

    private void getConfirmedDialog(){
        new Utils().Create_Dialog(context,
                context.getString(R.string.doneDialog),
                context.getString(R.string.buttonAcceptDialog),
                context.getString(R.string.doneDialogTitle));
    }
}