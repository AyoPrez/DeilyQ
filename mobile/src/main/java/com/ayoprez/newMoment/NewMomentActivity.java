package com.ayoprez.newMoment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.ayoprez.deilyquote.AbstractBaseMainActivity;
import com.ayoprez.deilyquote.MainActivity;
import com.ayoprez.deilyquote.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMomentActivity extends AbstractBaseMainActivity{

    @Bind(R.id.b_language)
    protected Button B_Language;
    @Bind(R.id.b_topic)
    protected Button B_Topic;
    @Bind(R.id.b_time)
    protected Button B_Time;

	@OnClick(R.id.b_language)
	void OnButtonLanguageClick(){
        new Language(this);
    }
    @OnClick(R.id.b_topic)
    void OnButtonTopicClick(){
        new Topic(this);
    }
    @OnClick(R.id.b_time)
    void OnButtonTimeClick(){
        new Moment(this);
    }
    @OnClick(R.id.b_accept)
    void OnButtonAcceptClick(){
        new Accept(this).Accept_Dialog(B_Language.getText().toString(),
                B_Topic.getText().toString(), B_Time.getText().toString());
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_newmoment);
        ButterKnife.bind(this);

        initToolbar();
	}

	@Override
	protected void initToolbar(){
        super.initToolbar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	}

	public void Language_Text(String Text){
		B_Language.setText(Text);
	}
	
	public void Topic_Text(String Text){
		B_Topic.setText(Text);
	}
		
	public void Time_Text(String Text){
		B_Time.setText(Text);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goToNewScreen(MainActivity.class);
                return true;
        }
        return true;
    }
}