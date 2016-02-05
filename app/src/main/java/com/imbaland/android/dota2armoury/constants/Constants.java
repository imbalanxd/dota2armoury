package com.imbaland.android.dota2armoury.constants;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Constants
{
	public static class Intents
	{
		public static final String BASE_INTENT = "com.imbaland.android.dota2armoury.base";

		public static final String [] FILTER_ARRAY = {BASE_INTENT};
	}

	public static class Fragments
	{
		public static final short HOME_FRAGMENT = 0;
		public static final short INVENTORY_FRAGMENT = 1;
        public static final short SPINNER_FRAGMENT = 4;
	}

	public static class Menu
	{

	}

	public static class Data
	{
		public static final short USER_SUMMARY_DATA = 0;
		public static final short USER_INVENTORY = 1;
		public static final short ITEM_SEARCH = 2;
		public static final short MARKET_INFO = 3;
	}

	public static class Error
	{
		public static final String INVALID_LOGIN = "invalid.login";
	}

	public static class LogState
	{
		public static final int UNKNOWN = -1;
		public static final int LOGGED_OUT = 0;
		public static final int LOGGED_IN = 1;
		public static final int LOGIN_AS = 2;
	}

	public static class WebServices
	{
		public static final String EXTRA = "EXTRA";

		public static class UserData
		{
			public static final String USER_ID = "USER_ID";
		}

		public static class SearchData
		{
			public static final String SEARCH_PARAM = "SEARCH_PARAM";
			public static final String SEARCH_START = "SEARCH_START";
			public static final String SEARCH_COUNT = "SEARCH_COUNT";
		}

		public static class ItemData
		{
			public static final String ITEM_NAME = "ITEM_NAME";
		}
	}
}
