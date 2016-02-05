package com.imbaland.android.dota2armoury.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.assets.SpriteSheet;
import com.imbaland.android.dota2armoury.ui.HeroGrid;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/07/06
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HeroFragment extends BaseFragment
{
	ScrollView heroScrollView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_hero, container, false);

		heroScrollView = (ScrollView)m_view.findViewById(R.id.heroScrollView);
		HeroGrid grid = HeroGrid.inflate(D2OApplication.getContext());
		heroScrollView.addView(grid);
		grid.initDisplay();

		return m_view;
	}
}
