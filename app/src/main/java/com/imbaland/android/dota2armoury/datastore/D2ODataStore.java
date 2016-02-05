package com.imbaland.android.dota2armoury.datastore;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.assets.AssetLoader;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.download.JSONDownloader;
import com.imbaland.android.dota2armoury.fragment.FragmentDataListener;
import com.imbaland.android.dota2armoury.model.*;
import com.imbaland.android.dota2armoury.webservice.ItemMarketWebService;
import com.imbaland.android.dota2armoury.webservice.ItemSearchWebService;
import com.imbaland.android.dota2armoury.webservice.UserDataWebService;
import com.imbaland.android.dota2armoury.webservice.UserInventoryWebService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class D2ODataStore
{
	private Context m_context;
	//Data values
	private int dataExpireTime = 20000;

	//Datalisteners
	private HashMap<Short, ArrayList<FragmentDataListener>> m_dataListeners;
	//Data responses
	private HashMap<Short, Object> m_dataResponse;
	//Data timestamps
	private HashMap<Short, Long> m_dataTimeStamps;

	private User m_activeUser;

	public D2ODataStore(Context _context)
	{
		m_context = _context;
		m_dataTimeStamps = new HashMap<Short, Long>();
		m_dataListeners = new HashMap<Short, ArrayList<FragmentDataListener>>();
		m_dataResponse = new HashMap<Short, Object>();
	}

	public void fetchUserData(String _userId)
	{
		UserDataWebService userData = new UserDataWebService();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.WebServices.UserData.USER_ID, _userId);
		userData.requestData(parameters);
	}

	public void fetchItemMarketInfo(String _itemIdentifier)
	{
		ItemMarketWebService marketInfo = new ItemMarketWebService();
		HashMap<String, String> parameters = new HashMap<String, String>();
		Item item = getUserInventory().getItem(_itemIdentifier);
		if(item != null)
		{
			parameters.put(Constants.WebServices.ItemData.ITEM_NAME, item.name);
			parameters.put(Constants.WebServices.EXTRA, _itemIdentifier);
			marketInfo.requestData(parameters);
		}
		else
		{
			//ERROR DISPLAY
		}
	}

	public User [] getUserData()
	{
		User [] data = {(User)m_dataResponse.get(Constants.Data.USER_SUMMARY_DATA)};
		return data;
	}

	public MarketInfo getMarketInfo()
	{
		MarketInfo info = (MarketInfo)m_dataResponse.get(Constants.Data.MARKET_INFO);
		return info;
	}

	public void setActiveUser(User _activeUser)
	{
		m_activeUser = _activeUser;
	}

	public User getActiveUser()
	{
		return m_activeUser;
	}

	public void fetchUserInventory(String _userId)
	{
		UserInventoryWebService userData = new UserInventoryWebService();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.WebServices.UserData.USER_ID, _userId);
		userData.requestData(parameters);
	}

	public Inventory getUserInventory()
	{
		return (Inventory)m_dataResponse.get(Constants.Data.USER_INVENTORY);
	}

	public void fetchSearchResults(ItemSearchParameters _parameters)
	{
		ItemSearchWebService itemSearch = new ItemSearchWebService();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.WebServices.SearchData.SEARCH_PARAM, _parameters.toString());
		itemSearch.requestData(parameters);
	}

	public ItemSearchResult getSearchResults()
	{
		return (ItemSearchResult)m_dataResponse.get(Constants.Data.ITEM_SEARCH);
	}

	public void onPostDataSuccess(final short _id, Object _data, String _extra)
	{
		m_dataResponse.put(_id, _data);
		switch(_id)
		{
			case Constants.Data.USER_SUMMARY_DATA:

				break;
			case Constants.Data.USER_INVENTORY:

				break;
			case Constants.Data.MARKET_INFO:
				Item item = getUserInventory().getItem(_extra);
				if(item != null)
					item.marketInfo = getMarketInfo();
				break;
		}
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				alertDataListeners(_id);
			}
		});
	}

	public void onPostDataFailure(final short _id, Object _data, String _extra)
	{
		m_dataResponse.put(_id, _data);
		switch(_id)
		{
		}
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				alertDataListeners(_id);
			}
		});
	}

	public void alertDataListeners(short _id)
	{
		ArrayList<FragmentDataListener> listeners = m_dataListeners.get(_id) == null? new ArrayList<FragmentDataListener>() : m_dataListeners.get(_id);
		for(FragmentDataListener listener : listeners)
		{
			listener.onDataComplete(_id);
		}
	}

	private void dataReceived(short _id, String _data)
	{
		m_dataResponse.put(_id, _data);
		m_dataTimeStamps.put(_id, System.currentTimeMillis());
	}

	public Object getData(short _id, boolean _refresh)
	{
		if(m_dataResponse.containsKey(_id) && (System.currentTimeMillis() - m_dataTimeStamps.get(_id) < dataExpireTime || _refresh))
			return m_dataResponse.get(_id);
		else
			return null;
	}

	public Object getData(short _id)
	{
		return getData(_id, false);
	}

	public void registerListener(FragmentDataListener _listener, short _id)
	{
		ArrayList<FragmentDataListener> listeners;
		if(m_dataListeners.containsKey(_id))
			listeners = m_dataListeners.get(_id);
		else
			listeners = new ArrayList<FragmentDataListener>();
		m_dataListeners.put(_id, listeners);
		if(!listeners.contains(_listener))
			listeners.add(_listener);
	}

	public void deregisterListener(FragmentDataListener _listener, short _id)
	{
		ArrayList<FragmentDataListener> listeners;
		if(m_dataListeners.containsKey(_id))
		{
			listeners = m_dataListeners.get(_id);
			listeners.remove(_listener);
		}
	}

	public String getString(int _id)
	{
		return D2OApplication.getContext().getString(_id);
	}

	public String loadRawText(int _id)
	{
		String data = AssetLoader.loadRawText(_id);
		return data;
	}
}
