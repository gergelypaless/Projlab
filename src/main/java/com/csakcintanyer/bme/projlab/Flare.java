package com.csakcintanyer.bme.projlab;

public class Flare extends CollectableItem
{
    public Flare(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        // a karakter felvett egy Flare-t
        c.setHasFlare();
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "flare";
    }
}
