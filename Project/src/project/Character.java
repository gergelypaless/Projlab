package project;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Character
{
    private static final Logger LOGGER = Logger.getLogger( Character.class.getName() );
    
    // A karaktereknek lett egy IDjük. Ez az ID egy indexként tud működni a Game osztály characters tömbjében
    public Character(int ID)
    {
        this.ID = ID;
        this.energy = 5; // mindenkinek 5 munkája van
        inventory = new ArrayList<>();
    }
    
    public void move(Direction d)
    {
        LOGGER.fine("Moving in direction: " + d.toString());
        
        IceBlock iceBlockToMoveTo = block.getNeighbours().get(d);
        block.remove(this);
        iceBlockToMoveTo.accept(this);
        setIceBlock(iceBlockToMoveTo);
        
        // a mozgás egy munkába kerül
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
        
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
    
    public abstract void changeHealth(int value);
    
    public void changeEnergy(int value)
    {
        energy += value;
        LOGGER.fine("Energy changed to " + energy);
    }
    
    public int getHealth()
    {
        return 0;
    }
    
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
    
    /* az inicializálásnál kell, így tudunk átadni a karakternek egy IceBlock-ot, a karakter ezen a blockon fog állni
       ez később a konstruktorba fog kerülni*/
    public void setIceBlock(IceBlock block)
    {
        LOGGER.fine("Character is on a new IceBlock");
        this.block = block;
    }
    
    public int getID()
    {
        return ID;
    }
    
    // csak UsableItem-et tudunk hozzáadni az inventoryhoz.
    public void addItem(UsableItem item)
    {
        inventory.add(item);
    }
    
    public ArrayList<UsableItem> getInventory()
    {
        return inventory;
    }
    
    public boolean isInIgloo()
    {
        return block.hasIgloo();
    }
    
    protected int energy;
    private int health;
    private boolean isInWater;
    private boolean hasSuit = false;
    
    private boolean hasFlare = false;
    
    private boolean hasBullet = false;
    private int ID = -1;
    
    // a block amin a karakter áll
    protected IceBlock block;
    
    // az inventory csak használható Item-eket tárolhat. Ezeknek az Item-eknek van Use() függvényük is
    private ArrayList<UsableItem> inventory;
}
