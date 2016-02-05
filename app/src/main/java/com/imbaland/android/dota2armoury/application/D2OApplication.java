package com.imbaland.android.dota2armoury.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/16
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class D2OApplication extends Application
{
	private static Context m_Context;
	private static D2ODataStore m_datastore;

	@Override
	public void onCreate()
	{
		super.onCreate();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		m_Context = this;
		getDatastore();
	}

	public static Context getContext()
	{
		return m_Context;
	}

	public static D2ODataStore getDatastore()
	{
		if(m_datastore == null)
			m_datastore = new D2ODataStore(m_Context);
		return m_datastore;
	}

	public static void sendIntent(String _intent)
	{
		Intent intent = new Intent();
		intent.setAction(_intent);
		D2OApplication.getContext().sendBroadcast(intent);
	}
}
