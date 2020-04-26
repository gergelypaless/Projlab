package com.csakcintanyer.bme.projlab;

public class EmptyBlock extends IceBlock
{
    
    public EmptyBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Entity c)
    {
        getEntities().add(c);
        // mivel üres block-ra lépünk ezért beleesünk a vízbe
        c.fallIn();
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        getEntities().remove(c);
    }
}
