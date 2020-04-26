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
        // ha üres block-ra lépünk akkor beleesünk a fízbe
        c.fallIn();
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Entity c)
    {
        getEntities().remove(c);
    }
}
