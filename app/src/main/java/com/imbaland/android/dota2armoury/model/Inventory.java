package com.imbaland.android.dota2armoury.model;

import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
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
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Inventory extends WebServiceModel
{
	HashMap<String, Item> m_items;
	public int commonCount;
	public int uncommonCount;
	public int rareCount;
	public int mythicalCount;
	public int legendaryCount;
	public int immortalCount;
	public int arcanaCount;
	public int ancientCount;

	public Inventory()
	{
		m_items = new HashMap<String, Item>();
	}

	public void addItem(Item _item)
	{
		if(!m_items.containsKey(_item.identifier))
		{
			m_items.put(_item.identifier, _item);
			assignRarity(_item);
		}
	}

	public Item getItem(String _id)
	{
		return m_items.get(_id);
	}

	public String [] fetchIds(int _count)
	{
		return fetchIds(0, _count);
	}

	public int size()
	{
		return m_items.size();
	}

	public String [] fetchIds(int _start, int _count)
	{
		String [] ids = new String[_count];
		Iterator it = m_items.entrySet().iterator();
		for(int i = 0; i < _start; i++)
			it.next();
		for(int i = 0; i < _count ; i++)
		{
			ids[i] = (String)((Map.Entry)(it.next())).getKey();
		}
		return ids;
	}

	private void assignRarity(Item _item)
	{
		String rarity = _item.rarity;
		if(rarity.equals(Item.COMMON))
			commonCount++;
		else if(rarity.equals(Item.UNCOMMON))
			uncommonCount++;
		else if(rarity.equals(Item.RARE))
			rareCount++;
		else if(rarity.equals(Item.MYTHICAL))
			mythicalCount++;
		else if(rarity.equals(Item.LEGENDARY))
			legendaryCount++;
		else if(rarity.equals(Item.IMMORTAL))
			immortalCount++;
		else if(rarity.equals(Item.ARCANA))
			arcanaCount++;
		else if(rarity.equals(Item.ANCIENT))
			ancientCount++;
	}

	public static Inventory parseInventory(String data)
	{
		Inventory inventory = new Inventory();
		InventoryParser parser = new InventoryParser(inventory);
		parser.parseInventory(data);
		return inventory;
	}
}

class InventoryParser
{
	Inventory m_inventory;

	public InventoryParser()
	{
		m_inventory = new Inventory();
	}

	public InventoryParser(Inventory _inventory)
	{
		m_inventory = _inventory;
	}

	D2ODataStore dataStore = D2OApplication.getDatastore();
	public void parseInventory(String _json)
	{
		JsonObject items = (JsonObject)new com.google.gson.JsonParser().parse(_json);
		Iterator<Map.Entry<String, JsonElement>> iterator = items.entrySet().iterator();
		while (iterator.hasNext())
		{
			Map.Entry<String, JsonElement> entry = iterator.next();
			parseItem(entry.getKey(), (JsonObject)entry.getValue());
		}
	}

	private void parseItem(String _id, JsonObject _element)
	{
		Item item = new Item(_id);
		item.name = _element.get("name").getAsString();
		item.marketHashName = _element.get("market_hash_name").getAsString();
		item.marketName = _element.get("market_name").getAsString();
		item.description = _element.get("type").getAsString();
		item.marketable = _element.get("marketable").getAsString().equals("1");
		item.tradable = _element.get("tradable").getAsString().equals("1");
		item.commodity = _element.get("commodity").getAsString().equals("1");
		item.imageUrl = _element.get("icon_url").getAsString();
		if(_element.get("icon_url_large") != null)
			item.imageUrlLarge = _element.get("icon_url_large").getAsString();

		JsonArray tags = (JsonArray)_element.get("tags");
		for(int i = 0; i < tags.size(); i++)
		{
			String category = ((JsonObject)(tags.get(i))).get("category").getAsString();
			String value = ((JsonObject)(tags.get(i))).get("name").getAsString();
			String internalValue = ((JsonObject)(tags.get(i))).get("internal_name").getAsString();
			if("Quality".equals(category))
			{
				item.quality = value;
				item.internalQuality = internalValue;
			}
			else if("Slot".equals(category))
			{
				item.slot = value;
				item.internalSlot = internalValue;
			}
			else if("Rarity".equals(category))
			{
				item.rarity = value;
				item.internalRarity = internalValue;
			}
			else if("Type".equals(category))
			{
				item.type = value;
				item.internalType = internalValue;
			}
			else if("Hero".equals(category))
			{
				item.hero = value;
				item.internalHero = internalValue;
			}
		}

//		JsonArray description = (JsonArray)_element.get("descriptions");
//		for(int i = 0; i < tags.size(); i++)
//		{
//
//		}

		m_inventory.addItem(item);
	}
}
