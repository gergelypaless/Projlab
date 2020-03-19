package project;

import java.util.logging.Logger;

public class Suit implements CollectableItem
{
    private static final Logger LOGGER = Logger.getLogger( Suit.class.getName() );
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Suit");
        
        // a karakter felvett egy Suit-ot.
        c.setHasSuit();
    }
}
