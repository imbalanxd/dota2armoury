package com.imbaland.android.dota2armoury.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.util.DisplayUtility;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Drawer extends LinearLayout implements OnGlobalLayoutListener
{
	private boolean m_initialised = false;
	private boolean m_open = false;
	private boolean m_enabled = true;
	private int m_entry = 0;
	private int m_width, m_height;
	private int m_gap, m_lip;

	private long m_slideDuration = 750;
	private long m_durationPadding = 50;
	private boolean m_isAnimating = false;

	public Drawer(Context context)
	{
		super(context);
		this.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	public Drawer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		getAttributes(context, attrs);
		this.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	public Drawer(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		getAttributes(context, attrs);
		this.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	private void getAttributes(Context context, AttributeSet attrs)
	{
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Drawer, 0, 0);
		try
		{
			m_entry = ta.getInt(R.styleable.Drawer_entry, 0);
			m_gap = (int)ta.getDimension(R.styleable.Drawer_gap, 0);
			m_lip = (int)ta.getDimension(R.styleable.Drawer_lip, 0);
		}
		finally
		{
			ta.recycle();
		}
	}

	public boolean isInitialised()
	{
		return m_initialised;
	}

	public void setInitialised(boolean _initialised)
	{
		m_initialised = _initialised;
	}

	private void initialise()
	{
		m_width = getWidth();
		m_height = getHeight();
		obscure();
		//toggle();
	}

	private void obscure()
	{
		LayoutParams params = new LayoutParams(getWidth(), getHeight());
		int [] windowLocation = new int[2];
		this.getLocationInWindow(windowLocation);
		switch(m_entry)
		{
			case 1://top
				this.setTranslationY((-1)*windowLocation[1]);
				params.setMargins(0,(-1) * getHeight() + m_lip + DisplayUtility.getStatusBarSize(),0,0);
				break;
			case 2://bottom
				params.setMargins(0,0,0,(-1) * getHeight() + m_lip);
				break;
			case 3://left
				params.setMargins((-1) * getWidth() + m_lip,0,0,0);
				break;
			case 4://right
				params.setMargins(0,0,(-1) * getWidth() + m_lip,0);
				break;
		}
		this.setLayoutParams(params);
	}

	public boolean toggle()
	{
		if(m_isAnimating || !m_enabled)
			return false;
		this.clearAnimation();
		int delta = 0;
		switch(m_entry)
		{
			case 1://top
				delta = m_height + m_gap - m_lip;
				break;
			case 2://bottom
				delta = -m_height - m_gap;
				break;
			case 3://left

				break;
			case 4://right

				break;
		}

		if(m_open)
		{
			this.animate().translationYBy(-delta).setDuration(m_slideDuration).start();
		}
		else
		{
			this.animate().translationYBy(delta).setDuration(m_slideDuration).start();
		}
		m_isAnimating = true;
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Drawer.this.clearAnimation();
				m_isAnimating = false;
			}
		}, m_slideDuration + m_durationPadding);
		m_open = !m_open;
		return true;
	}

	public void setEnabled(boolean _enabled)
	{
		if(m_open & !_enabled)
			toggle();
		m_enabled = _enabled;
	}

	public boolean show()
	{
		if(!m_open)
			return toggle();
		return false;
	}

	public boolean hide()
	{
		if(m_open)
			return toggle();
		return false;
	}

	@Override
	public void onGlobalLayout()
	{
		if(m_width != getWidth() || m_height != getHeight())
			initialise();
		setInitialised(true);
	}

	public long getDuration() {
		return m_slideDuration;
	}

	public void setDuration(long _duration) {
		m_slideDuration = _duration;
	}

	public boolean isOpen()
	{
		return m_open;
	}
}
