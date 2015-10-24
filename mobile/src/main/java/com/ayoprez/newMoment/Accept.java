package com.ayoprez.newMoment;

import android.content.Context;
import android.widget.Toast;

import com.ayoprez.database.UserMomentsRepository;
import com.ayoprez.deilyquote.R;
import com.ayoprez.notification.StartAndCancelAlarmManager;
import com.ayoprez.utils.Utils;

import deilyquote.UserMoments;

public class Accept {

	private Context context;
	
	public Accept(Context context){
		this.context = context;
	}

	//TODO See if I need AppLanguage and delete it if I don't need it
	public void Accept_Dialog(String AppLanguage, String Language, final String Topic, String Time){
		UserMomentsRepository userMomentsRepository = new UserMomentsRepository();

		if(!Language.equals(context.getString(R.string.button_language)) && !Topic.equals(context.getString(R.string.button_topic))
				&& !Time.equals(context.getString(R.string.button_time))){
            UserMoments userMoments = new UserMoments(
                    userMomentsRepository.getLastId(context),
                    Language,
                    Topic,
                    Utils.TakeOutTimeDots(Time));

            userMomentsRepository.insertOrUpdate(context, userMoments);

			if(new StartAndCancelAlarmManager(context, userMoments).startAlarmManager(Time)){
                Utils.Create_Dialog(context, context.getString(R.string.successSavingMoment),
                        context.getString(R.string.buttonAcceptDialog),
                        context.getString(R.string.successSavingDialogTitle));
            }else{
                Utils.Create_Dialog(context, context.getString(R.string.errorSavingMoment),
                        context.getString(R.string.buttonAcceptDialog),
                        context.getString(R.string.errorSavingDialogTitle));
            }
		}else{
			Toast.makeText(context, context.getString(R.string.error_select), Toast.LENGTH_LONG).show();
		}
	}
		
}