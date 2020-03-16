
public abstract class IceBlock
{
    private int amountOfSnow;
    private int stability;
    private Character characters;
    private Item item;
    
    public void SetNeighbour(Direction d, IceBlock block)
    {
    }
    
    public int GetSnow()
    {
        return 0;
    }
    
    public int GetStability()
    {
        return 0;
    }
    
    public void ChangeAmountOfSnow(int i)
    {
    }
    
    public abstract void Accept(Character c);
    
    public abstract void Remove(Character c);
    
    public Item RemoveItem()
    {
        return null;
    }
    
    public boolean PlaceIgloo()
    {
        return false;
    }
    
    public boolean HasIgloo()
    {
        return false;
    }
    
    public Character[] GetCharacters()
    {
        return null;
    }
}
