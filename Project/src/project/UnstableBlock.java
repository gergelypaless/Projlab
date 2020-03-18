package project;

import java.util.logging.Logger;

public class UnstableBlock extends IceBlock
{
    private static final Logger LOGGER = Logger.getLogger( UnstableBlock.class.getName() );
    
    public UnstableBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    public UnstableBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        super(amountOfSnow, stability, item);
    }
    
    public void accept(Character c)
    {
        LOGGER.fine("UnstableBlock accepting");
        getCharacters().add(c);
        
        if (getCharacters().size() > getStability())
        {
            for (Character character : getCharacters())
            {
                character.fallIn();
            }
        }
    }
    
    public void remove(Character c)
    {
        LOGGER.fine("UnstableBlock removing");
        getCharacters().remove(c);
    }
}
