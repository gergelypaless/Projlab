package project;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Gun implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Gun.class.getName() );
    
    public void Use(IceBlock block)
    {
        LOGGER.fine("Using Gun");
        
        boolean hasFlare = false, hasBullet = false;
        ArrayList<Character> characters = block.getCharacters();
        for (Character c : characters)
        {
            if (!hasFlare)
                hasFlare = c.hasFlare();
            if (!hasBullet)
                hasBullet = c.hasBullet();
        }
        if (hasBullet && hasFlare)
        {
            LOGGER.fine("Gun used");
            Game.get().win();
        }
    }
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Gun");
        c.addItem(this);
    }
}
