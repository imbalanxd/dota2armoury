package com.imbaland.android.dota2armoury.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.adapters.SearchItemListAdapter;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.model.ItemSearchParameters;
import com.imbaland.android.dota2armoury.model.ItemSearchResult;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/15
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadoutFragment extends BaseFragment implements View.OnClickListener
{
	private EditText searchText;
	private Button searchButton;
	private ListView itemList;

	@Override
	protected void registerFragment()
	{
		m_fragID = Constants.Fragments.HOME_FRAGMENT;
		registerForData(Constants.Data.ITEM_SEARCH);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_loadout, container, false);
		searchText = (EditText)m_view.findViewById(R.id.searchText);
		searchButton = (Button)m_view.findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);
		itemList = (ListView)m_view.findViewById(R.id.resultList);

		return m_view;
	}

	private void itemSearch()
	{
		ItemSearchParameters parameters = new ItemSearchParameters();
		parameters.setQuery(searchText.getText().toString());
		//parameters.setHero("tag_npc_dota_hero_pudge");
		//parameters.setSlot("tag_weapon");
		dataStore.fetchSearchResults(parameters);
	}

	private void setSearchResult(ItemSearchResult _data)
	{
		itemList.setAdapter(new SearchItemListAdapter(_data));
	}

	@Override
	public void onClick(View v)
	{
		if(v == searchButton)
		{
			itemSearch();
		}
	}

	@Override
	public void onDataComplete(short _id)
	{
		switch(_id)
		{
			case Constants.Data.ITEM_SEARCH:
				setSearchResult(dataStore.getSearchResults());
				break;
		}
	}
}
