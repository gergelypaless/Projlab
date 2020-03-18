package project;

import groovyx.gpars.activeobject.ActiveObjectRegistry;

import java.util.logging.Logger;

public class Explorer extends Character
{
    private static final Logger LOGGER = Logger.getLogger( Explorer.class.getName() );
    
    public Explorer()
    {
        super(0);
    }
    
    public Explorer(int ID)
    {
        super(ID);
        LOGGER.finest("Explorer constructor");
    }
    
    public void useAbility()
    {
        LOGGER.fine("Using character's ability");
        CheckStability(Direction.LEFT);
    }
    
    private int CheckStability(Direction d)
    {
        LOGGER.finest("Explorer checking stability");
        block.getStability();
        return 0;
    }
    
    public void changeHealth(int value)
    {
        LOGGER.fine("Changing health to: " + getHealth());
    }
}
