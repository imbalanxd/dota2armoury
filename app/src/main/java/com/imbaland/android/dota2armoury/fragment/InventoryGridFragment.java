package com.imbaland.android.dota2armoury.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.ui.InventoryGrid;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/14
 * Time: 12:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryGridFragment extends BaseFragment implements View.OnTouchListener
{
	private int pageIndex;
	private int gridSize;
	private InventoryGrid inventoryGrid;
	private InventoryGridListener listener;

	public InventoryGridFragment(int _gridSize, int _pageIndex)
	{
		pageIndex = _pageIndex;
		gridSize = _gridSize;
	}

	@Override
	protected void registerFragment()
	{
		m_fragID = Constants.Fragments.HOME_FRAGMENT;
		registerForData(Constants.Data.USER_INVENTORY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_inventory_grid, container, false);

		LinearLayout inventoryContainer = (LinearLayout)m_view.findViewById(R.id.inventoryContainer);
		inventoryGrid = InventoryGrid.inflate(D2OApplication.getContext());
		inventoryContainer.addView(inventoryGrid);
		inventoryGrid.setItemData(dataStore.getUserInventory().fetchIds((gridSize * gridSize) * pageIndex, (gridSize * gridSize)));
		inventoryGrid.setOnTouchListener(this);

		return m_view;
	}

	public void setListener(InventoryGridListener _listener)
	{
		listener = _listener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if(listener != null && v instanceof InventoryGrid)
		{
			String itemName = inventoryGrid.getItemAt(event.getX(), event.getY());
			listener.itemClicked(itemName);
		}
		return false;
	}

	public interface InventoryGridListener
	{
		public void itemClicked(String _itemName);
	}
}
