package com.imbaland.android.dota2armoury.download;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/21
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Downloader
{
	public static java.net.CookieManager cookieManager = new java.net.CookieManager();
	static HashMap<String, Object> waitingViews = new HashMap<String, Object>();
	static HashMap<String, Object> dataCache = new HashMap<String, Object>();

	static BitmapDownloader.BitmapDownloaderListener bitmapDownloaderListener =
			new BitmapDownloader.BitmapDownloaderListener(){
				public void onImageDownloaded(Bitmap bmp, String url){
					dataCache.put(url, bmp);
					for(ImageView view : ((ArrayList<ImageView>)(waitingViews.get(url))))
					{
						view.setImageBitmap(bmp);
					}
				}};
	static ImageLoader imageLoader = ImageLoader.getInstance();
	static DisplayImageOptions options = new DisplayImageOptions.Builder()
		.cacheOnDisk(true)
		.build();

//	static public ImageView downloadBitmap(String url, ImageView view)
//	{
//		if(dataCache.containsKey(url))
//		{
//			view.setImageBitmap((Bitmap)dataCache.get(url));
//			return view;
//		}
//		else if(waitingViews.containsKey(url))
//		{
//			((ArrayList<ImageView>)(waitingViews.get(url))).add(view);
//			return view;
//		}
//		else
//		{
//			ArrayList<ImageView> viewList = new ArrayList<ImageView>();
//			viewList.add(view);
//			waitingViews.put(url, viewList);
//		}
//
//		BitmapDownloader downloader = new BitmapDownloader(bitmapDownloaderListener);
//		downloader.execute(url);
//		return view;
//	}

	static public ImageView downloadBitmap(String url, ImageView view)
	{
		imageLoader.displayImage(url, view, options);
		return view;
	}

	static public void downloadJSON(String _url, JSONDownloader.JSONDownloaderListener _listener)
	{
		JSONDownloader downloader = new JSONDownloader( _listener);
		downloader.execute(_url);
	}

	static public void downloadJSON(String _url, short _id, JSONDownloader.JSONDownloaderListener _listener)
	{
        Log.d("WEBSERVICE", "Requested "+_url);
		JSONDownloader downloader = new JSONDownloader(_id, _listener);
		downloader.execute(_url);
	}

	static public void downloadJSON(String _url, short _id, JSONDownloader.JSONDownloaderListener _listener, HashMap<String, String> _cookies)
	{
		JSONDownloader downloader = new JSONDownloader(_id, _cookies, _listener);
		downloader.execute(_url);
	}
}
