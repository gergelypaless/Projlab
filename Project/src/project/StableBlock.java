package project;

import java.util.logging.Logger;

public class StableBlock extends IceBlock
{
    private static final Logger LOGGER = Logger.getLogger( StableBlock.class.getName() );
    
    public StableBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    public StableBlock(int amountOfSnow, int stability, Item item)
    {
        super(amountOfSnow, stability, item);
    }
    
    public void accept(Character c)
    {
        LOGGER.fine("StableBlock accepting");
        getCharacters().add(c);
    }
    
    public void remove(Character c)
    {
        LOGGER.fine("StableBlock removing");
        getCharacters().remove(c);
    }
    
    public boolean placeIgloo()
    {
        return false;
    }
    
    public boolean hasIgloo()
    {
        return false;
    }
    
    private boolean hasIgloo;
}
