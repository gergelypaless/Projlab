package com.csakcintanyer.bme.projlab;

public class Suit extends CollectableItem
{
    
    public Suit(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        // a karakter felvett egy Suit-ot.
        c.setHasSuit();
    }
    
    public String toString()
    {
        return "suit";
    }
}
