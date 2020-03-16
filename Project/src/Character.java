

public abstract class Character
{
    private int energy;
    private int health;
    private boolean isInWater;
    private boolean hasSuit;
    private boolean hasFlare;
    private boolean hasBullet;
    private IceBlock block;
    private Item inventory;
    
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
    
    public abstract void UseAbility();
    
    public void SetHasSuit()
    {
    }
    
    public boolean IsDrowning()
    {
        return false;
    }
    
    public void UseItem()
    {
    }
    
    public abstract void ChangeHealth(int value);
    
    public int GetHealth()
    {
        return 0;
    }
    
    public boolean HasFlare()
    {
        return false;
    }
    
    public void SetHasFlare()
    {
    }
    
    public boolean HasBullet()
    {
        return false;
    }
    
    public void SetHasBullet()
    {
    }
}
