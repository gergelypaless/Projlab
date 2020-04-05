package com.csakcintanyer.bme.projlab;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Character
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Character.class.getName() );
    
    // A karaktereknek lett egy IDjük. Ez az ID egy indexként tud működni a Game osztály characters tömbjében
    public Character(int ID)
    {
        this.ID = ID;
        this.energy = 5; // mindenkinek 5 munkája van
        inventory = new ArrayList<>();
    }

    // a karakter lépett a jégmezőn.
    public void move(Direction d)
    {
        LOGGER.fine("Moving in direction: " + d.toString());

        // lekérjük annak az iceBlocknak a szomszédait amin állunk, majd lekérjük a megfelelő irányban lévőt
        IceBlock iceBlockToMoveTo = block.getNeighbours().get(d);

        // mozgatjuk a játékost az IceBlock accept és remove függvényeivel
        block.remove(this);
        iceBlockToMoveTo.accept(this);

        // beállítjuk a karakternek az új IceBlockot.
        setIceBlock(iceBlockToMoveTo);
        
        // a mozgás egy munkába kerül
        energy--;
        LOGGER.fine("Energy decreased to " + energy);

        // ha Emptyblockra léptünk akkor vízbe estünk (ezt az EmptyBlock állítja be)
        if (isInWater)
            LOGGER.fine("Next player's turn");
    }
    
    public void clear()
    {
        LOGGER.fine("Clearing...");
        block.changeAmountOfSnow(-1);

        // a tisztítás egy munkába kerül
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
    }
    
    public void pickUp()
    {
        LOGGER.fine("Picking up item");
    
        // a blocktól visszakapunk egy itemet
        CollectableItem item = block.removeItem();
        // "értesítjük" az itemet, hogy felvettük
        item.interactWithCharacter(this);
    }
    
    // amikor a karakter vízbe esik
    public void fallIn()
    {
        LOGGER.fine("Character fell in water");
        isInWater = true;
    }

    // a karaktert kimentette valaki
    public void save()
    {
        LOGGER.fine("Character was saved");
        isInWater = false;
    }
    
    public abstract void useAbility();
    
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
    public void useItem(int itemIdx)
    {
        LOGGER.fine("Using item...");
        
        inventory.get(itemIdx).use(block);
        
        // egy Item használata egy munkába kerül.
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
    }
    
    //megváltoztatható vele a karakter aktuális energiájának száma
    public void changeEnergy(int value)
    {
        energy += value;
        LOGGER.fine("Energy changed to " + energy);
    }

    public abstract void changeHealth(int value);

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
    
    // felvettünk egy Bullet-et
    public void setHasBullet()
    {
        LOGGER.fine("Character has a bullet now!");
        hasBullet = true;
    }
    
    /* ezzel tudunk átadni a karakternek egy IceBlock-ot, a karakter ezen a blockon fog állni */
    public void setIceBlock(IceBlock block)
    {
        LOGGER.fine("Character is on a new IceBlock");
        this.block = block;
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
    
    protected int energy; // munkák száma
    protected int health; // élet
    private boolean isInWater; // vízben vagyunk-e?
    private boolean hasSuit = false; // van-e a karakteren búvárruha?
    private boolean hasFlare = false;
    private boolean hasBullet = false;
    private int ID = -1;
    
    // a block amin a karakter áll
    protected IceBlock block;
    
    // az inventory csak használható Item-eket tárolhat. Ezeknek az Item-eknek van Use() függvényük is
    private ArrayList<UsableItem> inventory;
}
