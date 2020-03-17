package project;

import java.util.logging.Logger;

public abstract class Character
{
    private static final Logger LOGGER = Logger.getLogger( Character.class.getName() );
    
    public Character(int ID)
    {
        this.ID = ID;
    }
    
    public void Move(Direction d)
    {
    }
    
    public void Clear()
    {
    }
    
    public void PickUp()
    {
    }
    
    public void FallIn()
    {
    }
    
    public abstract void useAbility();
    
    public void SetHasSuit()
    {
    }
    
    public boolean isDrowning()
    {
        return isInWater && !hasSuit;
    }
    
    public void useItem()
    {
    }
    
    public abstract void changeHealth(int value);
    
    public int getHealth()
    {
        return 0;
    }
    
    public boolean hasFlare()
    {
        return false;
    }
    
    public void setHasFlare()
    {
    }
    
    public boolean hasBullet()
    {
        return false;
    }
    
    public void setHasBullet()
    {
    }
    
    public int getID()
    {
        return ID;
    }
    
    private int energy;
    private int health;
    private boolean isInWater;
    private boolean hasSuit;
    private boolean hasFlare;
    
    private boolean hasBullet;
    
    private int ID;
    private IceBlock block;
    private Item inventory;
}
