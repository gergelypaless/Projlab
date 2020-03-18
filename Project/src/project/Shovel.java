package project;

import java.util.logging.Logger;

public class Shovel implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Shovel.class.getName() );
    
    public void Use(IceBlock block)
    {
        LOGGER.fine("Using Shovel");
        block.changeAmountOfSnow(-2);
    }
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Shovel");
        c.addItem(this);
    }
}
