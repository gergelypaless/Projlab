package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public abstract class IceBlock
{
    private static final Logger LOGGER = Logger.getLogger( IceBlock.class.getName() );
    
    public IceBlock(int amountOfSnow, int stability)
    {
        this(amountOfSnow, stability, null);
        LOGGER.finest("IceBlock constructor");
    }
    
    public IceBlock(int amountOfSnow, int stability, CollectableItem item)
    {
        this.amountOfSnow = amountOfSnow;
        this.stability = stability;
        this.item = item;
    }
    
    public void setNeighbour(Direction d, IceBlock block)
    {
        LOGGER.finer("Setting neighbour in Direction: " + d.toString());
        neighbours.put(d, block);
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
    
    public CollectableItem removeItem()
    {
        LOGGER.fine("Removing Item from IceBlock");
    
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("Which item to pick up (suit, flare, food, shovel, bullet, gun, rope)");
        try
        {
            input = reader.readLine();
            if (input.equals("suit"))
            {
                return new Suit();
            }
            else if (input.equals("flare"))
            {
                return new Flare();
            }
            else if (input.equals("food"))
            {
                return new Food();
            }
            else if (input.equals("shovel"))
            {
                return new Shovel();
            }
            else if (input.equals("bullet"))
            {
                return new Bullet();
            }
            else if (input.equals("gun"))
            {
                return new Gun();
            }
            else if (input.equals("rope"))
            {
                return new Rope();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
    
    public HashMap<Direction, IceBlock> getNeighbours()
    {
        LOGGER.finest("Neighbours getter");
        return neighbours;
    }
    
    private int amountOfSnow;
    private int stability;
    private ArrayList<Character> characters = new ArrayList<>();
    private CollectableItem item;
    private HashMap<Direction, IceBlock> neighbours = new HashMap<>();
}
