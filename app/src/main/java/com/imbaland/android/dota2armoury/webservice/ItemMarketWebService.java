package com.imbaland.android.dota2armoury.webservice;


import android.text.Html;
import android.util.Log;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.model.MarketInfo;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/10/12
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class ItemMarketWebService  extends D2OWebService<MarketInfo>
{
	public ItemMarketWebService()
	{
		super(Constants.Data.MARKET_INFO);
	}

	@Override
	public void requestData(HashMap<String, String> _parameters)
	{
		String itemName = _parameters.get(Constants.WebServices.ItemData.ITEM_NAME);
		try{
		String requestURL = String.format(dataStore.getString(R.string.steam_price_overview), URLEncoder.encode(itemName, "utf-8"));
		setExtra(_parameters.get(Constants.WebServices.EXTRA));
		Downloader.downloadJSON(requestURL, Constants.Data.MARKET_INFO, this);}
		catch(Exception e){}
	}

	@Override
	protected MarketInfo processResponse(String _data)
	{
		return parseMarketInfo(_data);  //To change body of implemented methods use File | Settings | File Templates.
	}

	public static MarketInfo parseMarketInfo(String json)
	{
		try
		{
			Gson gson = new Gson();
			MarketInfo data = gson.fromJson(Html.fromHtml(json).toString(), MarketInfo.class);
			return data;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
