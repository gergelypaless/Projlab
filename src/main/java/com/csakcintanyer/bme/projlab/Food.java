package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class Food implements CollectableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Food.class.getName() );
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Food");
        c.changeEnergy(-1); // Item használata egy munka.
        // Foodot vettünk fel, ezért 1-el nő a testhőnk
        c.changeHealth(1);
    }
}
