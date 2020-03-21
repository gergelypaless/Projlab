package project;

import java.util.logging.Logger;

public class Bullet implements CollectableItem // csak gyűjthető
{
    private static final Logger LOGGER = Logger.getLogger( Bullet.class.getName() );
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Bullet");
        c.changeEnergy(-1); // Item használata egy munka.
        c.setHasBullet();
    }
}
