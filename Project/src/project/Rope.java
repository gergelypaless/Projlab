package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Rope implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Rope.class.getName() );
    
    public void Use(IceBlock block)
    {
        LOGGER.fine("Using Rope");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("What direction should i save a character? (up/down/left/right)");
        try
        {
            input = reader.readLine();
            Direction d = Direction.INVALID;
            if (input.equals("up"))
                d = Direction.UP;
            if (input.equals("down"))
                d = Direction.DOWN;
            if (input.equals("left"))
                d = Direction.LEFT;
            if (input.equals("right"))
                d = Direction.RIGHT;
            
            LOGGER.fine("Saving character in direction " + d.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void InteractWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Rope");
        c.addItem(this);
    }
}
