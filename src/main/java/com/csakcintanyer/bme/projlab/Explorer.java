package com.csakcintanyer.bme.projlab;

import java.io.IOException;


public class Explorer extends Character
{
    
    public Explorer(int ID)
    {
        super(ID);
        health = 4; // 4 testhője van
    }
    
    // az Explorer képessége, hogy meg tudja nézni egy szomszédos block hány karaktert bír el.
    public boolean useAbility()
    {
        if (energy == 0) // ha nincs elég energiája a játékosnak akkor nem sikerül
            return false;

        try
        {
            System.out.println(checkStability(IOLanguage.GetDirection())); // TODO : ez hogy van?
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    
        // a képesség használata egy munka
        changeEnergy(-1);
        return true; // sikeres használat
    }

    // a sarkkutató ellenőrzi egy adott irányba lévő jégtábla stabilitását
    private int checkStability(Direction d) // TODO : mi van ha nincs arra tábla?
    {
        //lekérjük a szomszédos block stability értékét.
        return block.getNeighbours().get(d).getStability();
    }
}
