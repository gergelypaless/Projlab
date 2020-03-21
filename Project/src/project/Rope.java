package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Rope implements CollectableItem, UsableItem
{
    private static final Logger LOGGER = Logger.getLogger( Rope.class.getName() );
    
    public void use(IceBlock savingTo)
    {
        LOGGER.fine("Using Rope");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("What direction should i save a character? (up/down/left/right)");
        try
        {
            input = reader.readLine();
            Direction d = Direction.UP;
            if (input.equals("up"))
                d = Direction.UP;
            if (input.equals("down"))
                d = Direction.DOWN;
            if (input.equals("left"))
                d = Direction.LEFT;
            if (input.equals("right"))
                d = Direction.RIGHT;

            IceBlock savingFrom = savingTo.getNeighbours().get(d);
            ArrayList<Character> characters = savingFrom.getCharacters();
            Character characterInTrouble = characters.get(0);
            savingFrom.remove(characterInTrouble);
            savingTo.accept(characterInTrouble);
            characterInTrouble.setIceBlock(savingTo);
            characterInTrouble.save();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Rope");
        c.changeEnergy(-1); // Item használata egy munka.
        c.addItem(this);
    }
}
