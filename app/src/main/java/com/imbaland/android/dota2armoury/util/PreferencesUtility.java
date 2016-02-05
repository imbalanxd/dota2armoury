package com.imbaland.android.dota2armoury.util;

import android.content.SharedPreferences;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.model.User;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/08/02
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PreferencesUtility
{
	private static SharedPreferences preferences;
	private static String USERS = "USERS";

	public static void createdUserSharedPreferences()
	{
		if(preferences == null)
			preferences = D2OApplication.getContext().getSharedPreferences(USERS, 0);
	}

	public static void addUser(User _user)
	{
		createdUserSharedPreferences();
		if(preferences.contains(_user.getSteamID()))
			return;
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString("User" + getUserCount(), _user.getSteamID());
		editor.putString(_user.getSteamID(), _user.getPreferenceString());
		editor.commit();
	}

	public static ArrayList<User> getUsers()
	{
		createdUserSharedPreferences();
		ArrayList<User> userList = new ArrayList<User>();
		while(preferences.contains("User"+userList.size()))
		{
			userList.add(User.createFromPreferenceString(preferences.getString(preferences.getString("User"+userList.size(), null), null)));
		}
		return userList;
	}

	public static int getUserCount()
	{
		createdUserSharedPreferences();
		int count = 0;
		while(preferences.contains("User"+count))
			count++;
		return count;
	}
}
