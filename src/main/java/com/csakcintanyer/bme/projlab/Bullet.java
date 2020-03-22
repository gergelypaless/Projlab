package com.csakcintanyer.bme.projlab;

import java.util.logging.Logger;

public class Bullet implements CollectableItem // csak gyűjthető
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Bullet.class.getName() );
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Bullet");
        c.changeEnergy(-1); // Item használata egy munka.
        /* ezzel a függvénnyel mondjuk meg a karakternek, hogy mostantol nála van a Bullet
         * Bulletből az egész játék során csak egy példány van
         */
        c.setHasBullet();
    }
}
