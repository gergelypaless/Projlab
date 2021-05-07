package com.csakcintanyer.bme.projlab;

public class Flare extends CollectableItem
{
    public void interactWithCharacter(Character c)
    {
        // a karakter felvett egy Flare-t
        c.setHasFlare();
    }
    
    // flare kirajzolása
    public void draw(int x, int y)
    {
        View view = View.get();
        view.draw(view.flareIcon, x, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "flare";
    }
}
