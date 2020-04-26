package com.csakcintanyer.bme.projlab;

public class Eskimo extends Character
{
    
    public Eskimo(int ID)
    {
        super(ID);
        health = 5;
    }
    
    //képesség használatát szolgáló függvény
    public boolean useAbility()
    {
    
        if (energy == 0)
            return false;
        
        if (block.placeIgloo())
        {
            changeEnergy(-1);
            return true;
        }
        return false;
    }
}
