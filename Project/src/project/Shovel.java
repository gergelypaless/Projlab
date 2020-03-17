package project;

import java.util.logging.Logger;

public class Shovel implements Item
{
    private static final Logger LOGGER = Logger.getLogger( Shovel.class.getName() );
    
    public void Use(IceBlock block)
    {
    }
    
    public Item PickedUp(Character c)
    {
        return null;
    }
}
