package project;

import java.util.logging.Logger;

public class StableBlock extends IceBlock
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( StableBlock.class.getName() );
    
    public StableBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    public StableBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        super(amountOfSnow, stability, item);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Character c)
    {
        LOGGER.fine("StableBlock accepting");
        getCharacters().add(c);
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Character c)
    {
        LOGGER.fine("StableBlock removing");
        getCharacters().remove(c);
    }
    
    public boolean placeIgloo()
    {
        return true;
    }
    
    public boolean hasIgloo()
    {
        return false;
    }
    
    private boolean hasIgloo; // van-e ezen az IceBlockon Igloo?
}
