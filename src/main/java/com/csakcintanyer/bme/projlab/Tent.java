package com.csakcintanyer.bme.projlab;

public class Tent extends CollectableItem implements UsableItem
{
	public void interactWithCharacter(Character c)
	{
		// a karakter felvesz egy sátrat
		c.addItem(this);
	}
	
	public boolean use(IceBlock block)
	{
		if (!block.placeTent()) // ha van rajta tent akkor nem lehet lerakni
			throw new IllegalArgumentException("Cannot place tent");
		
		return true;
	}
	
	// tent kirajzolása
	public void draw(int x, int y)
	{
		View view = View.get();
		view.draw(view.tentIcon, x, y);
	}
	
	// kiíráshoz kell
	public String toString()
	{
		return "tent";
	}
}
