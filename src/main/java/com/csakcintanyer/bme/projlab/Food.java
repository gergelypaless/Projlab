package com.csakcintanyer.bme.projlab;

public class Food extends CollectableItem
{
    
    public Food(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        // csak akkor ha nem max healthünk van
        if (c.getHealth() != c.getMaxHealth())
        {
            // Foodot vettünk fel, ezért 1-el nő a testhőnk
            c.changeHealth(1);
            System.out.println("Health increased by 1");
        }
        else
        {
            System.out.println("You have maximum amount of health");
            throw new IllegalArgumentException();
        }
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "food";
    }
}
