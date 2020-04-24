package com.csakcintanyer.bme.projlab;

public class FragileShovel extends CollectableItem implements UsableItem
{
	public void interactWithCharacter(Character c)
	{
		c.addItem(this);
	}
	
	public FragileShovel(IceBlock block)
	{
		super(block);
	}
	
	public boolean use(IceBlock block)
	{
		block.changeAmountOfSnow(-2);
		--durability;
		return durability == 0;
	}
	
	private int durability;
}
