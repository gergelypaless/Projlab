package com.csakcintanyer.bme.projlab;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public abstract class IceBlock
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
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
        // szomszédos IceBlock beállítása a megfelelő irányban, egy HashMap<Direction, IceBlock>-ot használva
        neighbours.put(d, block);
    }
    
    public int getSnow()
    {
        return amountOfSnow;
    }
    
    public int getStability()
    {
        LOGGER.finest("Stability getter");
        return 4; // ez a stability változót kéne visszaadja, de az egyszerűség kedvéért most egy fix értéket írtunk be
    }

    // hóréteg változtatása bizonyos értékkel
    public void changeAmountOfSnow(int i)
    {
        LOGGER.fine("Changing amount of snow by " + i);
    }
    
    public abstract void accept(Character c);
    
    public abstract void remove(Character c);
    
    // valaki fel akarja venni az Item-et ami ezen az IceBlockon van, visszaadjuk az IceBlockon lévő Itemet
    public CollectableItem removeItem()
    {
        LOGGER.fine("Removing Item from IceBlock");
    
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        // megkérdezzük, hogy milyen Item-et szeretne felvenni. Ez a künnyű tesztelhetőség érdekében kell.
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
        LOGGER.fine("Can we place igloo on this IceBlock?");
        // csak StableBlock-ra lehet igloo-t tenni, így csak az fog igazat visszaadni
        return false;
    }
    
    // van-e az IceBlockon igloo
    public boolean hasIgloo()
    {
        LOGGER.finest("Has igloo getter");
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
    
    // hó mennyisége
    private int amountOfSnow;
    
    // hány karaktert bír el
    private int stability;
    
    // karakterek az IceBlockon.
    private ArrayList<Character> characters = new ArrayList<>();
    
    // Item az IceBlockon
    private CollectableItem item;
    
    // IceBlock szomszédai egy HashMap-ben tárolva
    private HashMap<Direction, IceBlock> neighbours = new HashMap<>();
}
