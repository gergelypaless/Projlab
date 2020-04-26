package com.csakcintanyer.bme.projlab;

public class Eskimo extends Character
{
    
    public Eskimo(int ID)
    {
        super(ID);
        maxHealth = health = 5; // 5 testhője van
    }
    
    //képesség használatát szolgáló függvény (Épít 1 db iglut)
    public boolean useAbility()
    {
        if (energy == 0) // ha nincs elég energiája a játékosnak akkor nem sikerül
            return false;
        
        if (block.placeIgloo()) // ha sikeresen építet egy iglut
        {
            changeEnergy(-1); // 1 munkába került
            return true; // jelezzük, hogy sikeres
        }
        return false; // sikertelen
    }
}
