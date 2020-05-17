package com.csakcintanyer.bme.projlab;

import java.util.ArrayList;

public class StableBlock extends IceBlock
{
    //item nélküli konstruktor
    public StableBlock(int amountOfSnow)
    {
        super(amountOfSnow, -1);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Entity c)
    {
        getEntities().add(c);
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        getEntities().remove(c);
    }
    
    public boolean placeIgloo()
    {
        if (hasIgloo) // ha már van iglu akkor sikertelen
            return false;
        
        hasIgloo = true;
        hasTent = false; // az igloot leraktuk akkor a rajta lévő sátor eltűnik
        return true; // sikeres
    }
    
    public boolean placeTent()
    {
        if (hasIgloo || hasTent) // ha van már iglu vagy másik sátor a jégtáblán -> sikertelen
            return false;
        
        hasTent = true;
        return true; // sikeres
    }
    
    public boolean hasIgloo()
    {
        return hasIgloo;
    }
    
    public boolean hasTent()
    {
        return hasTent;
    }
    
    public void removeTent()
    {
        hasTent = false;
    }
    
    public boolean blockIsFull()
    {
        return false;
    }
    
    public void draw(int x, int y) // screen coordinates
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
    
        // enities
        ArrayList<Entity> entities = getEntities();
        Bear bear = null;
        if (entities.size() == 1)
        {
            if (entities.get(0) instanceof Bear)
                bear = (Bear)entities.get(0);
            else
                entities.get(0).draw(x + 5, y - 8);
        }
        else if (entities.size() > 1)
        {
            for (int i = 0; i < entities.size(); ++i)
            {
                if (entities.get(i) instanceof Bear)
                {
                    bear = (Bear)entities.get(i);
                    continue;
                }
                entities.get(i).draw(x + 5 + i * 10, y - 8);
            }
        }
    
        // placables
        if (hasIgloo())
            view.draw(view.iglooIcon, x, y);
        else if (hasTent())
            view.draw(view.tentOnBlockIcon, x, y);
    
        // bear on top of everything
        if (bear != null) bear.draw(x + 5, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "stableblock";
    }
    
    private boolean hasIgloo; // van-e ezen az IceBlockon Igloo?
    private boolean hasTent; // van-e ezen az IceBlockon Igloo?
}
