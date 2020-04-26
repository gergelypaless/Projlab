package com.csakcintanyer.bme.projlab;
import java.util.ArrayList;

public class UnstableBlock extends IceBlock
{
    
    public UnstableBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    public UnstableBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        super(amountOfSnow, stability, item);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Entity c)
    {
        ArrayList<Entity> entities = getEntities();
        entities.add(c);
        
        // megnézzük, hogy átfordul-e az IceBlock
        if (entities.size() > getStability())
        {
            // ha igen, akkor mindegyik karakternek meg kell hivni a fallIn() metódusát
            for (Entity entity : entities)
                entity.fallIn();
        }
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        getEntities().remove(c);
    }
}
