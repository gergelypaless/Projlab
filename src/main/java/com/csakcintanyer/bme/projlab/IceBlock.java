package com.csakcintanyer.bme.projlab;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class IceBlock implements Serializable, Drawable
{
    
    //item nélküli konstruktor
    public IceBlock(int amountOfSnow, int stability)
    {
        this(amountOfSnow, stability, null);
    }
    
    //itemes konstruktor
    public IceBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        this.amountOfSnow = amountOfSnow;
        this.stability = stability;
        this.item = item;
    }

    public boolean blockIsFull()
    {
        return getEntities().size() >= getStability();
    }
    
    // item beállítása
    public void setItem(CollectableItem item)
    {
        this.item = item;
    }
    
    //szomszédok beállításáért felelős függvény
    public void setNeighbour(Direction d, IceBlock block)
    {
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
        return stability; // ez a stability változót kéne visszaadja, de az egyszerűség kedvéért most egy fix értéket írtunk be
    }

    // hóréteg változtatása bizonyos értékkel
    public void changeAmountOfSnow(int value)
    {
        amountOfSnow += value;
        if (amountOfSnow < 0)
            amountOfSnow = 0;
    }
    
    public abstract void accept(Entity c);
    public abstract void remove(Entity c);
    
    // valaki fel akarja venni az Item-et ami ezen az IceBlockon van, visszaadjuk az IceBlockon lévő Itemet
    public CollectableItem removeItem()
    {
        if (amountOfSnow > 0) // ha hó borítja nem veszünk fel semmit
            return null;

        CollectableItem itemToReturn = item;
        item = null; // miután valaki felveszi a tárgyat, nem lesz többé a jégtáblában
        return itemToReturn; // visszaadjuk a jégtáblában lévő tárgyat
    }
    
    //eszkimó képességének használata váltja ki
    public boolean placeIgloo()
    {
        // csak StableBlock-ra lehet igloo-t tenni, így csak az fog igazat visszaadni
        return false;
    }
    
    // set if an explorer was inspected this iceblock
    public void setChecked()
    {
        checked = true;
    }
    
    // have somebody ever inspected this iceblock?
    public boolean isChecked()
    {
        return checked;
    }
    
    //eszkimó képességének használata váltja ki
    public boolean placeTent()
    {
        return false;
    }
    
    // van-e az IceBlockon igloo
    public boolean hasIgloo()
    {
        return false;
    }
    
    // van-e az IceBlockon igloo
    public boolean hasTent()
    {
        return false;
    }
    
    //a blokkon lévő karakterek listájának lekérésére szolgáló függvény
    public ArrayList<Entity> getEntities()
    {
        return entities;
    }
    
    //a szomszédos mezők visszaadása
    public HashMap<Direction, IceBlock> getNeighbours()
    {
        return neighbours;
    }
    
    // item lekérdezése
    public CollectableItem getItem()
    {
        return item;
    }
    
    public void removeTent() { }
    
    // hó mennyisége
    protected int amountOfSnow;
    
    // hány karaktert bír el
    protected int stability;
    
    private boolean checked = false;
    
    // karakterek az IceBlockon.
    private ArrayList<Entity> entities = new ArrayList<>();
    
    // Item az IceBlockon
    private CollectableItem item;
    
    // IceBlock szomszédai egy HashMap-ben tárolva
    private HashMap<Direction, IceBlock> neighbours = new HashMap<>();
}
