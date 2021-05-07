package com.csakcintanyer.bme.projlab;

public class Food extends CollectableItem
{
    public void interactWithCharacter(Character c)
    {
        // csak akkor ha nem max healthünk van
        if (c.getHealth() != c.getMaxHealth())
        {
            // Foodot vettünk fel, ezért 1-el nő a testhőnk
            c.changeHealth(1);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
    
    // food kirajzolása
    public void draw(int x, int y)
    {
        View view = View.get();
        view.draw(view.foodIcon, x, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "food";
    }
}
