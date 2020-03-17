package project;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class IceBlock
{
    private static final Logger LOGGER = Logger.getLogger( IceBlock.class.getName() );
    
    public IceBlock(int amountOfSnow, int stability)
    {
        this(amountOfSnow, stability, null);
        LOGGER.finest("IceBlock constructor");
    }
    
    public IceBlock(int amountOfSnow, int stability, Item item)
    {
        this.amountOfSnow = amountOfSnow;
        this.stability = stability;
        this.item = item;
    }
    
    public void setNeighbour(Direction d, IceBlock block)
    {
        LOGGER.finer("Setting neighbour in Direction: " + d.toString());
    }
    
    public int getSnow()
    {
        return 0;
    }
    
    public int getStability()
    {
        LOGGER.finest("Stability getter");
        return stability;
    }
    
    public void changeAmountOfSnow(int i)
    {
    }
    
    public abstract void accept(Character c);
    
    public abstract void remove(Character c);
    
    public Item removeItem()
    {
        return null;
    }
    
    public boolean placeIgloo()
    {
        return false;
    }
    
    public boolean hasIgloo()
    {
        return false;
    }
    
    public ArrayList<Character> getCharacters()
    {
        LOGGER.finest("IceBlock Characters getter");
        return characters;
    }
    
    private int amountOfSnow;
    private int stability;
    private ArrayList<Character> characters = new ArrayList<>();
    private Item item;
}
