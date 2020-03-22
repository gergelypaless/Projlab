package project;

import java.util.logging.Logger;

public class Shovel implements CollectableItem, UsableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Shovel.class.getName() );

    // item használata
    public void use(IceBlock block)
    {
        LOGGER.fine("Using Shovel");
        block.changeAmountOfSnow(-2); // az ásóval 2 hóréteget tudunk eltakarítani
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Shovel");
        c.changeEnergy(-1); // Item használata egy munka.
        c.addItem(this); // hozzáadjuk a karakter inventoryjához mivel UsableItem
    }
}
