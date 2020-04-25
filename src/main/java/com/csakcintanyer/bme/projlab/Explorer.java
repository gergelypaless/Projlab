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
    public boolean useAbility()
    {
        if (energy == 0)
            return false;
        
        LOGGER.fine("Using character's ability");
        try
        {
            System.out.println(checkStability(IOLanguage.GetDirection()));
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    
        // a képesség használata egy munka
        changeEnergy(-1);
        return true;
    }
    
    private int checkStability(Direction d)
    {
        LOGGER.finest("Explorer checking stability");
        
        //lekérjük a szomszédos block stability értékét.
        return block.getNeighbours().get(d).getStability();
    }
}
