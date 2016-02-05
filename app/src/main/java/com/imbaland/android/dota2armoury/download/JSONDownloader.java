package com.imbaland.android.dota2armoury.download;

import android.os.AsyncTask;
import com.imbaland.android.dota2armoury.application.D2OApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/28
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONDownloader  extends AsyncTask<String, Integer, String>
{
	JSONDownloaderListener m_listener;
	short m_id;
	HashMap<String, String> m_cookies;

	public JSONDownloader(JSONDownloaderListener _listener)
	{
		m_listener = _listener;
	}

	public JSONDownloader(short _id, JSONDownloaderListener _listener)
	{
		m_listener = _listener;
		m_id = _id;
	}

	public JSONDownloader(short _id, HashMap<String, String> _cookies, JSONDownloaderListener _listener)
	{
		m_listener = _listener;
		m_id = _id;
		m_cookies = _cookies;
	}

	@Override
	protected String doInBackground(String... params)
	{
		try
		{
//			String storedResponse = D2OApplication.getDatastore().getData(m_id);
//			if(storedResponse != null)
//				return storedResponse;
			URL url = new URL(params[0]);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			if(m_cookies != null)
			{
				String cookies = "";
				for (String key : m_cookies.keySet())
				{
					cookies = cookies+(key+"="+m_cookies.get(key)+";");
				}
				connection.setRequestProperty("Cookie", cookies);
			}
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");
			input.close();
			return sb.toString();
		}
		catch (Exception e)
		{
//			e.printStackTrace();
//			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result)
	{
		m_listener.onJSONDownloaded(m_id, result);
	}

	public interface JSONDownloaderListener
	{
		public void onJSONDownloaded(short _id, String data);
	}
}
