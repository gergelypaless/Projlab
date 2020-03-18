package project;

import java.util.logging.Logger;

public class Rope implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Rope.class.getName() );
    
    public void Use(IceBlock block)
    {
        LOGGER.fine("Using Rope");
    }
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Rope");
        c.addItem(this);
    }
}
