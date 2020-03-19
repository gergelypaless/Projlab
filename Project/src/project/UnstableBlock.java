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
    
    // valaki rálépett erre az IceBlockra
    public void accept(Character c)
    {
        LOGGER.fine("UnstableBlock accepting");
        getCharacters().add(c);
        
        // megnézzük, hogy átfordul-e az IceBlock
        if (getCharacters().size() > getStability())
        {
            // ha igen, akkor mindegyik karakternek meg kell hivni a fallIn() metódusát
            for (Character character : getCharacters())
            {
                character.fallIn();
            }
        }
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Character c)
    {
        LOGGER.fine("UnstableBlock removing");
        getCharacters().remove(c);
    }
}
