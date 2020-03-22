package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class Flare implements CollectableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Flare.class.getName() );
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Flare");
        c.changeEnergy(-1); // Item használata egy munka.
        // a karakter felvett egy Flare-t
        c.setHasFlare();
    }
}
