package com.imbaland.android.dota2armoury.webservice;

import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.download.JSONDownloader;
import com.imbaland.android.dota2armoury.model.WebServiceModel;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/08
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class D2OWebService<T> implements JSONDownloader.JSONDownloaderListener
{
	protected D2ODataStore dataStore;
	protected String dataExtra;

	public D2OWebService()
	{
		dataStore = D2OApplication.getDatastore();
	}

	public D2OWebService(short _id)
	{
		dataStore = D2OApplication.getDatastore();
		setIdentifier(_id);
	}
	private short m_identifier;
	protected void setIdentifier(short _id)
	{
		m_identifier = _id;
	}

	protected void setExtra(String _extra)
	{
		dataExtra = _extra;
	}

	public abstract void requestData(HashMap<String, String> _parameters);

	protected abstract T processResponse(String _data);

	@Override
	public void onJSONDownloaded(short _id, String _data)
	{
		T response = processResponse(_data);
		if(response != null && ((WebServiceModel)(response)).isSuccess())
			dataStore.onPostDataSuccess(_id, response, dataExtra);
		else
			dataStore.onPostDataFailure(_id, response, dataExtra);
	}
}
