package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.interfaces.HomeFragmentListener;
import com.imbaland.android.dota2armoury.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/08
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddUserDisplay extends RelativeLayout
{
	private ImageView m_playerImage;
	private EditText m_steamID;
	private HomeFragmentListener m_listener;

	private User m_user;

	public AddUserDisplay(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public static AddUserDisplay inflate(Context context)
	{
		return (AddUserDisplay) LayoutInflater.from(context).inflate(
				R.layout.layout_user_data_entry, null);
	}

	public void setListener(HomeFragmentListener _listener)
	{
		m_listener = _listener;
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		m_playerImage = (ImageView)findViewById(R.id.userImage);
		m_steamID = (EditText)findViewById(R.id.idText);
		m_steamID.requestFocus();
		m_steamID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId== EditorInfo.IME_ACTION_DONE){
					D2OApplication.getDatastore().fetchUserData(m_steamID.getText().toString());
				}
				return false;
			}
		});
	}
}
