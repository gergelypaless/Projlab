package com.csakcintanyer.bme.projlab;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Character extends Entity
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Character.class.getName() );
    
    // A karaktereknek lett egy IDjük. Ez az ID egy indexként tud működni a Game osztály characters tömbjében
    public Character(int ID)
    {
        this.ID = ID;
        setEnergy(4);
        inventory = new ArrayList<>();
    }

    // a karakter lépett a jégmezőn.
    public boolean move(Direction d)
    {
        LOGGER.fine("Moving in direction: " + d.toString());

        if (energy == 0)
            return false;
        
        // lekérjük annak az iceBlocknak a szomszédait amin állunk, majd lekérjük a megfelelő irányban lévőt
        IceBlock iceBlockToMoveTo = block.getNeighbours().get(d);
        
        if (iceBlockToMoveTo == null)
            return false;

        // mozgatjuk a játékost az IceBlock accept és remove függvényeivel
        block.remove(this);
        iceBlockToMoveTo.accept(this);

        // beállítjuk a karakternek az új IceBlockot.
        setIceBlock(iceBlockToMoveTo);
        
        // a mozgás egy munkába kerül
        changeEnergy(-1);
        return true;
    }
    
    public boolean clear()
    {
        if (energy == 0 || block.getSnow() == 0)
            return false;
        
        LOGGER.fine("Clearing...");
        
        block.changeAmountOfSnow(-1);
        

        // a tisztítás egy munkába kerül
        changeEnergy(-1);
        return true;
    }
    
    public boolean pickUp()
    {
        if (energy == 0)
            return false;
        
        LOGGER.fine("Picking up item");
    
        // a blocktól visszakapunk egy itemet
        CollectableItem item = block.removeItem();
        if (item == null)
            return false;
    
        changeEnergy(-1); // Item használata egy munka.
        // "értesítjük" az itemet, hogy felvettük
        item.interactWithCharacter(this);
        return true;
    }
    
    public abstract boolean useAbility();
    
    // felvettünk egy Suit-ot
    public void setHasSuit()
    {
        LOGGER.fine("Character has a suit now!");
        hasSuit = true;
    }
    
    // fuldoklunk-e?
    public boolean isDrowning()
    {
        LOGGER.finer("Drowning getter. Has suit checked");
        // akkor fuldoklunk (Drowning) ha a vízben vagyunk és nincs rajtunk búvárruha
        return isInWater && !hasSuit;
    }
    
    // Egy Item használata. Az itemIdx paraméter a Character inventory tömbjében indexel, így kapunk egy Itemet
    public boolean useItem(int itemIdx)
    {
        if (energy == 0)
            return false;
        
        LOGGER.fine("Using item...");
        
        if (inventory.get(itemIdx).use(block))
            inventory.remove(itemIdx);
        
        // egy Item használata egy munkába kerül.
        changeEnergy(-1);
        return true;
    }
    
    //megváltoztatható vele a karakter aktuális energiájának száma
    public void changeEnergy(int amount)
    {
        energy += amount;
        LOGGER.fine("Energy changed to " + energy);
    }
    
    public void setEnergy(int value)
    {
        energy = value;
    }

    public void changeHealth(int value)
    {
        health += value;
        if (health == 0)
            Game.get().lose();
    }

    public int getHealth()
    {
        return health;
    }

    // ennél a játékosnál van-e a Flare (egy játék során pontosan egy jelzőfény van)
    public boolean hasFlare()
    {
        return hasFlare;
    }
    
    // felvettünk egy Flare-t
    public void setHasFlare()
    {
        LOGGER.fine("Character has a flare now!");
        hasFlare = true;
    }

    // ennél a játékosnál van-e a Bullet (egy játék során pontosan egy töltény van)
    public boolean hasBullet()
    {
        return hasBullet;
    }
    
    public boolean hasSuit()
    {
        return hasSuit;
    }
    
    public int getID()
    {
        return ID;
    }
    
    // felvettünk egy Bullet-et
    public void setHasBullet()
    {
        LOGGER.fine("Character has a bullet now!");
        hasBullet = true;
    }
    
    // csak UsableItem-et tudunk hozzáadni az inventoryhoz.
    public void addItem(UsableItem item)
    {
        inventory.add(item);
    }
    
    //visszaadja a karakter inventoryjának tömbjét
    public ArrayList<UsableItem> getInventory()
    {
        return inventory;
    }

    // a játékos olyan mezőn áll-e amin van igloo?
    public boolean isInIgloo()
    {
        return block.hasIgloo();
    }
    
    // a játékos olyan mezőn áll-e amin van igloo?
    public boolean isInTent()
    {
        return block.hasTent();
    }
    
    public int getEnergy()
    {
        return energy;
    }
    
    protected int energy; // munkák száma
    protected int health; // élet
    private boolean hasSuit = false; // van-e a karakteren búvárruha?
    private boolean hasFlare = false;
    private boolean hasBullet = false;
    
    private int ID;
    // az inventory csak használható Item-eket tárolhat. Ezeknek az Item-eknek van Use() függvényük is
    
    private ArrayList<UsableItem> inventory;
}
