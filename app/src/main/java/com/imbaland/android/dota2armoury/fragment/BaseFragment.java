package com.imbaland.android.dota2armoury.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseFragment extends Fragment implements FragmentDataListener
{
	protected View m_view;
	protected short m_fragID;
	protected D2ODataStore dataStore = D2OApplication.getDatastore();

	@Override
	public void onResume()
	{
		super.onResume();
		registerFragment();
	}

	protected void registerFragment()
	{

	}

	protected void registerForData(short ... params)
	{
		for(short id : params)
			D2OApplication.getDatastore().registerListener(this, id);
	}

	@Override
	public void onDataComplete(short _id)
	{

	}
}