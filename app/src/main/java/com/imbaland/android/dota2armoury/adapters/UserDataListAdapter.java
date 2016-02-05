package com.imbaland.android.dota2armoury.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.interfaces.HomeFragmentListener;
import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.model.ItemSearchResult;
import com.imbaland.android.dota2armoury.model.User;
import com.imbaland.android.dota2armoury.ui.AddUserDisplay;
import com.imbaland.android.dota2armoury.ui.UserDisplay;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/21
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDataListAdapter extends BaseAdapter
{
	D2ODataStore dataStore;
	ArrayList<User> users;
	private boolean entryMode;
	private HomeFragmentListener m_listener;

	public UserDataListAdapter(ArrayList<User> _users, HomeFragmentListener _listener)
	{
		super();
		users =_users;
		m_listener = _listener;
		dataStore = D2OApplication.getDatastore();
	}

	@Override
	public int getCount()
	{
		return users.size() + (entryMode ? 1 : 0);
	}

	@Override
	public Object getItem(int position)
	{
		return users.get(position - (entryMode ? 1 : 0));
	}

	@Override
	public long getItemId(int position)
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = null;
		if(entryMode && position == 0)
		{
			//User user = (User)getItem(position);
			AddUserDisplay userDisplayView = null;
			if(view == null)
				userDisplayView = AddUserDisplay.inflate(D2OApplication.getContext());

			view = userDisplayView;
		}
		else
		{
			User user = (User)getItem(position);
			UserDisplay userDisplayView = null;
			if(view == null)
				userDisplayView = UserDisplay.inflate(D2OApplication.getContext());

			userDisplayView.setUserData(user);
			userDisplayView.setListener(m_listener);
			view = userDisplayView;
		}

		return view;
	}

	public void setEntryMode(boolean _state)
	{
		entryMode = _state;
	}
}
