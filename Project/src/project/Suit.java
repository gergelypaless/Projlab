package project;

import java.util.logging.Logger;

public class Suit implements CollectableItem
{
    private static final Logger LOGGER = Logger.getLogger( Suit.class.getName() );
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Suit");

        c.changeEnergy(-1); // Item használata egy munka.
        // a karakter felvett egy Suit-ot.
        c.setHasSuit();
    }
}
