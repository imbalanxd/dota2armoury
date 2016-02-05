package com.imbaland.android.dota2armoury.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.imbaland.android.dota2armoury.application.D2OApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/28
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayUtility
{
	private static final int LOW_DPI_STATUS_BAR_HEIGHT = 19;
	private static final int MEDIUM_DPI_STATUS_BAR_HEIGHT = 25;
	private static final int HIGH_DPI_STATUS_BAR_HEIGHT = 38;

	public static float convertDpToPixel(float dp)
	{
		Resources resources = D2OApplication.getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public static float convertPixelsToDp(float px)
	{
		Resources resources = D2OApplication.getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	public static int getStatusBarSize()
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager) D2OApplication.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

		int statusBarHeight;

		switch (displayMetrics.densityDpi) {
			case DisplayMetrics.DENSITY_HIGH:
				statusBarHeight = HIGH_DPI_STATUS_BAR_HEIGHT;
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
				break;
			case DisplayMetrics.DENSITY_LOW:
				statusBarHeight = LOW_DPI_STATUS_BAR_HEIGHT;
				break;
			default:
				statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
		}
		return (int)DisplayUtility.convertDpToPixel(statusBarHeight);
	}

	public static int [] getDisplaySize()
	{
		int [] response = new int[2];
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager) D2OApplication.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

		response[0] = displayMetrics.widthPixels;
		response[1] = displayMetrics.heightPixels;
		return response;
	}
}
