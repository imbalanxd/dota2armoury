package com.imbaland.android.dota2armoury.spinner;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.imbaland.android.dota2armoury.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/08
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemSpinner extends RelativeLayout
{
    static final int ORIENTATION_VERTICAL = 1;
    static final int ORIENTATION_HORIZONTAL = 2;

    private static float display_width = 160.0f;

    private SpinnerAdapter spinnerAdapter;
    private AdapterObserver adapterObserver = new AdapterObserver();
    private MotionDetector motionDetector = new MotionDetector();
    private int max_item_display = 10;
    private int firstDisplay, lastDisplay;
    private int spacing = 150;
    private ArrayList<SpinnerItem> itemList;

    private float scrollAmount = 0f;

    public ItemSpinner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public static ItemSpinner inflate(Context context)
    {
        return (ItemSpinner) LayoutInflater.from(context).inflate(
                R.layout.layout_item_spinner, null);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        if(spinnerAdapter != null)
        {
            initializeList();
        }
    }

    private void initializeList()
    {
        itemList = new ArrayList<SpinnerItem>(getCount());
        for(int i = 0; i < getCount() ; i++)
        {
            SpinnerItem item = new SpinnerItem(i);
            itemList.add(item);
        }
        for(int i = 0; i < max_item_display ; i++)
        {
            SpinnerItem item = itemList.get(max_item_display - 1 - i);
            SpinnerDisplay display = getView(i);
            item.attachDisplay(display);
            addView(display);
        }
        frontDisplay = itemList.get(0);
    }

    public int getCount()
    {
        return spinnerAdapter == null ? 0 : spinnerAdapter.getCount();
    }

    private SpinnerDisplay getView(int _index){
        SpinnerDisplay display = new SpinnerDisplay(getContext());
        View disp = spinnerAdapter.getView(_index, null, null, 1.0f);
        display.addView(disp);
        return display;
    }

    public void setAdapter(SpinnerAdapter _adapter)
    {
        if(spinnerAdapter != null)
            spinnerAdapter.unregisterDataSetObserver(adapterObserver);
        spinnerAdapter = _adapter;
        spinnerAdapter.registerDataSetObserver(adapterObserver);
        initializeList();
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h , oldw, oldh);
        resize(w, h);
    }

    private void resize(int width, int height)
    {
        for(int i = 0; i < getCount(); i++)
        {
            SpinnerItem item = itemList.get(i);
            item.scrollDisplay(width / 2.0f + (spacing * i) - display_width/2.0f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event)
    {
        motionDetector.motionEvent(_event);
        return true;
    }

    private void scrolled(float _scroll)
    {
        scrollAmount += _scroll;
        animatedDisplays(_scroll);
    }

    private void animatedDisplays(float _scroll)
    {
        for(int i = 0; i < getCount(); i++)
        {
            SpinnerItem item = itemList.get(i);
            item.scrollDisplay(item.m_location - _scroll);
        }
    }

    ArrayList<SpinnerItem> waitingQueue = new ArrayList<SpinnerItem>();
    ArrayList<SpinnerDisplay> displayStack = new ArrayList<SpinnerDisplay>();

    private void makeDisplayAvailable(SpinnerItem _item)
    {
        SpinnerDisplay display = _item.m_display;
        _item.detachDisplay();
        addDisplayToStack(display);
    }

    private void makeDisplayRequest(SpinnerItem _item)
    {
        if(displayStack.size() != 0)
        {
            _item.attachDisplay(displayStack.get(0));
        }
        else if(!_item.queued)
        {
            waitingQueue.add(_item);
            _item.queued = true;
        }
    }

    private void addDisplayToStack(SpinnerDisplay _display)
    {
        if(waitingQueue.size() != 0)
        {
            waitingQueue.get(0).attachDisplay(_display);
        }
        else
            displayStack.add(_display);
    }

    private void removeDisplayRequest(SpinnerItem _item)
    {
        waitingQueue.remove(_item);
        _item.queued = false;
    }

    private void displayRangeIncreased(int _index)
    {
        if(_index > lastDisplay)
            lastDisplay = _index;
        else if(_index < firstDisplay)
            firstDisplay = _index;

        Log.d("hello", firstDisplay +" --- "+ lastDisplay);
    }

    private void displayRangeReduced(int _index)
    {
        if(_index == lastDisplay)
            lastDisplay = _index - 1;
        else if(_index == firstDisplay)
            firstDisplay = _index + 1;

        Log.d("hello", firstDisplay +" --- "+ lastDisplay);
    }

    private void depthOrderDisplayRange()
    {
        int leftOfCenter = frontDisplay.index - firstDisplay;
        int rightOfCenter = lastDisplay - frontDisplay.index;
        for(int i = 0; i < Math.max(leftOfCenter, rightOfCenter); i++)
        {
            if(i < rightOfCenter)
            {
                itemList.get(lastDisplay - i).bringToFront();
            }
            if(i < leftOfCenter)
            {
                itemList.get(i + firstDisplay).bringToFront();
            }
        }
//        for(int i = firstDisplay; i < frontDisplay.index; i++)
//        {
//            itemList.get(i).bringToFront();
//        }
//        for(int i = lastDisplay; i > frontDisplay.index; i--)
//        {
//            itemList.get(i).bringToFront();
//        }
        frontDisplay.bringToFront();
    }

    private void removeDisplayFromStack(SpinnerDisplay _display)
    {
        displayStack.remove(_display);
    }

    public class AdapterObserver extends DataSetObserver
    {
        public void onChanged(){
            super.onChanged();
            initializeList();
        }

        public void onInvalidated(){
            super.onInvalidated();
        }
    }

    public class MotionDetector
    {
        private float x, y;
        private int orientation = ORIENTATION_HORIZONTAL;

        public void motionEvent(MotionEvent _event)
        {
            float deltaX = 0f, deltaY = 0f;
            if(_event.getAction() == MotionEvent.ACTION_DOWN)
            {
                x = _event.getX();
                y = _event.getY();
            }
            else if(_event.getAction() == MotionEvent.ACTION_MOVE)
            {
                deltaX = (1.0f * (x - _event.getX()));
                deltaY = (1.0f * (y - _event.getY()));
                x = _event.getX();
                y = _event.getY();

                switch(orientation)
                {
                    case ORIENTATION_HORIZONTAL:
                        scrolled(deltaX);
                        break;
                    case ORIENTATION_VERTICAL:
                        scrolled(deltaY);
                        break;
                }
            }
            else if(_event.getAction() == MotionEvent.ACTION_UP)
            {
                x = 0f;
                y = 0f;
            }
        }
    }

    class SpinnerItem
    {
        private SpinnerDisplay m_display;
        private float m_location, m_width, m_rotation = 0.0f, m_scale = 1.0f, m_displacement = 100.0f;
        int index;
        boolean queued = false;

        public SpinnerItem(int _index)
        {
            index = _index;
        }

        public void scrollDisplay(float _destination)
        {
            m_location = _destination;
            isVisible();
            calculateDisplacement();
            //calculateRotation();
            calculateScale();
            if(m_display != null)
            {
                m_display.scrollDisplay(_destination, m_rotation, m_scale, m_displacement);
            }
        }

        public void attachDisplay(SpinnerDisplay _display)
        {
            removeDisplayRequest(this);
            displayStack.remove(_display);
            m_display = _display;
            m_display.isActivated = false;
            m_width = m_display.getWidth();
            displayRangeIncreased(this.index);
            scrollDisplay(m_location);
        }

        public void detachDisplay()
        {
            if(m_display != null)
                m_display.isActivated = false;
            displayRangeReduced(this.index);
            m_display = null;
        }

        private void isVisible()
        {
            if(display_width == 0)
                return;
            switch(isOnScreen())
            {
                case 0://View is on screen
                    if(m_display == null)
                        makeDisplayRequest(this);
                    else {
                        m_display.isActivated = true;
                    }
                    break;
                case 1://View is off screen to the left
                    if(m_display != null){
                        m_display.isActivated = false;
                        makeDisplayAvailable(this);
                    }
                    break;
                case 2://View is off screen to the right
                    if(m_display != null){
                        m_display.isActivated = false;
                        makeDisplayAvailable(this);
                    }
                    break;
            }
        }

        public float getWidth()
        {
            return display_width * m_scale;
        }

        public float getHeight()
        {
            return 0f;
        }

        public float getLocation()
        {
            return m_location + (display_width * ((1.0f - m_scale)/2.0f));
        }

        public int isOnScreen()
        {
            if(getLocation() + getWidth() < 0)
            {
                return 1;
            }
            else if(getLocation() > ItemSpinner.this.getWidth())
            {
                return 2;
            }
            return 0;
        }

        private void calculateRotation()
        {
            float diff = (scrollAmount - (index * spacing));
            m_rotation = Math.min(Math.max(diff, -200.0f), 200.0f)/200.0f * 60.0f;
        }

        private void calculateScale()
        {
            m_scale = Math.abs(m_displacement * m_displacement) * -1.0f + 1.6f;
        }

        private void calculateDisplacement()
        {
            float diff = (scrollAmount - (index * spacing));
            float half = ItemSpinner.this.getWidth()/2.0f;
            if(half != 0.0f)
                m_displacement = Math.min(Math.max(diff, -(half)), half)/half;
            findFrontDisplay();
        }

        public void bringToFront()
        {
            if(this.m_display != null)
                this.m_display.bringToFront();
        }

        public void findFrontDisplay()
        {
            if(frontDisplay != null && this.m_display != null && frontDisplay != this && Math.abs(this.m_displacement) <= 1.0f && Math.abs(this.m_displacement) <= Math.abs(frontDisplay.m_displacement))
            {
                frontDisplay = this;
                depthOrderDisplayRange();
            }
        }
    }

    private SpinnerItem frontDisplay;
    class SpinnerDisplay extends LinearLayout
    {
        private int orientation = ORIENTATION_HORIZONTAL;

        private float m_displacement;
        private boolean inView = true;
        private boolean isActivated = true;

        SpinnerDisplay(Context _context)
        {
            super(_context);
        }

        public void scrollDisplay(float _destination, float _rotation, float _scale, float _displacement)
        {
            m_displacement = Math.abs(_displacement);
            switch(orientation)
            {
                case ORIENTATION_HORIZONTAL:
                    scrollDisplayHorizontal(_destination, _rotation, _scale);
                    break;
                case ORIENTATION_VERTICAL:
                    scrollDisplayVertical(_destination, _rotation, _scale);
                    break;
            }
        }

        private void scrollDisplayHorizontal(float _destination, float _rotation, float _scale)
        {
            ObjectAnimator animTrans = ObjectAnimator.ofFloat(this, "x", _destination);
            animTrans.setDuration(0);
            ObjectAnimator animRot = ObjectAnimator.ofFloat(this, "rotationY", _rotation);
            animRot.setDuration(0);
            ObjectAnimator animScaleX = ObjectAnimator.ofFloat(this, "scaleX", _scale);
            animScaleX.setDuration(0);
            ObjectAnimator animScaleY = ObjectAnimator.ofFloat(this, "scaleY", _scale);
            animScaleY.setDuration(0);
            AnimatorSet anim = new AnimatorSet();
            anim.playTogether(animTrans, animRot, animScaleX, animScaleY);
            anim.start();
        }

        private void scrollDisplayVertical(float _destination, float _rotation, float _scale)
        {

        }

        @Override
        protected void onSizeChanged (int w, int h, int oldw, int oldh)
        {
            super.onSizeChanged(w, h , oldw, oldh);
            display_width = w;
        }

        @Override
        public void onDraw (Canvas canvas)
        {
            if(isActivated)
            {
                //findFrontDisplay();
                super.onDraw(canvas);
            }
        }
    }
}
