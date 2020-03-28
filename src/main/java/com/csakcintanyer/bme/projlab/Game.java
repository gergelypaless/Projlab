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
        
        // karakterek és IceMap inicializálása
        createCharacters();
        createMap(createItems());
    }
    
    // Itemek létrehozása. Ezeket átadjuk majd az IceMapnek
    private ArrayList<CollectableItem> createItems()
    {
        LOGGER.fine("Creating Items");
        ArrayList<CollectableItem> itemsToAdd = new ArrayList<>();
        itemsToAdd.add(new Suit());
        itemsToAdd.add(new Rope());
        itemsToAdd.add(new Shovel());
        itemsToAdd.add(new Gun());
        itemsToAdd.add(new Bullet());
        itemsToAdd.add(new Flare());
        itemsToAdd.add(new Food());
        return itemsToAdd;
    }
    
    // karakterek létrehozása, és eltárolása a characters ArrayList-ben
    private void createCharacters()
    {
        LOGGER.fine("Creating characters");
        characters.add(new Eskimo());
        characters.add(new Eskimo());
        characters.add(new Explorer());
        characters.add(new Explorer());
    }
    
    // IceMap inicializálása
    private void createMap(ArrayList<CollectableItem> itemsToAdd)
    {
        LOGGER.fine("Initialization");
        
        // 1*1-es az IceMap
        map = new IceMap(1,1, characters.size(), characters, itemsToAdd);
        // beállítjuk az egyes IceBlockok szomszédait
        map.setNeighboursOnTheMap();
    }
    
    // játék kezdése
    public void start()
    {
        LOGGER.fine("Starting game");
        try
        {
            // ebben a testprogramban csak egy kört játszunk le
            nextRound();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    // következő kör
    public void nextRound() throws IOException
    {
        LOGGER.fine("Changing round");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
    
        // minden kör elején a characters tömb 0. elemén lévő játékos fog kezdeni
        currentlyMovingCharacter = characters.get(0);
        
        Random random = new Random();
        int numOfTurns = characters.size();
        for (int i = 0; i < numOfTurns; ++i)
        {
            // az i. karakter lép
            
            LOGGER.fine("Next player");
            
            // Hóvihar 50%-os valószínűséggel jön
            if (random.nextInt(2) == 0)
                snowStorm();
            
            // az éppen aktuálisan soron lévő játékos beállítása
            currentlyMovingCharacter = characters.get(i);
            
            LOGGER.fine("You have " + (numOfTurns - i) + " turns left. Player" + (i + 1));
            
            // mozgás
            System.out.println("What direction should i move? (up/down/left/right)");
            input = reader.readLine();
            if (input.equals("up"))
                currentlyMovingCharacter.move(Direction.UP);
            if (input.equals("down"))
                currentlyMovingCharacter.move(Direction.DOWN);
            if (input.equals("left"))
                currentlyMovingCharacter.move(Direction.LEFT);
            if (input.equals("right"))
                currentlyMovingCharacter.move(Direction.RIGHT);
    
            // fullad-e a karakter?
            if (checkDrowning(reader)) return;
    
            // képesség használata
            currentlyMovingCharacter.useAbility();
            
            // IceBlock tisztítása
            currentlyMovingCharacter.clear();
            
            // Item felvétele
            currentlyMovingCharacter.pickUp();
    
            // ha használható Item-et vettünk fel, akkor a karakter inventory-ában van egy elem
            if (currentlyMovingCharacter.getInventory().size() > 0)
            {
                System.out.println("Which item to use? You have " + currentlyMovingCharacter.getInventory().size() + " item. (0, 1, 2... stb)");
                input = reader.readLine();
                int index = Integer.parseInt(input);
                currentlyMovingCharacter.useItem(index);
                
                // ha a Gun-t használtuk, és a győzelem minden feltétele teljesült akkor ezen a ponton az isWin igaz lesz
                if (isWin)
                    return;
            }
        }
        LOGGER.fine("No more rounds left, end of the game");
        
    }
    
    private boolean checkDrowning(BufferedReader reader) throws IOException
    {
        String input;
        // megkérdezzük a felhasználót, hogy szeretné-e, ha a karaktere fuldokolna=meghalna.
        // Így ezt a működést is tudjuk tesztelni
        System.out.println("Should player drowning? (y/n) Player is " + (currentlyMovingCharacter.isDrowning() ? "" : "not ") + "drowning at the moment!");
        input = reader.readLine();
        if (input.equals("y"))
        {
            // ha a válasz az, hogy meg kellene, hogy fulladjon, akkor vesztettünk
            lose();
            return true;
        }
        return false;
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
    
    // Hóvihar van
    private void snowStorm()
    {
        LOGGER.fine("Snow storm is now!");
    
        // végigmegyünk a karaktereken, megnézük hogy iglooban vannak-e, ha nem akkor egy élet minusz
        for (Character c : characters)
        {
            if (c.isInIgloo())
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
    
    private boolean isWin; // nyertünk-e?
    private boolean isLost; // vesztettünk-e?
    
    // a jégmező
    private IceMap map;
    
    // karakterek akik játszanak
    private ArrayList<Character> characters = new ArrayList<>();
    
    // éppen soron lévő játékos
    private Character currentlyMovingCharacter;
    
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
