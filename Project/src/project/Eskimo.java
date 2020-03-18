package project;

import java.util.logging.Logger;

public class Eskimo extends Character
{
    private static final Logger LOGGER = Logger.getLogger(Eskimo.class.getName());
    
    public Eskimo()
    {
        super(0);
    }
    
    public Eskimo(int ID)
    {
        super(ID);
        LOGGER.finest("Eskimo constructor");
    }
    
    public void useAbility()
    {
        LOGGER.fine("Using character's ability");
        placeIgloo();
    }
    
    private boolean placeIgloo()
    {
        LOGGER.fine("Placing igloo");
        //block.placeIgloo();
        return false;
    }
    
    public void changeHealth(int value)
    {
        LOGGER.fine("Changing health to: " + getHealth());
        
    }
}
