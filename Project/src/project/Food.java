package project;

import java.util.logging.Logger;

public class Food implements Item
{
    private static final Logger LOGGER = Logger.getLogger( Food.class.getName() );
    
    public void Use(IceBlock block)
    {
    }
    
    public Item PickedUp(Character c)
    {
        return null;
    }
}
