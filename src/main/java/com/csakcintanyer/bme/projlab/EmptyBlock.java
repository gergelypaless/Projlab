package com.csakcintanyer.bme.projlab;

import java.util.logging.Logger;

public class EmptyBlock extends IceBlock
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( EmptyBlock.class.getName() );
    
    public EmptyBlock(int amountOfSnow, int stability)
    {
        super(amountOfSnow, stability);
    }
    
    // valaki rálépett erre az IceBlockra
    public void accept(Character c)
    {
        LOGGER.fine("EmptyBlock accepting");
        getCharacters().add(c);
        // ha üres block-ra lépünk akkor beleesünk a fízbe
        c.fallIn();
    }
    
    // valaki ellépett erről az IceBlockól
    public void remove(Character c)
    {
        LOGGER.fine("EmptyBlock removing");
        getCharacters().remove(c);
    }
}
