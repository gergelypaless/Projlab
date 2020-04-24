package com.csakcintanyer.bme.projlab;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Explorer extends Character
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Explorer.class.getName() );
    
    public Explorer(int ID)
    {
        super(ID);
        LOGGER.finest("Explorer constructor");
        health = 4;
    }
    
    // az Explorer képessége, hogy meg tudja nézni egy szomszédos block hány karaktert bír el.
    public void useAbility()
    {
        if (energy == 0)
            return;
        
        LOGGER.fine("Using character's ability");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        // megkérdezzük, hogy melyik irányba kell használni a képességét
        System.out.println("Direction: ");
        try
        {
            input = reader.readLine();

            // lekérjük a megfelelő IceBlock stability értékét
            if (input.equals("up"))
                System.out.println(checkStability(Direction.UP));
            if (input.equals("down"))
                System.out.println(checkStability(Direction.DOWN));
            if (input.equals("left"))
                System.out.println(checkStability(Direction.LEFT));
            if (input.equals("right"))
                System.out.println(checkStability(Direction.RIGHT));
        }
        catch (IOException e)
        {
            System.out.println("Exception occured in the input");
            return;
        }
        
        // a képesség használata egy munka
        changeEnergy(-1);
    }
    
    private int checkStability(Direction d)
    {
        LOGGER.finest("Explorer checking stability");
        
        //lekérjük a szomszédos block stability értékét.
        return block.getNeighbours().get(d).getStability();
    }
}
