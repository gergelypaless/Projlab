package com.csakcintanyer.bme.projlab;

public class StableBlock extends IceBlock
{
    
    //item nélküli konstruktor
    public StableBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    //itemes konstruktor
    public StableBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        super(amountOfSnow, stability, item);
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
        hasTent = false;
        return true;
    }
    
    public boolean placeTent()
    {
        if (hasIgloo || hasTent) // ha van már iglu vagy másik sátor a jégtáblán -> sikertelen
            return false;
        
        hasTent = true;
        return true;
    }
    
    public boolean hasIgloo()
    {
        return hasIgloo;
    }
    
    public boolean hasTent()
    {
        return hasIgloo;
    }
    
    private boolean hasIgloo; // van-e ezen az IceBlockon Igloo?
    private boolean hasTent; // van-e ezen az IceBlockon Igloo?
}
