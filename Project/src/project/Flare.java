package project;

import java.util.logging.Logger;

public class Flare implements CollectableItem
{
    private static final Logger LOGGER = Logger.getLogger( Flare.class.getName() );
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Flare");
        // a karakter felvett egy Flare-t
        c.setHasFlare();
    }
}
