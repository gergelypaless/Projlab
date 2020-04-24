package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class Flare extends CollectableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Flare.class.getName() );
    
    public Flare(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Flare");
        // a karakter felvett egy Flare-t
        c.setHasFlare();
    }
}
