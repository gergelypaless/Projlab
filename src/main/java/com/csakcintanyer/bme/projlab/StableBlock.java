package com.csakcintanyer.bme.projlab;
import java.util.logging.Logger;

public class StableBlock extends IceBlock
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( StableBlock.class.getName() );
    
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
        LOGGER.fine("StableBlock accepting");
        getEntities().add(c);
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        LOGGER.fine("StableBlock removing");
        getEntities().remove(c);
    }
    
    public boolean placeIgloo()
    {
        if (hasIgloo)
            return false;
        
        hasIgloo = true;
        hasTent = false;
        return true;
    }
    
    public boolean placeTent()
    {
        if (hasIgloo || hasTent)
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
