package project;

import java.util.logging.Logger;

public class Suit implements Item
{
    private static final Logger LOGGER = Logger.getLogger( Suit.class.getName() );
    
    public void Use(IceBlock block)
    {
    }
    
    public Item PickedUp(Character c)
    {
        return null;
    }
}
