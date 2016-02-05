package com.imbaland.android.dota2armoury.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.ImageView;
import com.google.gson.Gson;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/14
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteSheet
{
	Bitmap m_spriteSheet;
	HashMap<String, String> m_map;
	String [] keySet;
	Context m_context;

	public SpriteSheet(Context _context, String _sheet)
	{
		m_context = _context;
		m_spriteSheet =	AssetLoader.loadImage(_sheet+".png");
		Gson gson=new Gson();
		m_map = new HashMap<String, String>();
		m_map=(HashMap<String,String>) gson.fromJson(AssetLoader.loadText(_sheet+".json"), m_map.getClass());
		createKeySet();
	}

	protected void createKeySet()
	{
		Object [] objects = m_map.keySet().toArray();
		keySet = Arrays.copyOf(objects, objects.length, String[].class);
	}

	public void alphabetise()
	{
		ArrayList<String> set = new ArrayList<String>(Arrays.asList(keySet));
		Collections.sort(set, new Comparator<String>()
		{
			@Override
			public int compare(String name1, String name2)
			{
				return name1.compareTo(name2);
			}
		});
		keySet = Arrays.copyOf(set.toArray(), set.size(), String[].class);
	}

	private Rect getBoundaries(String _name)
	{
		String [] values = m_map.get(_name.replace(" ","").replace("-", "").toLowerCase()).split(" ");
		int x = Integer.parseInt(values[0]);
		int y = Integer.parseInt(values[1]);
		int width = Integer.parseInt(values[2]);
		int height = Integer.parseInt(values[3]);
		return new Rect(x, y, x + width, y + height);
	}

	public Bitmap getSprite(String _name)
	{
		Rect source = getBoundaries(_name);
		Bitmap sprite = Bitmap.createBitmap(m_spriteSheet, source.left, source.top, source.width(), source.height());
		return sprite;
	}

	public Bitmap getSprite(int _index)
	{
		Rect source = getBoundaries(keySet[_index]);
		Bitmap sprite = Bitmap.createBitmap(m_spriteSheet, source.left, source.top, source.width(), source.height());
		return sprite;
	}

	public ImageView getImage(String _name)
	{
		SpriteSheetImage image = new SpriteSheetImage(m_context, this, _name);
		return image;
	}

	public ImageView getImage(int _index)
	{
		SpriteSheetImage image = new SpriteSheetImage(m_context, this, keySet[_index]);
		return image;
	}

	public void draw(Canvas _canvas, String _name)
	{
		Rect source = getBoundaries(_name);
		Rect destination = new Rect(0,0,source.width(), source.height());
		_canvas.drawBitmap(m_spriteSheet, source, destination, null);
	}
}
