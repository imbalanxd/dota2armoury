package com.imbaland.android.dota2armoury.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import com.imbaland.android.dota2armoury.fragment.InventoryGridFragment;
import com.imbaland.android.dota2armoury.fragment.InventoryGridFragment.InventoryGridListener;
import com.imbaland.android.dota2armoury.model.Inventory;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/14
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryGridAdapter extends FragmentPagerAdapter
{
	private Inventory inventory;
	private int gridSize;
	private	InventoryGridListener listener;


	public InventoryGridAdapter(FragmentManager _fm, Inventory _inventory, InventoryGridListener _listener)
	{
		super(_fm);
		inventory = _inventory;
		listener = _listener;
	}

	public void setGridSize(int _size)
	{
		gridSize = _size;
	}

	@Override
	public Fragment getItem(int i)
	{
		InventoryGridFragment fragment = new InventoryGridFragment(gridSize, i);
		fragment.setListener(listener);
		return fragment;
	}

	@Override
	public int getCount()
	{
		return (int)(Math.ceil(inventory.size() / (gridSize * gridSize)));
	}
}
