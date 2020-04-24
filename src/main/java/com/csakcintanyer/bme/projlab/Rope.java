package com.csakcintanyer.bme.projlab;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Rope extends CollectableItem implements UsableItem
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Rope.class.getName() );
    
    public Rope(IceBlock block)
    {
        super(block);
    }
    
    // Item használata
    public boolean use(IceBlock savingTo)
    {
        LOGGER.fine("Using Rope");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("Direction: ");
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

            // lekérjük azt az IceBlockot ahonnan ki kell mentenünk valakit
            IceBlock savingFrom = savingTo.getNeighbours().get(d);
            // lekérjük az IceBlockról a karaktereket
            ArrayList<Entity> characters = savingFrom.getEntities();
            // kiválasztunk egyet, mondjuk a 0. helyen lévő karaktert
            Entity characterInTrouble = characters.get(0);
            // mozgatjuk a karaktert
            savingFrom.remove(characterInTrouble);
            savingTo.accept(characterInTrouble);
            // beállítjuk a karakternek az új helyét
            characterInTrouble.setIceBlock(savingTo);

            // meghívjhuk a save függvényt mivel a karakter megmenekült/nem fulladt meg
            characterInTrouble.save();

        }
        catch (IOException e)
        {
            System.out.println("Exception occured in the input");
            return false;
        }
        return false;
    }

    public void interactWithCharacter(Character c)
    {
        LOGGER.fine("Picked up Rope");
        c.addItem(this); // hizzáadjuk az inventoryhoz mivel UsableItem
    }
}
