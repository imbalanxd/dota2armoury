package com.imbaland.android.dota2armoury.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.adapters.DotaSpinnerAdapter;
import com.imbaland.android.dota2armoury.spinner.ItemSpinner;

/**
 * Created by Imbalanxd on 2015-01-09.
 */
public class SpinnerFragment extends BaseFragment
{
    ItemSpinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_view = inflater.inflate(R.layout.fragment_spinner, container, false);
        spinner = (ItemSpinner)m_view.findViewById(R.id.item_spinner);
        return m_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        DotaSpinnerAdapter adapter = new DotaSpinnerAdapter();
        spinner.setAdapter(adapter);
    }
}
