package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.interfaces.HomeFragmentListener;
import com.imbaland.android.dota2armoury.model.Inventory;
import com.imbaland.android.dota2armoury.model.User;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/08
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActiveUserDisplay extends LinearLayout
{
	private TextView m_playerAlias;
	private ImageView m_playerImage;
	private ColourContentBar m_itemRarityBar;
	private HomeFragmentListener m_listener;

	private User m_user;
	private boolean m_initialized = false;

	public ActiveUserDisplay(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public static ActiveUserDisplay inflate(Context context, User _user)
	{
		ActiveUserDisplay display = (ActiveUserDisplay) LayoutInflater.from(context).inflate(
				R.layout.layout_active_user, null);
		display.setUserData(_user);
		return display;
	}

	public static ActiveUserDisplay inflate(Context context)
	{
		return (ActiveUserDisplay) LayoutInflater.from(context).inflate(
				R.layout.layout_user_data, null);
	}

	public void setListener(HomeFragmentListener _listener)
	{
		m_listener = _listener;
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		m_playerAlias = (TextView)findViewById(R.id.currentUserName);
		m_playerImage = (ImageView)findViewById(R.id.currentUserImage);
		m_itemRarityBar = (ColourContentBar)findViewById(R.id.itemRarityBar);
		m_initialized = true;
		if(m_user != null)
			setUserData(m_user);
	}
	
	public void setUserData(User _user)
	{
		m_user = _user;
		if(m_initialized)
		{
			Downloader.downloadBitmap(_user.getImageURL(), m_playerImage);
			m_playerAlias.setText(_user.getName());
			createColourBar();
		}
	}

	private void createColourBar()
	{
		m_itemRarityBar.reset();
		Inventory inventory = D2OApplication.getDatastore().getUserInventory();
		ArrayList<ColourContent> contents = new ArrayList<ColourContent>();

		if(inventory.commonCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Common", inventory.commonCount, getResources().getColor(R.color.item_common)));
		if(inventory.uncommonCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Uncommon", inventory.uncommonCount, getResources().getColor(R.color.item_uncommon)));
		if(inventory.rareCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Rare", inventory.rareCount, getResources().getColor(R.color.item_rare)));
		if(inventory.mythicalCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Mythical", inventory.mythicalCount, getResources().getColor(R.color.item_mythical)));
		if(inventory.immortalCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Immortal", inventory.immortalCount, getResources().getColor(R.color.item_immortal)));
		if(inventory.legendaryCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Legendary", inventory.legendaryCount, getResources().getColor(R.color.item_legendary)));
		if(inventory.arcanaCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Aracana", inventory.arcanaCount, getResources().getColor(R.color.item_arcana)));
		if(inventory.ancientCount != 0)
			contents.add(m_itemRarityBar.createColourContent("Ancient", inventory.ancientCount, getResources().getColor(R.color.item_ancient)));

		m_itemRarityBar.setContents(contents);
		m_itemRarityBar.complete();
	}
}
