package com.csakcintanyer.bme.projlab;

import java.util.logging.Logger;

public class Eskimo extends Character
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger(Eskimo.class.getName());
    
    public Eskimo()
    {
        super(0); // ősosztály kosntruktora
    }
    
    public Eskimo(int ID)
    {
        super(ID);
        LOGGER.finest("Eskimo constructor");
    }
    
    //képesség használatát szolgáló függvény
    public void useAbility()
    {
        LOGGER.fine("Using character's ability");
    
        // a képesség használata egy munkába kerül
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
        
        // Eskimo képessége iglook lerakása
        placeIgloo();
    }
    
    //maga a képesség függvénye
    private boolean placeIgloo()
    {
        LOGGER.fine("Placing igloo");
        // visszaad egy igaz/hamis értéket, ez alapján lehet eldönteni, hogy tudunk-e lerakni itt igloo-t
        return block.placeIgloo();
    }
    
    //a karakter aktuális életének megváltoztatására alkalmas függvény
    public void changeHealth(int value)
    {
        LOGGER.fine("Changing health by: " + value);
        health += value;
    }
}
