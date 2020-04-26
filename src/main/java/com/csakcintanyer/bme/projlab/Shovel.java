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
        else if (block.getSnow() == 1) // csak 1 ho van rajta
            block.changeAmountOfSnow(-1);
        return false;
    }
    
    public void interactWithCharacter(Character c)
    {
        c.addItem(this); // hozzáadjuk a karakter inventoryjához mivel UsableItem
    }
}
