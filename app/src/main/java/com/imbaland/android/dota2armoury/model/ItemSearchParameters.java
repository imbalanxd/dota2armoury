package com.imbaland.android.dota2armoury.model;

import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/14
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemSearchParameters
{
	private String query;
	private	String hero;
	private	String slot;
	private String quality = "tag_unique";
	private int start = 0;
	private int count = 50;
    private String sort_type = "price";
    private String sort_order = "desc";

	public ItemSearchParameters()
	{

	}

	public ItemSearchParameters(String _query, String _hero, String _slot)
	{
		query = _query;
		hero = _hero;
		slot = _slot;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public void setQuality(String quality){
		this.quality = quality;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString()
	{
		return getStartParameter() + getCountParameter() + getQueryParameter() + getHeroParameter() + getSlotParameter() + getQualityParameter() + getSort();
	}

	private String getHeroParameter()
	{
		if(hero != null)
		{
			return String.format(getString(R.string.search_param_hero), "%","%", hero);
		}
		return "";
	}

	private String getSlotParameter()
	{
		if(slot != null)
		{
			return String.format(getString(R.string.search_param_slot),  "%","%", slot);
		}
		return "";
	}

	private String getQualityParameter()
	{
		if(quality != null)
		{
			return String.format(getString(R.string.search_param_quality),  "%","%", quality);
		}
		return "";
	}

	private String getQueryParameter()
	{
		if(query != null)
		{
			return String.format(getString(R.string.search_param_query), query);
		}
		return "";
	}

	private String getStartParameter()
	{
		return String.format(getString(R.string.search_param_start), start);
	}

	private String getCountParameter()
	{
		return String.format(getString(R.string.search_param_count), count);
	}

    private String getSort()
    {
        return String.format(getString(R.string.search_param_sort_column), sort_type) + String.format(getString(R.string.search_param_sort_dir), sort_order);
    }

    private String getString(int _resId)
    {
        return D2OApplication.getDatastore().getString(_resId);
    }
}
