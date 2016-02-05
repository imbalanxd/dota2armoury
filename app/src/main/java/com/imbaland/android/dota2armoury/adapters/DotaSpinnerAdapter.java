package com.imbaland.android.dota2armoury.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.spinner.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by Imbalanxd on 2015-02-13.
 */
public class DotaSpinnerAdapter extends SpinnerAdapter {

    public DotaSpinnerAdapter()
    {
        super();
        ArrayList<DotaObject> data = new ArrayList<DotaObject>();
        String [] heroes = {"Abaddon", "Alchemist", "Ancient Apparition", "Anti Mage", "Axe", "Bane", "Batrider",
                "Beastmaster", "Beastmaster", "Bloodseeker", "Bounty Hunter", "Brewmaster", "Bristleback", "Broodmother", "Centaur Warrunner",
                "Chaos Knight", "Chen", "Clinkz", "Clockwork", "Crystal Maiden", "Dark Seer"};
        for(String hero : heroes)
            data.add(new DotaObject(hero));
        setData(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, float _quality) {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) D2OApplication.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item, null);
        }
        ImageView image = (ImageView)view.findViewById(R.id.heroImage);
        Resources res = D2OApplication.getContext().getResources();
        DotaObject object = (DotaObject)getItem(i);
        image.setImageDrawable(res.getDrawable(res.getIdentifier(object.drawable, "drawable", D2OApplication.getContext().getPackageName())));
        return view;
    }

    public class DotaObject extends Object
    {
        public String name;
        public String drawable;

        public DotaObject(String _name, String _drawable){
            name = _name;
            drawable = _drawable;
        }

        public DotaObject(String _name){
            name = _name;
            drawable = _name.toLowerCase().replace(" ", "_");
        }
    }
}
