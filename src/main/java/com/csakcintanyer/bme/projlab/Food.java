package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class Food extends CollectableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Food.class.getName() );
    
    public Food(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Food");
        // Foodot vettünk fel, ezért 1-el nő a testhőnk
        c.changeHealth(1);
    }
}
