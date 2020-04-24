package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class Shovel extends CollectableItem implements UsableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Shovel.class.getName() );
    
    public Shovel(IceBlock block)
    {
        super(block);
    }
    
    // item használata
    public boolean use(IceBlock block)
    {
        LOGGER.fine("Using Shovel");
        block.changeAmountOfSnow(-2); // az ásóval 2 hóréteget tudunk eltakarítani
        return false;
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Shovel");
        c.addItem(this); // hozzáadjuk a karakter inventoryjához mivel UsableItem
    }
}
