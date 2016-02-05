package com.imbaland.android.dota2armoury.webservice;

import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.model.ItemSearchResult;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/14
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemSearchWebService extends D2OWebService<ItemSearchResult>
{

	@Override
	public void requestData(HashMap<String, String> _parameters)
	{
		String searchParams = _parameters.get(Constants.WebServices.SearchData.SEARCH_PARAM);
		String requestURL = String.format(dataStore.getString(R.string.steam_item_search),searchParams);
		Downloader.downloadJSON(requestURL, Constants.Data.ITEM_SEARCH, this);
	}

	@Override
	protected ItemSearchResult processResponse(String _data)
	{
		ItemSearchResult searchResult = new ItemSearchResult();
		SearchResult result = parseSearchResult(_data);
		searchResult.setSuccess(result.success);
		String [] elements = result.results_html.split("<a");
		for(int i = 1; i < elements.length; i++)
		{
			String currentElement = elements[i];
			int startIndex = currentElement.indexOf("\"market_listing_item_name\"");
			String name = currentElement.substring(currentElement.indexOf(">", startIndex) + 1,
					currentElement.indexOf("<", startIndex));
			String image = currentElement.substring(currentElement.indexOf("http://steamcommunity-a.akamaihd.net/economy/image/") + 51,
					currentElement.indexOf("/62fx62f"));
			String price = null;
			searchResult.addItem(name, image, price);
		}
		return searchResult;
	}

	public static SearchResult parseSearchResult(String json)
	{
		try
		{
			Gson gson = new Gson();
			SearchResult result = gson.fromJson(json, SearchResult.class);
			return result;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	class SearchResult
	{
		public boolean success;
		public int start;
		public int pagesize;
		public int total_count;
		public String results_html;
	}
}
