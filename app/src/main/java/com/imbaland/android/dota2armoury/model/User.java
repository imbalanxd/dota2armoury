package com.imbaland.android.dota2armoury.model;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/28
 * Time: 11:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class User extends WebServiceModel
{
	public static String STATE_NORMAL = "STATE_NORMAL";
	public static String STATE_ACTIVE = "STATE_ACTIVE";
	public static String STATE_EXISTS = "STATE_EXISTS";
	public static String STATE_ENTRY = "STATE_ENTRY";

	private String steamid;
	private String personaname;
	private String avatarfull;
	private String profileurl;
	private String state;

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public String getSteamID() {
		return steamid;
	}

	public String getProfileurl() {
		return profileurl;
	}

	public String getName() {

		return personaname;
	}

	public String getImageURL() {
		return avatarfull;
	}

	public void setSteamID(String steamID) {
		this.steamid = steamID;
	}

	public void setName(String name) {
		this.personaname = name;
	}

	public void setImageURL(String imageURL) {
		this.avatarfull = imageURL;
	}

	public void setProfileURL(String profileURL){
		this.profileurl = profileURL;
	}

	public String getCustomID()
	{
		if(profileurl != null)
			return profileurl.substring(profileurl.indexOf("/id/") + 4, profileurl.length()-1);
		return null;
	}

	public String getPreferenceString()
	{
		return personaname+";"+steamid+";"+avatarfull+";"+profileurl;
	}

	public static User createFromPreferenceString(String _userString)
	{
		User user = new User();
		String [] data = _userString.split(";");
		user.setName(data[0]);
		user.setImageURL(data[2]);
		user.setSteamID(data[1]);
		user.setProfileURL(data[3]);
		return	user;
	}
}
