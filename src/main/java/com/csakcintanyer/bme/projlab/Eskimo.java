package com.csakcintanyer.bme.projlab;

import java.util.logging.Logger;

public class Eskimo extends Character
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger(Eskimo.class.getName());
    
    public Eskimo(int ID)
    {
        super(ID);
        LOGGER.finest("Eskimo constructor");
        health = 5;
    }
    
    //képesség használatát szolgáló függvény
    public void useAbility()
    {
        LOGGER.fine("Using character's ability");
    
        if (energy == 0)
            return;
        
        if (block.placeIgloo())
            changeEnergy(-1);
    }
}
