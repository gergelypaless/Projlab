package project;

import java.util.logging.Logger;

public class Bullet implements CollectableItem // csak gyűjthető
{
    private static final Logger LOGGER = Logger.getLogger( Bullet.class.getName() );
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Bullet");
        c.setHasBullet();
    }
}
