package com.imbaland.android.dota2armoury.interfaces;

import com.imbaland.android.dota2armoury.model.Item;
import com.imbaland.android.dota2armoury.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HomeFragmentListener
{
	public void userAccepted(User _user);
	public void itemSelected(Item _item);
}
