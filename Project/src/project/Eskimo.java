package project;

import java.util.logging.Logger;

public class Eskimo extends Character
{
    private static final Logger LOGGER = Logger.getLogger(Eskimo.class.getName());
    
    public Eskimo(int ID)
    {
        super(ID);
        LOGGER.finest("Eskimo constructor");
    }
    
    public void useAbility()
    {
    }
    
    private boolean placeIgloo()
    {
        return false;
    }
    
    public void changeHealth(int value)
    {
    }
}
