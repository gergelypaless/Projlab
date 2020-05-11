package com.csakcintanyer.bme.projlab;

import java.io.Serializable;

public abstract class Entity implements Serializable, Drawable
{
	
	public abstract boolean move(Direction d);

	// az Entity beleesik a vízbe (medvénél nem fontos)
	public void fallIn()
	{
		isInWater = true;
	}

	// az Entity-t kihúzzák a vízből
	public void save()
	{
		isInWater = false;
	}
	
	/* ezzel tudunk átadni a karakternek egy IceBlock-ot, a karakter ezen a blockon fog állni */
	public void setIceBlock(IceBlock block)
	{
		this.block = block;
	}

	// visszaadja, hogy van-e az Entity mely IceBlock-on van jelenleg
	public IceBlock getBlock()
	{
		return block;
	}

	// visszaadja, hogy van-e az Entity-nél Bullet
	public boolean hasBullet()
	{
		return false;
	}

	// visszaadja, hogy van-e az Entity-nél Flare
	public boolean hasFlare()
	{
		return false;
	}
	
	public boolean isInWater; // vízben vagyunk-e?

	protected IceBlock block; // a block amin a karakter áll
}
