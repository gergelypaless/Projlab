package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class IceMap
{
    private static final Logger LOGGER = Logger.getLogger( IceMap.class.getName() );
    
    public IceMap(int N, int M, int numOfPlayers, ArrayList<Character> characters, ArrayList<CollectableItem> items)
    {
        LOGGER.finest("IceMap constructor");
        
        // create blocks
        //      place characters
        //      place Items
        
        createBlocks();
        placeCharacters(characters);
        placeItems(items);
        
        /*this.N = N;
        this.M = M;
        
        blocks = new ArrayList<>();
        for (int i = 0; i < N; ++i)
        {
            blocks.add(new ArrayList<>());
        }
        
        ArrayList<CollectableItem> itemsToAdd = new ArrayList<>();
        itemsToAdd.add(new Suit());
        itemsToAdd.add(new Rope());
        itemsToAdd.add(new Shovel());
        itemsToAdd.add(new Gun());
        itemsToAdd.add(new Bullet());
        itemsToAdd.add(new Flare());
        itemsToAdd.add(new Food());
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < M; ++j)
            {
                IceBlock block = null;
                
                Random random = new Random();
                if ((i * M + j) % 5 == 0)
                {
                    int itemIdx = random.nextInt(itemsToAdd.size());
                    block = new UnstableBlock(random.nextInt(6), 1 + random.nextInt(4), itemsToAdd.get(itemIdx));
                    itemsToAdd.remove(itemIdx);
                }
                else if ((i * M + j) % 8 == 0)
                {
                    block = new EmptyBlock(random.nextInt(2), 0);
                }
                else
                {
                    int itemIdx = random.nextInt(itemsToAdd.size());
                    block = new StableBlock(random.nextInt(6), numOfPlayers, itemsToAdd.get(itemIdx));
                    itemsToAdd.remove(itemIdx);
                }
                
                blocks.get(i).add(block);
            }
        }*/
    }
    
    private void placeItems(ArrayList<CollectableItem> items)
    {
        LOGGER.fine("Placing items");
    }
    
    private void createBlocks()
    {
        LOGGER.fine("Creating blocks");
        blocks = new ArrayList<>();
        ArrayList<IceBlock> aBlock = new ArrayList<>();
        aBlock.add(new StableBlock(0, 0));
        blocks.add(aBlock);
    }
    
    private void placeCharacters(ArrayList<Character> characters)
    {
        LOGGER.fine("Placing characters");
        blocks.get(0).get(0).accept(characters.get(0));
        characters.get(0).setIceBlock(blocks.get(0).get(0));
    }
    
    public void setNeighboursOnTheMap()
    {
        LOGGER.fine("Setting neighbours...");
        
        for (int y = 0; y < M; ++y)
        {
            for (int x = 0; x < N; ++x)
            {
                if (x != 0)
                    blocks.get(y).get(x).setNeighbour(Direction.LEFT, blocks.get(y).get(x - 1));
                
                if (x != N - 1)
                    blocks.get(y).get(x).setNeighbour(Direction.RIGHT, blocks.get(y).get(x + 1));
                
                if (y != N - 1)
                    blocks.get(y).get(x).setNeighbour(Direction.UP, blocks.get(y + 1).get(x));
                
                if (y != 0)
                    blocks.get(y).get(x).setNeighbour(Direction.DOWN, blocks.get(y - 1).get(x));
            }
        }
    }
    
    public ArrayList<ArrayList<IceBlock>> getBlocks()
    {
        LOGGER.finest("Blocks getter");
        return blocks;
    }
    
    int N;
    int M;
    
    private ArrayList<ArrayList<IceBlock>> blocks;
}
