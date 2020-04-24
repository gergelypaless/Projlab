package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class Suit extends CollectableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Suit.class.getName() );
    
    public Suit(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Suit");
        // a karakter felvett egy Suit-ot.
        c.setHasSuit();
    }
}
