package com.csakcintanyer.bme.projlab;
import javax.swing.*;
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
        deterministic = snowInXTurns >= 0;
        
        System.out.println("Snow in every " + snowInXTurns + " turns");
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
                System.out.println("Bear moved!");
                
                turns++;
            }
            
            IOLanguage.PrintCharacter(currentlyMovingCharacter);
            IOLanguage.PrintBlock(currentlyMovingCharacter.getBlock());
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
        
        currentlyMovingCharacter.setEnergy(4);
        
        System.out.println("Player " + whichPlayer + "'s turn");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (currentlyMovingCharacter.getEnergy() > 0 && !(input = reader.readLine()).equals("end") && !(isLost || isWin))
        {
            if (input.equals(""))
                continue;
            
            String[] elements = input.split(" ");
            switch (elements[0])
            {
                case "move":
                    if (currentlyMovingCharacter.move(IOLanguage.GetDirection(elements[1])))
                    {
                        System.out.println("OK, character moved");
                        
                        if (bear.getBlock() == currentlyMovingCharacter.getBlock())
                            lose();
                    }
                    else
                    {
                        System.out.println("You cannot move " + elements[1]);
                    }
                    break;
                case "use":
                    if (elements[1].equals("item"))
                    {
                        if (currentlyMovingCharacter.useItem(Integer.parseInt(elements[2])))
                            System.out.println("OK, item used");
                        else
                            System.out.println("Item was not used");
                    }
                    else if (elements[1].equals("ability"))
                    {
                        if (currentlyMovingCharacter.useAbility())
                            System.out.println("OK, ability used");
                        else
                            System.out.println("Ability was not used");
                    }
                    break;
                case "pick_up":
                    if (currentlyMovingCharacter.pickUp())
                        System.out.println("OK, item picked up");
                    else
                        System.out.println("Item was not picked up");
                    break;
                case "stat":
                    IOLanguage.PrintCharacter(currentlyMovingCharacter);
                    break;
                case "block":
                    IOLanguage.PrintBlock(currentlyMovingCharacter.getBlock());
                    break;
                    
                case "clear":
                    if (currentlyMovingCharacter.clear())
                        System.out.println("OK, iceblock cleared");
                    else
                        System.out.println("Iceblock was not cleared");
                    break;
                default:
                    throw new IllegalArgumentException("wrong command");
            }
        }
        System.out.println("Your turn is over");
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
    
        System.out.println("Oh no, SNOWSTORM!!");
        
        // végigmegyünk a karaktereken, megnézük hogy iglooban vannak-e, ha nem akkor egy élet minusz
        for (Character c : characters)
        {
            if (!(c.isInIgloo() || c.isInTent()))
                c.changeHealth(-1);
        }
        
        // az IceBlockok hórétegének nővelése
        ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
        for (ArrayList<IceBlock> block : blocks)
        {
            for (IceBlock iceBlock : block)
            {
                iceBlock.changeAmountOfSnow(1);
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
