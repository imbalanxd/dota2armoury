package com.imbaland.android.dota2armoury.webservice;

import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.model.Inventory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/08
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserInventoryWebService extends D2OWebService<Inventory>
{
	public UserInventoryWebService()
	{
		super(Constants.Data.USER_INVENTORY);
	}

	@Override
	public void requestData(HashMap<String, String> _parameters)
	{
		String userID = _parameters.get(Constants.WebServices.UserData.USER_ID);
		String requestURL = String.format(dataStore.getString(R.string.steam_user_inventory), userID);
		Downloader.downloadJSON(requestURL, Constants.Data.USER_INVENTORY, this);
	}

	@Override
	protected Inventory processResponse(String _data)
	{
		return parseInventory(_data);
	}

	private Inventory parseInventory(String json)
	{
		Inventory inventory = new Inventory();
		if(!json.contains("\"success\":true"))
			return inventory;
		json = json.substring(json.indexOf("\"rgDescriptions\"") + 17, json.indexOf(",\"more\""));
		inventory = Inventory.parseInventory(json);
		inventory.setSuccess(true);
		return inventory;
	}

	public static HashMap<String, Object> parse(String json)
	{
		try
		{
			JsonElement currentObject = new com.google.gson.JsonParser().parse(json);
			if(currentObject instanceof JsonObject)
			{
				JsonObject object = (JsonObject)currentObject;
				Set<Map.Entry<String, JsonElement>> set = object.entrySet();
				Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
				HashMap<String, Object> map = new HashMap<String, Object>();
				while (iterator.hasNext())
				{
					Map.Entry<String, JsonElement> entry = iterator.next();
					String key = entry.getKey();
					JsonElement value = entry.getValue();
					if (!value.isJsonPrimitive())
					{
						map.put(key, parse(value.toString()));
					}
					else
					{
						map.put(key, value.getAsString());
					}
				}
				return map;
			}
			else if(currentObject instanceof JsonArray)
			{
				return null;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
