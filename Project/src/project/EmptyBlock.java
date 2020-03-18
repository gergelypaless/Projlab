package project;

import java.util.logging.Logger;

public class EmptyBlock extends IceBlock
{
    private static final Logger LOGGER = Logger.getLogger( EmptyBlock.class.getName() );
    
    public EmptyBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    public void accept(Character c)
    {
        LOGGER.fine("EmptyBlock accepting");
        getCharacters().add(c);
        c.fallIn();
    }
    
    public void remove(Character c)
    {
        LOGGER.fine("EmptyBlock removing");
        getCharacters().remove(c);
    }
}
