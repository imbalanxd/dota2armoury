package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.imbaland.android.dota2armoury.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/08/17
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ColourContentBar extends LinearLayout
{
	ArrayList<ColourContent> contents;
	String nameList = "";
	Context m_context;

	public ColourContentBar(Context context) {
		super(context);
		m_context = context;
		init();
	}

	public ColourContentBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		m_context = context;
		init();
	}

	public ColourContentBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		m_context = context;
		init();
	}

	public void init()
	{
		this.setOrientation(HORIZONTAL);
	}

	public void addContent(String _name, int _count, int _colour)
	{
		if(contents == null)
			contents = new ArrayList<ColourContent>();
		if(nameList.contains("|"+_name+"|"))
		{
			for(ColourContent content : contents)
				if(content.name.equals(_name))
					content.count+= _count;

		}
		else
		{
		//	contents.add(new ColourContent(_name, _count, _colour).createView(2, m_context));
			nameList+="|"+_name+"|";
		}

	}

	public ColourContent createColourContent(String _name, int _count, int _colour)
	{
		return new ColourContent(_name, _count, _colour);
	}

	public void setContents(ArrayList<ColourContent> _contents)
	{
		contents = _contents;
	}

	public void complete()
	{
		contents.get(contents.size() - 1).setPosition(ColourContent.START);
		this.addView(contents.get(contents.size() - 1).createView(ColourContent.START, contents.get(contents.size() - 1).count ,m_context));
		for(int i = 1; i < contents.size() - 1; i++)
		{
			this.addView(contents.get(contents.size() - 1 - i).createView(ColourContent.MIDDLE, contents.get(contents.size() - 1 - i).count ,m_context));
		}
		this.addView(contents.get(0).createView(ColourContent.END, contents.get(0).count ,m_context));
	}

	public void reset()
	{
		contents = new ArrayList<ColourContent>();
		nameList = "";
		removeAllViews();
	}
}

class ColourContent
{
	public static int START = 0;
	public static int END = 2;
	public static int MIDDLE = 1;

	public String name;
	public int count;
	public int colour;
	public int position;
	public View view;

	ColourContent(String _name, int _count, int _colour)
	{
		name = _name;
		count = _count;
		colour = _colour;
	}

	void setPosition(int position)
	{
		this.position = position;
	}

	public View createView(int _position, float weight, Context _context)
	{
		view = new View(_context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
		view.setLayoutParams(params);
		view.setMinimumWidth(10);
		if(_position == START)
		{

		}
		else if(_position == END)
		{

		}
		else
		{

		}
		Drawable drawable = _context.getResources().getDrawable(R.drawable.item_content_middle);
		drawable.setColorFilter(new PorterDuffColorFilter(colour, PorterDuff.Mode.MULTIPLY));
		view.setBackground(drawable);

		return view;
	}
}
