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
		durability = 3; // 3-szor lehet ásni vele
	}
	
	public boolean use(IceBlock block)
	{
		block.changeAmountOfSnow(-2); // ásóval 2 réteg havat takarítunk
		--durability; // törékeny ásó élete csökken
		return durability == 0; // eltört-e már az ásó és törölni kell-e
	}
	
	public String toString()
	{
		return "fragileshovel";
	}
	
	private int durability; //annak az értéke, hogy hányszor lehet ásni az ásóval
}
