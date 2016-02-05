package com.imbaland.android.dota2armoury.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.adapters.InventoryGridAdapter;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.interfaces.HomeFragmentListener;
import com.imbaland.android.dota2armoury.model.Inventory;
import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.model.MarketInfo;
import com.imbaland.android.dota2armoury.ui.CustomViewPager;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/15
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryPagerFragment extends BaseFragment implements InventoryGridFragment.InventoryGridListener
{
	private CustomViewPager inventoryPager;
	private InventoryGridAdapter inventoryAdapter;
	private HomeFragmentListener m_listener;
	private Item m_currentItem;

	public InventoryPagerFragment(HomeFragmentListener _listener)
	{
		m_listener = _listener;
	}

	@Override
	protected void registerFragment()
	{
		m_fragID = Constants.Fragments.INVENTORY_FRAGMENT;
		registerForData(Constants.Data.MARKET_INFO);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_inventory_pager, container, false);
		inventoryPager = (CustomViewPager)m_view.findViewById(R.id.inventoryViewPager);
		inventoryAdapter = new InventoryGridAdapter(getActivity().getSupportFragmentManager(), dataStore.getUserInventory(), this);
		inventoryAdapter.setGridSize(4);
		inventoryPager.setAdapter(inventoryAdapter);
		inventoryPager.setCurrentItem(0);

		return m_view;
	}

	@Override
	public void itemClicked(String _itemIdentifier)
	{
		m_currentItem = dataStore.getUserInventory().getItem(_itemIdentifier);
		TextView itemName = (TextView)m_view.findViewById(R.id.itemName);
		itemName.setText(m_currentItem.name);
		TextView heroName = (TextView)m_view.findViewById(R.id.heroName);
		heroName.setText(m_currentItem.hero);
		TextView rarity = (TextView)m_view.findViewById(R.id.rarity);
		rarity.setText(m_currentItem.rarity);
		ImageView itemImage = (ImageView)m_view.findViewById(R.id.itemImage);
		itemImage.setImageDrawable(null);
		int itemSize = 300;
		Downloader.downloadBitmap(String.format(dataStore.getString(R.string.steam_item_image_square),
				m_currentItem.imageUrl, (int) (itemSize), (int) (itemSize * 0.8)), itemImage);
		dataStore.fetchItemMarketInfo(_itemIdentifier);
	}

	private void setMarketInfo(MarketInfo _info)
	{
		TextView itemName = (TextView)m_view.findViewById(R.id.price);
		itemName.setText(_info.median_price);
	}

	@Override
	public void onDataComplete(short _id)
	{
		switch(_id)
		{
			case Constants.Data.MARKET_INFO:
				Item inv = dataStore.getUserInventory().getItem(m_currentItem.identifier);
				setMarketInfo(m_currentItem.marketInfo);
				break;
		}
	}
}
