package com.imbaland.android.dota2armoury.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.download.Downloader;
import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.model.ItemSearchResult;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/21
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchItemListAdapter extends BaseAdapter
{
	ItemSearchResult result;
	D2ODataStore dataStore;

	public SearchItemListAdapter(ItemSearchResult _result)
	{
		super();
		result =_result;
		dataStore = D2OApplication.getDatastore();
	}

	@Override
	public int getCount()
	{
		return result.getCount();
	}

	@Override
	public Object getItem(int position)
	{
		return result.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Item item = (Item)getItem(position);
		View view = null;
		if(view == null)
		{
			LayoutInflater inflater = (LayoutInflater) D2OApplication.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listrow_search, null);
		}
		TextView itemName = (TextView)view.findViewById(R.id.itemName);
		itemName.setText(item.name);
		ImageView itemImage = (ImageView)view.findViewById(R.id.itemImage);
		Downloader.downloadBitmap(String.format(dataStore.getString(R.string.steam_item_image_square),
				item.imageUrl, 64, 64), itemImage);

		return view;
	}
}
