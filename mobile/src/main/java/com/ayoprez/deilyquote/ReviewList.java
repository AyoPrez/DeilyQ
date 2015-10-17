package com.ayoprez.deilyquote;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.Toast;

import com.ayoprez.database.UserMomentsRepository;
import com.ayoprez.notification.StartAndCancelAlarmManager;
import com.ayoprez.utils.Utils;

import java.util.List;

import deilyquote.UserMoments;

/**
 * Created by AyoPrez on 10.10.15.
 */
public class ReviewList {

    private static final String LOG_TAG = "ReviewList";

    protected ReviewAdapter reviewAdapter;
    private ListView reviewList;

    public ReviewList(ListView reviewList){
        this.reviewList = reviewList;
    }

    private List<UserMoments> getDataFromDatabaseToListView(Context context) {
        return new UserMomentsRepository().getAllMoments(context);
    }

    public void showReviewList(Context context) {
        reviewAdapter = new ReviewAdapter(context, getDataFromDatabaseToListView(context));
        reviewList.setAdapter(reviewAdapter);
    }

    public void showAlertDialogToDeleteItem(final Context context, final int selectedItem) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    deleteItemFromDatabase(context, selectedItem);
                    showReviewList(context);
                } catch (Exception e) {
                    //TODO Change for Snackbar
                    Toast.makeText(context, R.string.errorDeletingMoment, Toast.LENGTH_LONG).show();
                }
            }
        };

        Utils.Create_Dialog_DoNotFinishActivity(context, context.getString(R.string.deleteMomentDialog),
                context.getString(android.R.string.yes), context.getString(R.string.deleteMomentDialogTitle), onClickListener);
    }

    private void deleteItemFromDatabase(Context context, int selectedItem) {
        try {
            new StartAndCancelAlarmManager(context, getDataFromDatabaseToListView(context).get(selectedItem)).cancelAlarmManager();
            new UserMomentsRepository().deleteSelectedMoment(context, selectedItem);
        }catch(Exception e){
            //TODO Crashlytics
            ErrorHandle.getInstance().Error(LOG_TAG, e.toString());
        }
    }
}
