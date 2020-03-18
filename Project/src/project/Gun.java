package project;

import java.util.logging.Logger;

public class Gun implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Gun.class.getName() );
    
    public void Use(IceBlock block)
    {
        LOGGER.fine("Using Gun");
    }
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Gun");
        c.addItem(this);
    }
}
