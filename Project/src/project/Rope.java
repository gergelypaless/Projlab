package project;

import java.util.logging.Logger;

public class Rope implements Item
{
    private static final Logger LOGGER = Logger.getLogger( Rope.class.getName() );
    
    public void Use(IceBlock block)
    {
    }
    
    public Item PickedUp(Character c)
    {
        return null;
    }
}
