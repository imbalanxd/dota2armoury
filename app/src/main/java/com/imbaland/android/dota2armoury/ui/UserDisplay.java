package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.imbaland.android.dota2armoury.R;
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
public class UserDisplay extends RelativeLayout
{
	private TextView m_playerAlias;
	private TextView m_playerUrl;
	private ImageView m_playerImage;
	private Button m_acceptButton;
	private HomeFragmentListener m_listener;

	private User m_user;

	public UserDisplay(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public static UserDisplay inflate(Context context)
	{
		return (UserDisplay) LayoutInflater.from(context).inflate(
				R.layout.layout_user_data, null);
	}

	public void setListener(HomeFragmentListener _listener)
	{
		m_listener = _listener;
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		m_playerAlias = (TextView)findViewById(R.id.userAlias);
		m_playerUrl = (TextView)findViewById(R.id.userSteamPage);
		m_playerImage = (ImageView)findViewById(R.id.userImage);
		m_acceptButton = (Button)findViewById(R.id.acceptButton);

		m_acceptButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				m_listener.userAccepted(m_user);
			}
		});
	}
	
	public void setUserData(User _user)
	{
		m_user = _user;
		Downloader.downloadBitmap(_user.getImageURL(), m_playerImage);
		m_playerAlias.setText(_user.getName());
		m_playerUrl.setText(_user.getProfileurl().replace("http://",""));
	}
}
