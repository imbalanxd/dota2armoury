package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.util.DisplayUtility;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.model.Inventory;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/12
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryGrid extends GridLayout
{
	D2ODataStore dataStore = D2OApplication.getDatastore();
	Inventory inventory;
	int gridDimension = 4;
	String[] items;
	int itemSize;
	double quality = 0.4;

	public InventoryGrid(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public static InventoryGrid inflate(Context context)
	{
		return (InventoryGrid) LayoutInflater.from(context).inflate(
				R.layout.layout_inventory_grid, null);
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

		int [] screenSize = DisplayUtility.getDisplaySize();
		itemSize = screenSize[0]/gridDimension;
	}

	public void setItemData(String[] _items)
	{
		items = _items;
		inventory = dataStore.getUserInventory();
		for(int i = 0; i < _items.length; i++)
		{
			ImageView itemDisplay = new ImageView(D2OApplication.getContext());
			itemDisplay.setScaleType(ImageView.ScaleType.FIT_XY);
			GridLayout.LayoutParams params =new GridLayout.LayoutParams();
			params.columnSpec = GridLayout.spec(i % gridDimension);
			params.rowSpec = GridLayout.spec(i / gridDimension);
			params.height = (int)(itemSize * 0.8);//LayoutParams.WRAP_CONTENT;
			params.width = itemSize;//LayoutParams.WRAP_CONTENT;
			itemDisplay.setLayoutParams(params);
			this.addView(itemDisplay);
			Downloader.downloadBitmap(String.format(dataStore.getString(R.string.steam_item_image_square),
					inventory.getItem(items[i]).imageUrl, (int)(itemSize * quality), (int)(itemSize * 0.8 * quality)), itemDisplay);
		}
	}

	public String getItemAt(float x, float y)
	{
		return items[(int)(Math.floor(x/itemSize) + (Math.floor(y/itemSize) * gridDimension))];
	}
}
