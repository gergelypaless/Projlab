package com.csakcintanyer.bme.projlab;

public class Bullet extends CollectableItem // csak gyűjthető
{
    // IceBlock paraméterrel inicializájuk az ősosztály konstruktorát
    public Bullet(IceBlock block)
    {
        super(block);
    }
    
    public void interactWithCharacter(Character c)
    {
        /* ezzel a függvénnyel mondjuk meg a karakternek, hogy mostantól nála van a Bullet
         * Bulletből az egész játék során csak egy példány van
         */
        c.setHasBullet();
    }
    
    public void draw(int x, int y)
    {
        View view = View.get();
        view.draw(view.bulletIcon, x, y);
    }
    
    // kiiráshoz kell
    public String toString()
    {
        return "bullet";
    }
}
