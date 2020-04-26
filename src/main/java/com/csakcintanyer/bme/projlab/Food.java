package com.csakcintanyer.bme.projlab;

public class Food extends CollectableItem
{
    
    public Food(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        // Foodot vettünk fel, ezért 1-el nő a testhőnk
        c.changeHealth(1);
    }
}
