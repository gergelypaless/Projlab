package com.csakcintanyer.bme.projlab;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

public class Game
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( Game.class.getName() );
    
    private Game()
    {
        LOGGER.finest("Game constructor");
    }
    
    public void init(IceMap iceMap, ArrayList<Character> characters, Bear bear) // TODO: + Bear
    {
        init(iceMap, characters, bear, -1);
    }
    public void init(IceMap iceMap, ArrayList<Character> characters, Bear bear, int snowInXTurns) // TODO: + Bear
    {
        map = iceMap;
        this.characters = characters;
        this.bear = bear;
        if (snowInXTurns == -1)
            deterministic = false;
        else
            deterministic = true;
        
        this.snowInXTurns = snowInXTurns;
    }
    
    // játék kezdése
    public void start()
    {
        LOGGER.fine("Starting game");
        try
        {
            Random random = new Random();
            
            int turns = 0;
            nextRound(turns % characters.size());
            turns++;
            while (!gameOver())
            {
                if (deterministic && turns % snowInXTurns == 0)
                {
                    snowStorm();
                }
                else if (!deterministic)
                {
                    if (random.nextInt(2) == 1) // 50%
                    {
                        snowStorm();
                    }
                }
                if (gameOver())
                    break;
    
                nextRound(turns % characters.size());
                
                int rand = random.nextInt(4);
                if (rand == 0)
                    bear.move(Direction.LEFT);
                if (rand == 1)
                    bear.move(Direction.RIGHT);
                if (rand == 2)
                    bear.move(Direction.UP);
                if (rand == 3)
                    bear.move(Direction.DOWN);
                
                turns++;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private String getIceBlockTypeAsString(IceBlock block)
    {
        if (block instanceof StableBlock)
            return "StableBlock";
        else if (block instanceof UnstableBlock)
            return "UnstableBlock";
        else if (block instanceof EmptyBlock)
            return "EmptyBlock";
        
        return null;
    }
    
    // következő kör
    public void nextRound(int whichPlayer) throws IOException
    {
        LOGGER.fine("Changing round");
        
        currentlyMovingCharacter = characters.get(whichPlayer);
        if (currentlyMovingCharacter.isDrowning())
        {
            lose();
            return;
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (!(input = reader.readLine()).equals("end") && !(isLost || isWin) && currentlyMovingCharacter.getEnergy() > 0)
        {
            String[] elements = input.split(" ");
            switch (elements[0])
            {
                case "move":
                    Direction d;
                    if (elements[1].equals("left"))
                        d = Direction.LEFT;
                    else if (elements[1].equals("right"))
                        d = Direction.RIGHT;
                    else if (elements[1].equals("up"))
                        d = Direction.UP;
                    else if (elements[1].equals("down"))
                        d = Direction.DOWN;
                    else
                        throw new IllegalArgumentException("wrong direction");
    
                    currentlyMovingCharacter.move(d);
                    break;
                case "use":
                    if (elements[1].equals("item"))
                    {
                        currentlyMovingCharacter.useItem(Integer.parseInt(elements[2]));
                    } else if (elements[1].equals("ability"))
                    {
                        currentlyMovingCharacter.useAbility();
                    }
                    break;
                case "pick_up":
                    currentlyMovingCharacter.pickUp();
                    break;
                case "stat":
                    // Type
                    if (currentlyMovingCharacter instanceof Eskimo)
                        System.out.println("Type: Eskimo");
                    else if (currentlyMovingCharacter instanceof Explorer)
                        System.out.println("Type: Explorer");
    
                    // Energy
                    System.out.println("Energy: " + currentlyMovingCharacter.getEnergy());
                    // Health
                    System.out.println("Health: " + currentlyMovingCharacter.getHealth());
                    // HasSuit
                    System.out.println("HasSuit: " + (currentlyMovingCharacter.hasSuit() ? "True" : "False"));
                    // HasFlare
                    System.out.println("HasFlare: " + (currentlyMovingCharacter.hasFlare() ? "True" : "False"));
                    // HasBullet
                    System.out.println("HasBullet: " + (currentlyMovingCharacter.hasBullet() ? "True" : "False"));
                    // ID
                    System.out.println("ID: " + currentlyMovingCharacter.getID());
                    // Items
                    System.out.println("Items:");
                    for (UsableItem item : currentlyMovingCharacter.getInventory())
                    {
                        System.out.println("\t" + item.toString());
                    }
                    break;
                case "block":
                    IceBlock block = currentlyMovingCharacter.getBlock();
                    // Type
                    if (block instanceof StableBlock)
                        System.out.println("Type: StableBlock");
                    else if (block instanceof UnstableBlock)
                        System.out.println("Type: UnstableBlock");
                    else if (block instanceof EmptyBlock)
                        System.out.println("Type: EmptyBlock");
                    
                    // AmountOfSnow
                    System.out.println("AmountOfSnow" + block.getSnow());
                    // Stability
                    System.out.println("Stability: " + block.getStability());
                    // HasIgloo
                    System.out.println("HasIgloo: " + block.hasIgloo());
                    // HasTent
                    System.out.println("HasTent: " + block.hasTent());
                    // Item
                    System.out.println("Item: \n\t" + block.getItem());
                    // Entities
                    System.out.println("Entities:");
                    for (Entity entity : block.getEntities())
                    {
                        if (entity instanceof Eskimo)
                            System.out.println("Eskimo");
                        else if (entity instanceof Explorer)
                            System.out.println("Explorer");
                        else if (entity instanceof Bear)
                            System.out.println("Bear");
                    }
                    // Neighbours
                    System.out.println("Neighbours:");
                    System.out.println(getIceBlockTypeAsString(block.getNeighbours().get(Direction.LEFT)) + " - LEFT");
                    System.out.println(getIceBlockTypeAsString(block.getNeighbours().get(Direction.RIGHT)) + " - RIGHT");
                    System.out.println(getIceBlockTypeAsString(block.getNeighbours().get(Direction.UP)) + " - UP");
                    System.out.println(getIceBlockTypeAsString(block.getNeighbours().get(Direction.DOWN)) + " - DOWN");
                    break;
                    
                case "clear":
                    currentlyMovingCharacter.clear();
                    break;
                default:
                    throw new IllegalArgumentException("wrong command");
            }
        }
    }
    
    // ezt a függvényt kell meghívni, ha a győzelem feltétele teljesült
    public void win()
    {
        LOGGER.fine("You win!!!");
        isWin = true;
    }
    
    // ezt a függvényt kell meghívni, ha a vereség feltétele teljesült
    public void lose()
    {
        LOGGER.fine("End of the game");
        isLost = true;
    }
    
    private boolean gameOver()
    {
        return (isLost || isWin);
    }
    
    // Hóvihar van
    private void snowStorm()
    {
        LOGGER.fine("Snow storm is now!");
    
        // végigmegyünk a karaktereken, megnézük hogy iglooban vannak-e, ha nem akkor egy élet minusz
        for (Character c : characters)
        {
            if (!(c.isInIgloo() || c.isInTent()))
                c.changeHealth(-1);
        }
        
        // az IceBlockok hórétegének nővelése
        ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
        for (int i = 0; i < blocks.size(); ++i)
        {
            for (int j = 0; j < blocks.get(0).size(); ++j)
            {
                blocks.get(i).get(j).changeAmountOfSnow(1);
            }
        }
    }
    
    public IceMap getIceMap()
    {
        return map;
    }
    
    private boolean isWin; // nyertünk-e?
    private boolean isLost; // vesztettünk-e?
    
    // a jégmező
    private IceMap map;
    
    // karakterek akik játszanak
    private ArrayList<Character> characters = new ArrayList<>();
    
    // éppen soron lévő játékos
    private Character currentlyMovingCharacter;
    
    // bear
    private Bear bear = null;
    
    private boolean deterministic;
    public int snowInXTurns;
    
    // a Game osztály a Singleton tervezési formát követi, hisz a program futása során csak egy, a játékot vezérlő
    // osztálypéldány létezhet
    public static Game get()
    {
        if (instance == null)
            instance = new Game();
        
        return instance;
    }
    private static Game instance;
}
