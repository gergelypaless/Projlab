package project;

import java.util.logging.Logger;

public class Bullet implements Item
{
    private static final Logger LOGGER = Logger.getLogger( Bullet.class.getName() );
    
    public void Use(IceBlock block)
    {
    }
    
    public Item PickedUp(Character c)
    {
        return null;
    }
}
