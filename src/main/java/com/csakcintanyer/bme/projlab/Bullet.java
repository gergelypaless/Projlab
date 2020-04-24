package com.csakcintanyer.bme.projlab;

import java.util.logging.Logger;

public class Bullet extends CollectableItem // csak gyűjthető
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Bullet.class.getName() );
    
    public Bullet(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Bullet");
        /* ezzel a függvénnyel mondjuk meg a karakternek, hogy mostantol nála van a Bullet
         * Bulletből az egész játék során csak egy példány van
         */
        c.setHasBullet();
    }
}
