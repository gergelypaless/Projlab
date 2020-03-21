package project;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Gun implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Gun.class.getName() );
    
    public void use(IceBlock block)
    {
        LOGGER.fine("Using Gun");
        
        boolean hasFlare = false, hasBullet = false;
        ArrayList<Character> characters = block.getCharacters();
        // megnézzük, hogy az ezen a blockon lévő játékosoknál van-e a Flare és a Bullet.
        for (Character c : characters)
        {
            if (!hasFlare)
                hasFlare = c.hasFlare();
            if (!hasBullet)
                hasBullet = c.hasBullet();
        }
        // ha mindkettő megvan, akkor el tudjuk sütni a fegyvert, nyertünk
        if (hasBullet && hasFlare)
        {
            LOGGER.fine("Gun used");
            Game.get().win();
        }
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Gun");
        c.changeEnergy(-1); // Item használata egy munka.
        c.addItem(this); // inventoryhoz adjuk az itemet, mert UsableItem
    }
}
