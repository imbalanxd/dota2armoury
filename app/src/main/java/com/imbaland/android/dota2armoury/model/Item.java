package com.imbaland.android.dota2armoury.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/09
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Item
{
	public static String COMMON = "Common";
	public static String UNCOMMON = "Uncommon";
	public static String RARE = "Rare";
	public static String MYTHICAL = "Mythical";
	public static String LEGENDARY = "Legendary";
	public static String IMMORTAL = "Immortal";
	public static String ARCANA = "Arcana";
	public static String ANCIENT = "Ancient";

	public Item()
	{

	}

	public Item(String _id)
	{
		identifier = _id;
	}

	public String identifier;
	public String name;
	public String marketHashName;
	public String marketName;
	public String description;
	public boolean tradable;
	public boolean marketable;
	public boolean commodity;
	public String imageUrl;
	public String imageUrlLarge;
	public String hero;
	public String internalHero;
	public String slot;
	public String internalSlot;
	public String rarity;
	public String internalRarity;
	public String quality;
	public String internalQuality;
	public String type;
	public String internalType;
	public String setName;
	public ArrayList<String> setItems;
	public MarketInfo marketInfo;
}
