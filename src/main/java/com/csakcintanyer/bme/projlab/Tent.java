package com.csakcintanyer.bme.projlab;

public class Tent extends CollectableItem implements UsableItem
{
	public Tent(IceBlock block)
	{
		super(block);
	}
	
	public void interactWithCharacter(Character c)
	{
		c.addItem(this);
	}
	
	public boolean use(IceBlock block)
	{
		return block.placeTent();
	}
}
