package com.csakcintanyer.bme.projlab;
import java.util.ArrayList;

public class UnstableBlock extends IceBlock
{
    
    public UnstableBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Entity c)
    {
        ArrayList<Entity> entities = getEntities();
        entities.add(c);
        
        // TODO: unstable block kicserélése empty blocká
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
    
    public void draw(int x, int y)
    {
        View view  = View.get();
        
        // iceblock
        if (getSnow() > 0)
            view.draw(view.snowyIceBlockIcon, x, y);
        else
        {
            view.draw(view.icyIceBlockIcon, x, y);
    
            // item
            CollectableItem item = getItem();
            if (item != null)
                item.draw(x + 10, y + 10);
        }
    
        if (Game.get().getSelectedIceBlock() == this)
        {
            view.drawSelection(x, y);
        }
    
        // character
        ArrayList<Entity> entities = getEntities();
        if (entities.size() == 1)
        {
            entities.get(0).draw(x + 5, y - 8);
        }
        else if (entities.size() > 1)
        {
            for (int i = 0; i < entities.size(); ++i)
            {
                entities.get(i).draw(x + 5 + i * 10, y - 8);
            }
        }
    
        // placables
        if (hasIgloo())
            view.draw(view.iglooIcon, x, y);
        else if (hasTent())
            view.draw(view.tentOnBlockIcon, x, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "unstableblock";
    }
}
