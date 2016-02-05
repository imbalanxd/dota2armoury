package com.imbaland.android.dota2armoury.webservice;

import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.model.User;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/08
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDataWebService extends D2OWebService<User>
{
	public UserDataWebService()
	{
		super(Constants.Data.USER_SUMMARY_DATA);
	}

	@Override
	public void requestData(HashMap<String, String> _parameters)
	{
		String userID = _parameters.get(Constants.WebServices.UserData.USER_ID);
		String requestURL = String.format(dataStore.getString(R.string.steam_user_summary), dataStore.getString(R.string.steam_api_key), userID);
		Downloader.downloadJSON(requestURL, Constants.Data.USER_SUMMARY_DATA, this);
	}

	@Override
	protected User processResponse(String _data)
	{
		User[] users = parseUser(_data);
		if(users.length > 0)
			users[0].setSuccess(true);
		else
			return new User();

		return users[0];
	}

	public static User[] parseUser(String json)
	{
		try
		{
			json = json.substring(json.indexOf("["), json.indexOf("]") + 1).trim();
			Gson gson = new Gson();
			User[] user = gson.fromJson(json, User[].class);
			return user;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
