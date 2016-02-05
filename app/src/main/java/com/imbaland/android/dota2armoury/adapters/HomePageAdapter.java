package com.imbaland.android.dota2armoury.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.fragment.*;
import com.imbaland.android.dota2armoury.interfaces.HomeFragmentListener;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/15
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomePageAdapter extends FragmentPagerAdapter
{
	D2ODataStore dataStore = D2OApplication.getDatastore();
	HomeFragmentListener m_listener;

	public HomePageAdapter(FragmentManager _fm, HomeFragmentListener _listener)
	{
		super(_fm);
		m_listener = _listener;
	}

	@Override
	public Fragment getItem(int i)
	{
		BaseFragment fragment;
		switch(i)
		{
			case 0:
				fragment = new LoadoutFragment();
				break;
			case 1:
				fragment = new LoadoutFragment();
				break;
			case 2:
				fragment = new LoadoutFragment();
				break;
			default:
				fragment = new LoadoutFragment();
				break;
		}
		return fragment;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getCount()
	{
		return 2;
	}
}
