package com.csakcintanyer.bme.projlab;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public abstract class IceBlock implements Serializable
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( IceBlock.class.getName() );
    
    //item nélküli konstruktor
    public IceBlock(int amountOfSnow, int stability)
    {
        this(amountOfSnow, stability, null);
        LOGGER.finest("IceBlock constructor");
    }
    
    //itemes konstruktor
    public IceBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        this.amountOfSnow = amountOfSnow;
        this.stability = stability;
        this.item = item;
    }
    
    public void setItem(CollectableItem item)
    {
        this.item = item;
    }
    
    //szomszédok beállításáért felelős függvény
    public void setNeighbour(Direction d, IceBlock block)
    {
        LOGGER.finer("Setting neighbour in Direction: " + d.toString());
        // szomszédos IceBlock beállítása a megfelelő irányban, egy HashMap<Direction, IceBlock>-ot használva
        neighbours.put(d, block);
    }
    
    //amountOfSnow gettere
    public int getSnow()
    {
        return amountOfSnow;
    }
    
    //Stability gettere
    public int getStability()
    {
        LOGGER.finest("Stability getter");
        return stability; // ez a stability változót kéne visszaadja, de az egyszerűség kedvéért most egy fix értéket írtunk be
    }

    // hóréteg változtatása bizonyos értékkel
    public void changeAmountOfSnow(int value)
    {
        LOGGER.fine("Changing amount of snow by " + value);
        amountOfSnow += value;
    }
    
    public abstract void accept(Entity c);
    
    public abstract void remove(Entity c);
    
    // valaki fel akarja venni az Item-et ami ezen az IceBlockon van, visszaadjuk az IceBlockon lévő Itemet
    public CollectableItem removeItem()
    {
        if (amountOfSnow > 0)
            return null;
    
        LOGGER.fine("Removing Item from IceBlock");
        CollectableItem itemToReturn = item;
        item = null;
        return itemToReturn;
    }
    
    //eszkimó képességének használata váltja ki
    public boolean placeIgloo()
    {
        LOGGER.fine("Can we place igloo on this IceBlock?");
        // csak StableBlock-ra lehet igloo-t tenni, így csak az fog igazat visszaadni
        return false;
    }
    
    //eszkimó képességének használata váltja ki
    public boolean placeTent()
    {
        return false;
    }
    
    // van-e az IceBlockon igloo
    public boolean hasIgloo()
    {
        LOGGER.finest("Has igloo getter");
        return false;
    }
    
    // van-e az IceBlockon igloo
    public boolean hasTent()
    {
        LOGGER.finest("Has igloo getter");
        return false;
    }
    
    //a blokkon lévő karakterek listájának lekérésére szolgáló függvény
    public ArrayList<Entity> getEntities()
    {
        LOGGER.finest("IceBlock Characters getter");
        return entities;
    }
    
    //a szomszédos mezők visszaadása
    public HashMap<Direction, IceBlock> getNeighbours()
    {
        LOGGER.finest("Neighbours getter");
        return neighbours;
    }
    
    public CollectableItem getItem()
    {
        return item;
    }
    
    // hó mennyisége
    private int amountOfSnow;
    
    // hány karaktert bír el
    private int stability;
    
    // karakterek az IceBlockon.
    private ArrayList<Entity> entities = new ArrayList<>();
    
    // Item az IceBlockon
    private CollectableItem item;
    
    // IceBlock szomszédai egy HashMap-ben tárolva
    private HashMap<Direction, IceBlock> neighbours = new HashMap<>();
}
