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
        if (c.getHealth() != c.getMaxHealth())
            c.changeHealth(1);
        else throw new IllegalArgumentException();
    }
}
