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
        if (getEntities().size() > 0)
        {
            amountOfSnow = 0;
            return;
        }
        
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
        View view  = View.get();
        
        // iceblock
        if (getSnow() > 0)
            view.draw(view.snowyIceBlockIcon, x, y);
        else
            view.draw(view.emptyIceBlockIcon, x, y);
        
        // character
        ArrayList<Entity> entities = getEntities();
        if (entities.size() == 1)
        {
            entities.get(0).draw(x + 5, y - 8);
        }
        else if (entities.size() > 1)
        {
            for (int i = 0; i < entities.size(); ++i)
            {
                entities.get(i).draw(x + 5 + i * 10, y - 8);
            }
        }
        
        // placables
        if (hasIgloo())
            view.draw(view.iglooIcon, x, y);
        else if (hasTent())
            view.draw(view.tentOnBlockIcon, x, y);
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        getEntities().remove(c);
    }
}
