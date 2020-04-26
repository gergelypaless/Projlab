package com.csakcintanyer.bme.projlab;

import java.io.Serializable;

public abstract class Entity implements Serializable
{
	
	public abstract boolean move(Direction d);
	
	public void fallIn()
	{
		isInWater = true;
	}
	
	public void save()
	{
		isInWater = false;
	}
	
	/* ezzel tudunk átadni a karakternek egy IceBlock-ot, a karakter ezen a blockon fog állni */
	public void setIceBlock(IceBlock block)
	{
		this.block = block;
	}
	
	public IceBlock getBlock()
	{
		return block;
	}
	
	public boolean hasBullet()
	{
		return false;
	}
	
	public boolean hasFlare()
	{
		return false;
	}
	
	public boolean isInWater; // vízben vagyunk-e?
	// a block amin a karakter áll
	protected IceBlock block;
}
