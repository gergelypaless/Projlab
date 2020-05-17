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
    
    public void draw(int x, int y)
    {
        View view = View.get();
        if(!isInWater) view.draw(view.eskimoIcon, x, y);
        else {
            view.draw(view.eskimoInWaterIcon, x, y+5);
            if(isDrowning()) view.draw(view.drowningIcon, x, y-10);
        }
        
        if (Game.get().getCurrentlyMovingCharacter() == this)
            view.draw(view.littleArrow, x + 3, y - 7);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "eskimo";
    }
}
