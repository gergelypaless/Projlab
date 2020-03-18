package project;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Character
{
    private static final Logger LOGGER = Logger.getLogger( Character.class.getName() );
    
    public Character(int ID)
    {
        this.ID = ID;
        this.energy = 5;
        inventory = new ArrayList<>();
    }
    
    public void move(Direction d)
    {
        LOGGER.fine("Moving in direction: " + d.toString());
    
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
        
        if (isInWater)
            LOGGER.fine("Next player's turn");
    }
    
    public void clear()
    {
        LOGGER.fine("Clearing...");
        block.changeAmountOfSnow(-1);
    
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
    }
    
    public void pickUp()
    {
        LOGGER.fine("Picking up item");
        
        CollectableItem item = block.removeItem();
        item.InteractWithCharacter(this);
    }
    
    public void fallIn()
    {
        LOGGER.fine("Character fell in water");
        isInWater = true;
    }
    
    public abstract void useAbility();
    
    public void setHasSuit()
    {
        LOGGER.fine("Character has a suit now!");
        hasSuit = true;
    }
    
    public boolean isDrowning()
    {
        LOGGER.finer("Dorwing getter. Has suit checked");
        return isInWater && !hasSuit;
    }
    
    public void useItem(int itemIdx)
    {
        LOGGER.fine("Using item...");
        
        inventory.get(itemIdx).Use(block);
        
        energy--;
        LOGGER.fine("Energy decreased to " + energy);
    }
    
    public abstract void changeHealth(int value);
    
    public int getHealth()
    {
        return 0;
    }
    
    public boolean hasFlare()
    {
        return hasFlare;
    }
    
    public void setHasFlare()
    {
        LOGGER.fine("Character has a flare now!");
        hasFlare = true;
    }
    
    public boolean hasBullet()
    {
        return hasBullet;
    }
    
    public void setHasBullet()
    {
        LOGGER.fine("Character has a bullet now!");
        hasBullet = true;
    }
    
    public void setIceBlock(IceBlock block)
    {
        this.block = block;
    }
    
    public int getID()
    {
        return ID;
    }
    
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
    
    private boolean hasFlare;
    
    private boolean hasBullet;
    private int ID;
    protected IceBlock block;
    
    private ArrayList<UsableItem> inventory;
}
