package com.ayoprez.newMoment;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ayoprez.deilyquote.R;

import java.util.ArrayList;

public class Language {

	private Dialog Dialog;
	private LanguageDialogAdapter Adapter;
	private ListView Language_List;
	
	
	public Language(final Context context){
		this.Dialog = new Dialog(context);
		this.Dialog.setContentView(R.layout.dialog_language);
		this.Dialog.setTitle(R.string.button_language);

		final ArrayList<String> Languages = new ArrayList<>();
		Languages.add(context.getString(R.string.Spanish));
		Languages.add(context.getString(R.string.English));
		Languages.add(context.getString(R.string.German));
		Languages.add(context.getString(R.string.Italian));
		Languages.add(context.getString(R.string.French));

		this.Language_List = (ListView)Dialog.findViewById(R.id.LV_Language);
		this.Adapter = new LanguageDialogAdapter(context, Languages);
		this.Language_List.setAdapter(Adapter);

		this.Language_List.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				((NewMomentActivity)context).Language_Text(Languages.get(position));
				Dialog.dismiss();
			}
		});

		Dialog.show();
	}

}