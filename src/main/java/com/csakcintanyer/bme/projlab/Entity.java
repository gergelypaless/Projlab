package com.csakcintanyer.bme.projlab;

import java.io.Serializable;
import java.util.logging.Logger;

public abstract class Entity implements Serializable
{
	private static final Logger LOGGER = Logger.getLogger( Character.class.getName() );
	
	public abstract void move(Direction d);
	
	public void fallIn()
	{
		LOGGER.fine("Character fell in water");
		isInWater = true;
	}
	
	public void save()
	{
		LOGGER.fine("Character was saved");
		isInWater = false;
	}
	
	/* ezzel tudunk átadni a karakternek egy IceBlock-ot, a karakter ezen a blockon fog állni */
	public void setIceBlock(IceBlock block)
	{
		LOGGER.fine("Character is on a new IceBlock");
		this.block = block;
	}
	
	public boolean hasBullet()
	{
		return false;
	}
	
	public boolean hasFlare()
	{
		return false;
	}
	
	protected boolean isInWater; // vízben vagyunk-e?
	// a block amin a karakter áll
	protected IceBlock block;
}
