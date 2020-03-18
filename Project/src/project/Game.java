package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    
    public void newGame()
    {
        LOGGER.fine("New game");
    
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input;
    
            chooseCharacter(reader);
    
            
            
            /*Random random = new Random();
            ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
            int x = random.nextInt(blocks.size());
            int y = random.nextInt(blocks.get(0).size());
            while (blocks.get(x).get(y).getStability() == 0)
            {
                y = random.nextInt(blocks.size());
                x = random.nextInt(blocks.get(0).size());
            }
            blocks.get(y).get(x).accept(currentlyMovingCharacter);
            currentlyMovingCharacter.setIceBlock(blocks.get(y).get(x));*/
    
            //LOGGER.finer("Our player is on (" + x + ", " + y + ")");
            
            nextRound();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void chooseCharacter(BufferedReader reader) throws IOException
    {
        LOGGER.fine("Choosing character");
        
        String input;
        System.out.println("Which character? (eskimo/explorer)");
        input = reader.readLine();
        if (input.equals("eskimo"))
        {
            currentlyMovingCharacter = new Eskimo();
        }
        else if (input.equals("explorer"))
        {
            currentlyMovingCharacter = new Explorer();
        }
    }
    
    public void nextRound() throws IOException
    {
        LOGGER.fine("Changing round");
    
        snowStorm();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
    
        currentlyMovingCharacter = characters.get(0);
        // a játékos lép
        while (true)
        {
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
            currentlyMovingCharacter.pickUp();
    
            System.out.println("Which item to use? " + currentlyMovingCharacter.getInventory().size() + ". (0, 1, 2... stb)");
            input = reader.readLine();
            int index = Integer.parseInt(input);
            currentlyMovingCharacter.useItem(index);
            
            currentlyMovingCharacter.clear();
        }
    }
    
    private boolean checkDrowning(BufferedReader reader) throws IOException
    {
        String input;
        System.out.println("Player drowning? (y/n)");
        input = reader.readLine();
        if (input.equals("y"))
        {
            lose();
            return true;
        }
        return false;
    }
    
    public void moveCurrentCharacter(Direction d)
    {
        LOGGER.fine("Moving current character");
        currentlyMovingCharacter.move(d);
    }
    
    public void win()
    {
    }
    
    public void lose()
    {
        LOGGER.fine("End of the game");
    }
    
    public int getNumOfPlayers()
    {
        return 1;
    }
    
    private void snowStorm()
    {
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
