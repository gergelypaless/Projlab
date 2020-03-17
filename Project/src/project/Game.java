package project;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

public class Game
{
    private static final Logger LOGGER = Logger.getLogger( Game.class.getName() );
    
    private Game()
    {
        LOGGER.finest("Game constructor");
        NewGame(2, 2);
    }
    
    public void NewGame(int eskimoNum, int explorerNum)
    {
        LOGGER.fine("Initialization...");
    
        int idx = 0;
        for (int i = 0; i < eskimoNum; ++i)
            characters.add(new Eskimo(idx));
        for (int i = 0; i < explorerNum; ++i)
            characters.add(new Explorer(idx));
        
        map = new IceMap(15, 15, getNumOfPlayers());
        map.setNeighboursOnTheMap();
        
        currentlyMovingCharacter = characters.get(0);
    
        Random random = new Random();
        ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
        for (int j = 0; j < getNumOfPlayers(); ++j)
        {
            int x = random.nextInt(blocks.size());
            int y = random.nextInt(blocks.get(0).size());
            if (blocks.get(x).get(y).getStability() != 0)
                blocks.get(x).get(y).accept(characters.get(j));
        }
        LOGGER.fine("Initialization done");
    }
    
    public void nextRound()
    {
        LOGGER.fine("Changing round");
        
        Random random = new Random();
        if (random.nextInt(2) == 1)
        {
            snowStorm();
        }
        
        //                                    a karakterek ID-je megadja a characters tömbben a megfelelő karaktert
        currentlyMovingCharacter = characters.get((currentlyMovingCharacter.getID() + 1) % getNumOfPlayers());
        
        if (currentlyMovingCharacter.isDrowning())
        {
            lose();
            return;
        }
        
        // a játékos lép
        while (true)
        {
            
        }
    }
    
    public void win()
    {
    }
    
    public void lose()
    {
    }
    
    public int getNumOfPlayers()
    {
        return characters.size();
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
