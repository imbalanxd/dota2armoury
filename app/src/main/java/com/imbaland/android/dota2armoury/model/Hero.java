package com.imbaland.android.dota2armoury.model;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/06/28
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hero
{
	public String name;
	public String identifier;
	public String [] slots;

	public Hero(String _id)
	{
		identifier = _id;
	}

	public Hero()
	{

	}
}
