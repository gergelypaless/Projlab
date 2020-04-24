package com.csakcintanyer.bme.projlab;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Gun extends CollectableItem implements UsableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Gun.class.getName() );
    
    public Gun(IceBlock block)
    {
        super(block);
    }
    
    // az item használata
    public boolean use(IceBlock block)
    {
        LOGGER.fine("Using Gun");
        
        boolean hasFlare = false, hasBullet = false;
        ArrayList<Entity> entities = block.getEntities();
        // megnézzük, hogy az ezen a blockon lévő játékosoknál van-e a Flare és a Bullet.
        for (Entity e : entities)
        {
            if (!hasFlare)
                hasFlare = e.hasFlare();
            if (!hasBullet)
                hasBullet = e.hasBullet();
        }
        // ha mindkettő megvan, akkor el tudjuk sütni a fegyvert, nyertünk
        if (hasBullet && hasFlare)
        {
            LOGGER.fine("Gun used");
            Game.get().win();
        }
        
        return false;
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Gun");
        c.addItem(this); // inventoryhoz adjuk az itemet, mert UsableItem
    }
}
