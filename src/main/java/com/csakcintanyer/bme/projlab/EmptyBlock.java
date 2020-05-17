package com.csakcintanyer.bme.projlab;

import java.util.ArrayList;

public class EmptyBlock extends IceBlock
{
    
    public EmptyBlock(int amountOfSnow)
    {
        super(amountOfSnow, 0);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Entity c)
    {
        getEntities().add(c);
        // mivel üres block-ra lépünk ezért beleesünk a vízbe
        c.fallIn();
        changeAmountOfSnow(0);
    }
    
    public void changeAmountOfSnow(int value)
    {
        // ha áll valaki rajta akkor nem esik rá hó
        if (getEntities().size() > 0)
        {
            amountOfSnow = 0;
            return;
        }
        
        // hó növelése
        amountOfSnow += value;
        if (amountOfSnow < 0)
            amountOfSnow = 0;
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "emptyblock";
    }
    
    public void draw(int x, int y) // screen coordinates
    {
        View view = View.get();
        
        // iceblock kirajzolása
        // ha van rajta snow akkor más textúrát rajzolunk ki, és a rajta levő item sem jelenik meg
        if (getSnow() > 0)
            view.draw(view.snowyIceBlockIcon, x, y);
        else
            view.draw(view.emptyIceBlockIcon, x, y);
        
        // ha ez a selectedIceBlock akkor egy selection négyzetet is kirajzolunk
        if (Game.get().getSelectedIceBlock() == this)
        {
            view.drawSelection(x, y);
        }
        
        // character
        ArrayList<Entity> entities = getEntities();
        Bear bear = null;
        for (int i = 0; i < entities.size(); ++i)
        {
            if (entities.get(i) instanceof Bear)
            {
                bear = (Bear)entities.get(i); // beart később rajzoljuk ki
                continue;
            }
            entities.get(i).draw(x + 5 + i * 10, y - 8); // karakter kirajzolása, offset megadása
        }
        
        // placables
        if (hasIgloo()) // van rajta igloo
            view.draw(view.iglooIcon, x, y);
        else if (hasTent()) // van rajta tent
            view.draw(view.tentOnBlockIcon, x, y);
    
        // beart minden felett rajzoljuk ki, hogy az ingloo/tent ne takarja ki
        if (bear != null) bear.draw(x + 5, y);
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        c.isInWater = false;
        getEntities().remove(c);
    }
}
