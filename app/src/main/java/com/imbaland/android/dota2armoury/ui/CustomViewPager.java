package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/15
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomViewPager extends ViewPager
{
	boolean scrollState = true;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollingEnabled(boolean _state)
	{
		scrollState = _state;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.scrollState) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.scrollState) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}
}
