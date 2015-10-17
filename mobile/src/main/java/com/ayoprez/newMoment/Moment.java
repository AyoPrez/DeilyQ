package com.ayoprez.newMoment;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.widget.TimePicker;

import com.ayoprez.deilyquote.R;
import com.ayoprez.utils.Utils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Moment {

	private Context ctx;
	private Dialog Dialog;

    @Bind(R.id.tP_time)
	protected TimePicker Timey_Wimey;
    @OnClick(R.id.b_time_dialog)
    void OnButtonTimeClick(){
        int H;
        int M;

        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            H = Timey_Wimey.getCurrentHour();
            M = Timey_Wimey.getCurrentMinute();
        }else{
            H = Timey_Wimey.getHour();
            M = Timey_Wimey.getMinute();
        }

        ((NewMomentActivity) ctx).Time_Text(Utils.WithZero(H) + ":" + Utils.WithZero(M));

        Dialog.dismiss();
    }

	public Moment(Context context){
		this.ctx = context;
		initDialog(context);
        ButterKnife.bind(this, Dialog);

        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            Timey_Wimey.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        }else{
            Timey_Wimey.setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        }

        Timey_Wimey.setIs24HourView(true);

		Dialog.show();
	}

    private void initDialog(Context context){
        this.Dialog = new Dialog(context);
        this.Dialog.setContentView(R.layout.dialog_time);
        this.Dialog.setTitle(R.string.button_time);
    }
}