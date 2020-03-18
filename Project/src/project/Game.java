package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

public class Game
{
    private static final Logger LOGGER = Logger.getLogger( Game.class.getName() );
    
    private Game()
    {
        LOGGER.finest("Game constructor");
        createCharacters();
        createMap(createItems());
    }
    
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
    
    private void createCharacters()
    {
        LOGGER.fine("Creating characters");
        characters.add(new Eskimo());
        characters.add(new Eskimo());
        characters.add(new Explorer());
        characters.add(new Explorer());
    }
    
    private void createMap(ArrayList<CollectableItem> itemsToAdd)
    {
        LOGGER.fine("Initialization");
        
        map = new IceMap(15, 15, 1, characters, itemsToAdd);
        map.setNeighboursOnTheMap();
    }
    
    public void start()
    {
        LOGGER.fine("Starting game");
        try
        {
            nextRound();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void nextRound() throws IOException
    {
        LOGGER.fine("Changing round");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
    
        currentlyMovingCharacter = characters.get(0);
        // a játékos lép
        
        Random random = new Random();
        int numOfTurns = characters.size();
        for (int i = 0; i < numOfTurns; ++i)
        {
            LOGGER.fine("Next player");
            
            if (random.nextInt(2) == 0)
                snowStorm();
            
            currentlyMovingCharacter = characters.get(i);
            
            LOGGER.fine("You have " + (numOfTurns - i) + " turns left. Player" + (i + 1));
            
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
    
            if (checkDrowning(reader)) return;
    
            currentlyMovingCharacter.useAbility();
            currentlyMovingCharacter.clear();
            currentlyMovingCharacter.pickUp();
    
            if (currentlyMovingCharacter.getInventory().size() > 0)
            {
                System.out.println("Which item to use? You have " + currentlyMovingCharacter.getInventory().size() + " item. (0, 1, 2... stb; press x to not use any)");
                input = reader.readLine();
                int index = Integer.parseInt(input);
                currentlyMovingCharacter.useItem(index);
                
                if (isWin)
                    return;
            }
        }
    }
    
    private boolean checkDrowning(BufferedReader reader) throws IOException
    {
        String input;
        System.out.println("Should player drowning? (y/n) Player is " + (currentlyMovingCharacter.isDrowning() ? "" : "not ") + "drowning at the moment!");
        input = reader.readLine();
        if (input.equals("y"))
        {
            lose();
            return true;
        }
        return false;
    }
    
    public void win()
    {
        LOGGER.fine("You win!!!");
        isWin = true;
    }
    
    public void lose()
    {
        LOGGER.fine("End of the game");
        isLost = true;
    }
    
    public int getNumOfPlayers()
    {
        return characters.size();
    }
    
    private void snowStorm()
    {
        LOGGER.fine("Snow storm is now!");
    
        for (Character c : characters)
        {
            if (c.isInIgloo())
                c.changeHealth(-1);
        }
        
        ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
        for (int i = 0; i < blocks.size(); ++i)
        {
            for (int j = 0; j < blocks.get(0).size(); ++j)
            {
                blocks.get(i).get(j).changeAmountOfSnow(1);
            }
        }
        
        
    }
    
    private boolean isWin;
    private boolean isLost;
    
    private IceMap map;
    private ArrayList<Character> characters = new ArrayList<>();
    private Character currentlyMovingCharacter;
    
    
    // Singleton
    public static Game get()
    {
        if (game == null)
            game = new Game();
        
        return game;
    }
    
    private static Game game;
}
