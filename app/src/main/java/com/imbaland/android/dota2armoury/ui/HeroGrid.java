package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.assets.SpriteSheet;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.util.DisplayUtility;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/12
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class HeroGrid extends GridLayout
{
	D2ODataStore dataStore = D2OApplication.getDatastore();
	private SpriteSheet m_heroSpriteSheet;
	int gridDimension = 4;
	String[] items;
	int itemSize;

	public HeroGrid(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public static HeroGrid inflate(Context context)
	{
		return (HeroGrid) LayoutInflater.from(context).inflate(
				R.layout.layout_hero_grid, null);
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		m_heroSpriteSheet = new SpriteSheet(D2OApplication.getContext(), "Heroes");
		m_heroSpriteSheet.alphabetise();

		int [] screenSize = DisplayUtility.getDisplaySize();
		itemSize = screenSize[0]/gridDimension;
	}

	public void initDisplay()
	{
		for(int i = 0; i < gridDimension * gridDimension; i++)
		{
			ImageView heroDisplay = new ImageView(getContext());
			heroDisplay.setImageBitmap(m_heroSpriteSheet.getSprite(i));
			GridLayout.LayoutParams params =new GridLayout.LayoutParams();
			params.columnSpec = GridLayout.spec(i % gridDimension);
			params.rowSpec = GridLayout.spec(i / gridDimension);
			params.height = 100;
			params.width = 100;
			heroDisplay.setScaleType(ImageView.ScaleType.FIT_XY);
			heroDisplay.setLayoutParams(params);
			this.addView(heroDisplay);
		}
	}
}