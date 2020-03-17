package project;

import java.util.logging.Logger;

public class Explorer extends Character
{
    private static final Logger LOGGER = Logger.getLogger( Explorer.class.getName() );
    
    public Explorer(int ID)
    {
        super(ID);
        LOGGER.finest("Explorer constructor");
    }
    
    public void useAbility()
    {
        LOGGER.finest("Explorer using ability");
        CheckStability(Direction.LEFT);
    }
    
    private int CheckStability(Direction d)
    {
        LOGGER.finest("Explorer checking stability");
        return 0;
    }
    
    public void changeHealth(int value)
    {
    }
}
