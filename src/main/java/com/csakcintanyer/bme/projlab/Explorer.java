package com.csakcintanyer.bme.projlab;

import java.io.IOException;


public class Explorer extends Character
{
    public Explorer(int ID)
    {
        super(ID);
        maxHealth = health = 4; // 4 testhője van
    }
    
    // az Explorer képessége, hogy meg tudja nézni egy szomszédos block hány karaktert bír el.
    public boolean useAbility()
    {
        if (energy == 0) // ha nincs elég energiája a játékosnak akkor nem sikerül
            return false;

        try
        {
            System.out.println(checkStability(IOLanguage.GetDirection()));
        }
        catch (IllegalArgumentException | IOException e)
        {
            // Nem volt abban az irányban block
            return false;
        }
    
        // a képesség használata egy munka
        changeEnergy(-1);
        return true; // sikeres használat
    }

    // a sarkkutató ellenőrzi egy adott irányba lévő jégtábla stabilitását
    private int checkStability(Direction d) throws IllegalArgumentException
    {
        //lekérjük a szomszédos block stability értékét.
        if (block.getNeighbours().get(d) != null)
            return block.getNeighbours().get(d).getStability();
         
        throw new IllegalArgumentException();
    }
    
    public void draw(int x, int y)
    {
        View view = View.get();
        view.draw(view.explorerIcon, x, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "explorer";
    }
}
