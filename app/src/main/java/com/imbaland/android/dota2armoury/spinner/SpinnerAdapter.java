package com.imbaland.android.dota2armoury.spinner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Imbalanxd on 2015-02-08.
 */
public abstract class SpinnerAdapter<T> extends BaseAdapter
{
    private ArrayList<T> m_data;

    public void setData(ArrayList<T> _data)
    {
        m_data = _data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return m_data == null ? 0 : m_data.size();
    }

    @Override
    public Object getItem(int i) {
        return m_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup, 1.0f);
    }

    public abstract View getView(int i, View view, ViewGroup viewGroup, float _quality);
}
