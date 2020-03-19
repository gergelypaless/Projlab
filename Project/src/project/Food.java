package project;

import java.util.logging.Logger;

public class Food implements CollectableItem
{
    private static final Logger LOGGER = Logger.getLogger( Food.class.getName() );
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Food");
        c.changeEnergy(-1); // Item használata egy munka.
        c.changeHealth(1);
    }
}
