package project;

import java.util.logging.Logger;

public class Gun implements Item
{
    private static final Logger LOGGER = Logger.getLogger( Gun.class.getName() );
    
    public void Use(IceBlock block)
    {
    }
    
    public Item PickedUp(Character c)
    {
        return null;
    }
}
