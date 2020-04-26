package com.csakcintanyer.bme.projlab;

import java.io.IOException;


public class Explorer extends Character
{
    
    public Explorer(int ID)
    {
        super(ID);
        health = 4;
    }
    
    // az Explorer képessége, hogy meg tudja nézni egy szomszédos block hány karaktert bír el.
    public boolean useAbility()
    {
        if (energy == 0)
            return false;

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
        
        //lekérjük a szomszédos block stability értékét.
        return block.getNeighbours().get(d).getStability();
    }
}
