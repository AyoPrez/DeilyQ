package com.ayoprez.newMoment;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ayoprez.deilyquote.R;

import java.util.ArrayList;

public class Topic {
	
	private Dialog Dialog;
	private TopicDialogAdapter Adapter;
	private ListView listViewTopics;

	public Topic(final Context context){
		
		this.Dialog = new Dialog(context);
		this.Dialog.setContentView(R.layout.dialog_topic);
		this.Dialog.setTitle(R.string.button_topic);

		final ArrayList<String> Topics = new ArrayList<>();
		Topics.add(context.getString(R.string.topic1));
		Topics.add(context.getString(R.string.topic2));
		Topics.add(context.getString(R.string.topic3));
		Topics.add(context.getString(R.string.topic4));
		Topics.add(context.getString(R.string.topic5));
		Topics.add(context.getString(R.string.topic6));
		Topics.add(context.getString(R.string.topic7));
		Topics.add(context.getString(R.string.topic8));
//		Topics.add(context.getString(R.string.topic9));
		Topics.add(context.getString(R.string.topic10));

		listViewTopics = (ListView) Dialog.findViewById(R.id.listView_topic);
		this.Adapter = new TopicDialogAdapter(context, Topics);
		this.listViewTopics.setAdapter(Adapter);

		listViewTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				((NewMomentActivity)context).Topic_Text(Topics.get(position));
				Dialog.dismiss();
			}
		});

		Dialog.show();
	}
}