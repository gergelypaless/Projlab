package com.csakcintanyer.bme.projlab;
import java.util.ArrayList;

public class Gun extends CollectableItem implements UsableItem
{
    
    public Gun(IceBlock block)
    {
        super(block);
    }
    
    // az item használata
    public boolean use(IceBlock block)
    {
        
        boolean hasFlare = false, hasBullet = false;
        ArrayList<Entity> entities = block.getEntities();
        // megnézzük, hogy az ezen a blockon lévő játékosoknál van-e a Flare és a Bullet.
        for (Entity e : entities)
        {
            if (!hasFlare)
                hasFlare = e.hasFlare();
            if (!hasBullet)
                hasBullet = e.hasBullet();
        }
        // ha mindkettő megvan, akkor el tudjuk sütni a fegyvert, nyertünk
        if (hasBullet && hasFlare && Game.get().getNumOfCharacters() == block.getEntities().size())
        {
            Game.get().win();
        }
        
        return false; // az ásót nem kell törölni az Inventory-ból
    }
    
    public String toString()
    {
        return "gun";
    }
    
    public void interactWithCharacter(Character c)
    {
        c.addItem(this); // inventoryhoz adjuk az itemet, mert UsableItem
    }
}
