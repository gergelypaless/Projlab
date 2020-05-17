package com.csakcintanyer.bme.projlab;

public class Suit extends CollectableItem
{
    public void interactWithCharacter(Character c)
    {
        // a karakter felvett egy Suit-ot.
        c.setHasSuit();
    }
    
    // suit kirajzolása
    public void draw(int x, int y)
    {
        View view = View.get();
        view.draw(view.suitIcon, x, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "suit";
    }
}
