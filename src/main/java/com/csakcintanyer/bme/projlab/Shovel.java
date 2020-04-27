package com.csakcintanyer.bme.projlab;

public class Shovel extends CollectableItem implements UsableItem
{
    
    public Shovel(IceBlock block)
    {
        super(block);
    }
    
    // item használata
    public boolean use(IceBlock block)
    {
        if (block.getSnow() > 1)
            block.changeAmountOfSnow(-2); // az ásóval 2 hóréteget tudunk eltakarítani
        else if (block.getSnow() == 1) // csak 1 réteg hó van rajta
            block.changeAmountOfSnow(-1);
        return false; // nem kell törölni a ásót használat után
    }
    
    public void interactWithCharacter(Character c)
    {
        c.addItem(this); // hozzáadjuk a karakter inventoryjához mivel UsableItem
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "shovel";
    }
    
}
