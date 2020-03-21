package project;

import java.util.logging.Logger;

public class Shovel implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Shovel.class.getName() );
    
    public void use(IceBlock block)
    {
        LOGGER.fine("Using Shovel");
        block.changeAmountOfSnow(-2);
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Shovel");
        c.changeEnergy(-1); // Item használata egy munka.
        c.addItem(this);
    }
}
