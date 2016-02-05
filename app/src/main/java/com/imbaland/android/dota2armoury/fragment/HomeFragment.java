package com.imbaland.android.dota2armoury.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.adapters.HomePageAdapter;
import com.imbaland.android.dota2armoury.adapters.InventoryGridAdapter;
import com.imbaland.android.dota2armoury.adapters.UserDataListAdapter;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.interfaces.HomeFragmentListener;
import com.imbaland.android.dota2armoury.model.HeroPool;
import com.imbaland.android.dota2armoury.model.Inventory;
import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.model.User;
import com.imbaland.android.dota2armoury.ui.*;
import com.imbaland.android.dota2armoury.util.PreferencesUtility;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/28
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeFragmentListener
{
	private EditText idEdit;
	private Button idEnter;
	private ListView userDataList;
	private LinearLayout inventoryContainer;
	private CustomViewPager homePager;
	private HomePageAdapter homePageAdapter;
	private Drawer selectUserDrawer;
	private View selectUserToggle;
	private View addUser;

	private UserDataListAdapter userListAdapter;
	private ArrayList<User> userList;

	@Override
	protected void registerFragment()
	{
		m_fragID = Constants.Fragments.HOME_FRAGMENT;
		registerForData(Constants.Data.USER_SUMMARY_DATA, Constants.Data.USER_INVENTORY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_home, container, false);

		userList = new ArrayList<User>();
		idEdit = (EditText)m_view.findViewById(R.id.idText);
		idEnter = (Button)m_view.findViewById(R.id.idEnter);
		idEnter.setOnClickListener(this);
		userDataList = (ListView)m_view.findViewById(R.id.userDataList);
		userListAdapter = new UserDataListAdapter(userList, this);
		userDataList.setAdapter(userListAdapter);
		inventoryContainer = (LinearLayout)m_view.findViewById(R.id.inventoryContainer);
		homePager = (CustomViewPager)m_view.findViewById(R.id.homePager);
		homePager.setScrollingEnabled(false);

		HeroPool.parseHeroPool(dataStore.loadRawText(R.raw.hero_slots));

		ArrayList<User> users = PreferencesUtility.getUsers();


		selectUserDrawer = ((Drawer)m_view.findViewById(R.id.loginDrawer));
		selectUserToggle = m_view.findViewById(R.id.selectUserLip);
		selectUserToggle.setOnClickListener(this);

		addUser = m_view.findViewById(R.id.addUser);
		addUser.setOnClickListener(this);
		setUserData(users.toArray(new User[users.size()]));
		return m_view;
	}

	private void setUserData(User [] _data)
	{
		for(int i = 0; i < _data.length; i++)
		{
			PreferencesUtility.addUser(_data[i]);
			if(!userList.contains(_data[i]))
				userList.add(0, _data[i]);
		}
		enableAddUser(false);
		updateUserList();
	}

	private void updateUserList()
	{
		userListAdapter.notifyDataSetChanged();
	}

	private void setUserInventory(Inventory _inventory)
	{
		LinearLayout userDisplay = (LinearLayout)m_view.findViewById(R.id.currentUserContainer);
		userDisplay.removeAllViews();
		userDisplay.addView(ActiveUserDisplay.inflate(D2OApplication.getContext(), dataStore.getActiveUser()));

		homePageAdapter = new HomePageAdapter(getActivity().getSupportFragmentManager(), this);
		homePager.setAdapter(homePageAdapter);
		homePager.setCurrentItem(2);
	}

	private void displayItemInfo(Item _item)
	{

	}

	private void enableAddUser(boolean _state)
	{
		if(_state)
		{
			userListAdapter.setEntryMode(true);
			updateUserList();
			addUser.setVisibility(View.GONE);
		}
		else
		{
			userListAdapter.setEntryMode(false);
			updateUserList();
			addUser.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void userAccepted(User _user)
	{
		dataStore.setActiveUser(_user);
		dataStore.fetchUserInventory(_user.getSteamID());
	}

	@Override
	public void itemSelected(Item _item)
	{
		displayItemInfo(_item);
	}

	@Override
	public void onClick(View v)
	{
		if(v == idEnter)
		{
			dataStore.fetchUserData(idEdit.getText().toString());
		}
		else if(v == selectUserToggle)
		{
			selectUserDrawer.toggle();
		}
		else if(v == addUser)
		{
			enableAddUser(true);
		}
	}

	@Override
	public void onDataComplete(short _id)
	{
		switch(_id)
		{
			case Constants.Data.USER_SUMMARY_DATA:
				setUserData(dataStore.getUserData());
				break;
			case Constants.Data.USER_INVENTORY:
				setUserInventory(dataStore.getUserInventory());
				selectUserDrawer.hide();
				break;
		}
	}
}
