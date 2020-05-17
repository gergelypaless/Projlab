package com.csakcintanyer.bme.projlab;

public class FragileShovel extends CollectableItem implements UsableItem
{
	public void interactWithCharacter(Character c)
	{
		c.addItem(this);
	}
	
	public FragileShovel()
	{
		durability = 3; // 3-szor lehet ásni vele
	}
	
	public boolean use(IceBlock block)
	{
		if (block.getSnow() > 1)
		{
			block.changeAmountOfSnow(-2); // az ásóval 2 hóréteget tudunk eltakarítani
			--durability; // törékeny ásó élete csökken
		}
		else if (block.getSnow() == 1) // csak 1 réteg hó van rajta
		{
			block.changeAmountOfSnow(-1);
			--durability; // törékeny ásó élete csökken
		}
		else
			throw new IllegalArgumentException("Cannot use shovel");
		
		return durability == 0; // eltört-e már az ásó és törölni kell-e
	}
	
	// fragile shovel kirajzolása
	public void draw(int x, int y)
	{
		View view = View.get();
		view.draw(view.fragileShovelIcon, x, y);
	}
	
	// kiíráshoz kell
	public String toString()
	{
		return "fragileshovel";
	}
	
	private int durability; //annak az értéke, hogy hányszor lehet ásni az ásóval
}
