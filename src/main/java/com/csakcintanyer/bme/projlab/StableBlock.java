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
    
    // van rajta igloo?
    public boolean hasIgloo()
    {
        return hasIgloo;
    }
    
    // van rajta tent?
    public boolean hasTent()
    {
        return hasTent;
    }
    
    // tent eltüntetése
    public void removeTent()
    {
        hasTent = false;
    }
    
    // az iceblock a terhelése maximumán van-e
    public boolean blockIsFull()
    {
        return false;
    }
    
    // stableblock kirajzolása
    public void draw(int x, int y) // screen coordinates
    {
        View view  = View.get();
        
        // iceblock
        if (getSnow() > 0) // ha van rajta hó akkor havas blockot rajzolunk ki
            view.draw(view.snowyIceBlockIcon, x, y);
        else
        {
            view.draw(view.icyIceBlockIcon, x, y); // ha nincs rajta hó akkor simát
            
            // itemet csak akkor rajzoljuk ki ha nincs az iceblockon hó
            CollectableItem item = getItem();
            if (item != null)
                item.draw(x + 10, y + 10);
        }
    
        // ha ez a kijelölt iceblock
        if (Game.get().getSelectedIceBlock() == this)
        {
            view.drawSelection(x, y);
        }
    
        // entitik kirajzolása a blockon
        ArrayList<Entity> entities = getEntities();
        Bear bear = null;
        for (int i = 0; i < entities.size(); ++i)
        {
            if (entities.get(i) instanceof Bear)
            {
                bear = (Bear)entities.get(i); // beart később rajzoljuk ki
                continue;
            }
            entities.get(i).draw(x + 5 + i * 10, y - 8); // character kirajzolása a megadott offsettel
        }
    
        // placables
        if (hasIgloo()) // van rajta igloo?
            view.draw(view.iglooIcon, x, y);
        else if (hasTent()) // van rajta tent?
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
