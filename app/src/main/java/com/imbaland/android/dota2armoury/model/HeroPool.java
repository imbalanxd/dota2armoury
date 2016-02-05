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
 * Date: 2014/06/28
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HeroPool
{
	HashMap<String, Hero> m_heroes;

	public HeroPool()
	{
		m_heroes = new HashMap<String, Hero>();
	}

	public void addHero(Hero _hero)
	{
		if(!m_heroes.containsKey(_hero.identifier))
			m_heroes.put(_hero.identifier, _hero);
	}

	public Hero getHero(String _id)
	{
		return m_heroes.get(_id);
	}

	public int size()
	{
		return m_heroes.size();
	}

	public static HeroPool parseHeroPool(String data)
	{
		HeroPool heroPool = new HeroPool();
		HeroPoolParser parser = new HeroPoolParser(heroPool);
		parser.parseHeroPool(data);
		return heroPool;
	}
}

class HeroPoolParser
{
	HeroPool m_heroPool;

	public HeroPoolParser()
	{
		m_heroPool = new HeroPool();
	}

	public HeroPoolParser(HeroPool _heroPool)
	{
		m_heroPool = _heroPool;
	}

	D2ODataStore dataStore = D2OApplication.getDatastore();
	public void parseHeroPool(String _json)
	{
		JsonArray items = (JsonArray)new com.google.gson.JsonParser().parse(_json);
		Iterator<JsonElement> iterator = items.iterator();
		while (iterator.hasNext())
		{
			JsonObject entry = (JsonObject)iterator.next();
			Iterator<Map.Entry<String,JsonElement>> dataIterator = entry.entrySet().iterator();
			Map.Entry<String, JsonElement> hero = dataIterator.next();
			parseHero(hero.getKey(), (JsonArray)hero.getValue());
		}
	}

	private void parseHero(String _id, JsonArray _element)
	{
		Hero hero = new Hero(_id);
		String [] slots = new String[_element.size()];
		for(int i = 0; i < slots.length; i++)
		{
			slots[i] = _element.get(i).getAsString();
		}
		hero.slots = slots;
		m_heroPool.addHero(hero);
	}
}
