package com.imbaland.android.dota2armoury.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/14
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemSearchResult extends WebServiceModel
{
	public ArrayList<Item> results;

	public ItemSearchResult()
	{
		results = new ArrayList<Item>();
	}

	public void addItem(String name, String image, String cost)
	{
		Item item = new Item();
		item.name = name;
		item.imageUrl = image;
		results.add(item);
	}

	public Item getItem(int _index)
	{
		return results.get(_index);
	}

	public int getCount()
	{
		return results.size();
	}
}
