package com.csakcintanyer.bme.projlab;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Explorer extends Character
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
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
    
    // az Explorer képessége, hogy meg tudja nézni egy szomszédos block hány karaktert bír el.
    public void useAbility()
    {
        LOGGER.fine("Using character's ability");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        // megkérdezzük, hogy melyik irányba kell használni a képességét
        System.out.println("What direction should i use my ability? (up/down/left/right)");
        try
        {
            input = reader.readLine();

            // lekérjük a megfelelő IceBlock stability értékét
            if (input.equals("up"))
                checkStability(Direction.UP);
            if (input.equals("down"))
                checkStability(Direction.DOWN);
            if (input.equals("left"))
                checkStability(Direction.LEFT);
            if (input.equals("right"))
                checkStability(Direction.RIGHT);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        // a képesség használata egy munka
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
    }
    
    private int checkStability(Direction d)
    {
        LOGGER.finest("Explorer checking stability");
        
        //lekérjük a szomszédos block stability értékét.
        return block.getNeighbours().get(d).getStability();
    }
  //a karakter aktuális életének megváltoztatására alkalmas függvény
    public void changeHealth(int value)
    {
        LOGGER.fine("Changing health by " + value);
    }
}
