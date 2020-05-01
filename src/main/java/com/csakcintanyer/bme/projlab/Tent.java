package com.csakcintanyer.bme.projlab;

public class Tent extends CollectableItem implements UsableItem
{
	public Tent(IceBlock block)
	{
		super(block);
	}
	
	public void interactWithCharacter(Character c)
	{
		// a karakter felvesz egy sátrat
		c.addItem(this);
	}
	
	public boolean use(IceBlock block)
	{
		return block.placeTent();
	}
	
	// kiíráshoz kell
	public String toString()
	{
		return "tent";
	}
}
