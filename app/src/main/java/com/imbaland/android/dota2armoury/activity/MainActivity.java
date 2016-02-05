package com.imbaland.android.dota2armoury.activity;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import com.imbaland.android.dota2armoury.R;
import com.imbaland.android.dota2armoury.application.D2OApplication;
import com.imbaland.android.dota2armoury.constants.Constants;
import com.imbaland.android.dota2armoury.datastore.D2ODataStore;
import com.imbaland.android.dota2armoury.fragment.BaseFragment;
import com.imbaland.android.dota2armoury.fragment.HomeFragment;
import com.imbaland.android.dota2armoury.fragment.SpinnerFragment;

public class MainActivity extends FragmentActivity
{
	private IntentReceiver receiver;
	private D2ODataStore dataStore = D2OApplication.getDatastore();

	private LinearLayout fragmentContainer;
	private BaseFragment currentFragment;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		registerReceiver();

		showFragment(Constants.Fragments.SPINNER_FRAGMENT);
    }

	private void registerReceiver()
	{
		receiver = new IntentReceiver();
		IntentFilter filter = new IntentFilter();
		for(String intent : Constants.Intents.FILTER_ARRAY)
			filter.addAction(intent);
		this.registerReceiver(receiver, filter);
	}

	private void showFragment(short _id)
	{
		BaseFragment fragment;

		switch(_id)
		{
			case Constants.Fragments.HOME_FRAGMENT:
				if(currentFragment instanceof HomeFragment)
					return;
				fragment = new HomeFragment();
				break;
            case Constants.Fragments.SPINNER_FRAGMENT:
                if(currentFragment instanceof SpinnerFragment)
                    return;
                fragment = new SpinnerFragment();
                break;
			default:
				fragment = new BaseFragment();
				break;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if(currentFragment != null)
			ft.remove(currentFragment);
		ft.add(R.id.fragmentContainer, fragment);
		ft.commit();
		currentFragment = fragment;
	}

	class IntentReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
		}
	}
}
