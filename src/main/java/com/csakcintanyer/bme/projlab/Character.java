package com.csakcintanyer.bme.projlab;

import java.util.ArrayList;

public abstract class Character extends Entity
{
    
    // A karakter osztály rendelkezik egy ID-vel. Ez az ID egy indexként működik a Game osztály characters tömbjében
    public Character(int ID)
    {
        this.ID = ID;
        setEnergy(4); // mindenkinek 4 energiája van
        inventory = new ArrayList<>();
    }

    // a karakter lép egyet a jégmezőn.
    public boolean move(Direction d)
    {
        if (energy == 0) //ha nincs több energiája, akkor nem tud lépni többet ebben a körben
            return false;
        
        // lekérjük annak az iceBlocknak a szomszédait amin állunk, majd lekérjük a megfelelő irányban lévőt
        IceBlock iceBlockToMoveTo = block.getNeighbours().get(d);
        
        if (iceBlockToMoveTo == null) // ha az adott irányba nincs jégtábla, akkor a lépés sikertelen
            return false;

        // mozgatjuk a játékost az IceBlock accept és remove függvényeivel
        block.remove(this);
        iceBlockToMoveTo.accept(this);

        // beállítjuk a karakternek az új IceBlockot.
        setIceBlock(iceBlockToMoveTo);
        
        // a mozgás egy munkába kerül
        changeEnergy(-1);
        return true; // sikeres lépés
    }

    // a játékos eltakarít 1 réteg havat
    public boolean clear()
    {
        /*
        * Ha nincs már több energiája a játékosnak vagy nincs 1 réteg hó sem a táblán,
        * akkor a takarítás sikertelen
        * */
        if (energy == 0 || block.getSnow() == 0)
            return false;

        // csökkentjük a jégtáblán lévő hórétegek számát 1-el
        block.changeAmountOfSnow(-1);

        // a tisztítás egy munkába kerül
        changeEnergy(-1);
        return true; // sikeres hóeltakarítás
    }

    // a játékos felvesz 1 tárgyat
    public boolean pickUp()
    {
        if (energy == 0 || inventory.size() == 6) // ha nincs elég energiája a játékosnak akkor nem sikerül
            return false;
    
        // a blocktól visszakapunk egy itemet
        CollectableItem item = block.removeItem();
        if (item == null) // ha nincs tárgy a jégtáblán nem sikerül
            return false;
        
        try
		{
            // "értesítjük" az itemet, hogy felvettük
            item.interactWithCharacter(this); // kivételt dob ha nem lehet felvenni
        } catch (IllegalArgumentException e) {
            block.setItem(item); // nem lehet felvenni ezért visszatesszük az iceblockra
            return false;
        }
        changeEnergy(-1); // Item használata egy munka.
        return true; // sikeres
    }
    
    public abstract boolean useAbility(); // Az Eskimo és az Explorer képessége
    
    // a játékos felvesz 1 Suit-ot
    public void setHasSuit()
    {
        hasSuit = true;
    }
    
    // fuldoklunk-e?
    public boolean isDrowning()
    {
        // akkor fuldoklunk (Drowning) ha a vízben vagyunk és nincs rajtunk búvárruha
        return isInWater && !hasSuit;
    }
    
    // Egy Item használata. Az itemIdx paraméter a Character inventory tömbjében indexel, így kapunk egy Itemet
    public boolean useItem(int itemIdx)
    {
        if (energy == 0) // ha nincs elég energiája a játékosnak akkor nem sikerül
            return false;

        try
        {
            // ha a UsableItem elhasználódik töröljuk az Inventory-ból
            if (inventory.get(itemIdx).use(block))
                inventory.remove(itemIdx);
    
            // egy Item használata egy munkába kerül.
            changeEnergy(-1);
            return true; // sikeres
        } catch (IllegalArgumentException e)
        {
            System.out.println(e.toString());
        }
        return false;
    }
    
    //megváltoztatható vele a karakter aktuális energiájának száma
    public void changeEnergy(int amount)
    {
        energy += amount;
    }

    // a játékos energiájának beállítása a kör elején a paraméter értékére
    public void setEnergy(int value)
    {
        energy = value;
    }

    // megváltoztatja a játékos testhőjét a paraméter értékével
    public void changeHealth(int value)
    {
        health += value;
        if (health == 0) // ha a változással a játékos testhője 0-ra csökken -> Game over
            Game.get().lose();
    }

    // visszaadja a játékos testhőjét
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
        hasFlare = true;
    }

    // ennél a játékosnál van-e a Bullet (egy játék során pontosan egy töltény van)
    public boolean hasBullet()
    {
        return hasBullet;
    }

    // visszaadja, hogy a játékoson van-e búvárruha
    public boolean hasSuit()
    {
        return hasSuit;
    }

    // visszaadja a maximum health értékét
    public int getMaxHealth()
    {
        return maxHealth;
    }
    
    // visszadaja a játékos ID-ját
    public int getID()
    {
        return ID;
    }
    
    // felvettünk egy Bullet-et
    public void setHasBullet()
    {
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

    // visszadaja a játékos energiaszintjét
    public int getEnergy()
    {
        return energy;
    }
    
    protected int energy; // munkák száma
    protected int health; // élet
    private boolean hasSuit = false; // van-e a karakteren búvárruha?
    private boolean hasFlare = false; // a karakternél van-e a Flare?
    private boolean hasBullet = false; // a karakternél van-e a Bullet?
    
    private int ID; // játékos azonosítója
    protected int maxHealth; // maximum élet

    // az inventory csak használható Item-eket tárolhat. Ezeknek az Item-eknek van Use() függvényük is
    private ArrayList<UsableItem> inventory;
}
