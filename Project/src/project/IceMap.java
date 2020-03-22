package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class IceMap
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( IceMap.class.getName() );
    
    public IceMap(int N, int M, int numOfPlayers, ArrayList<Character> characters, ArrayList<CollectableItem> items)
    {
        LOGGER.finest("IceMap constructor");

        // IceMap init
        createBlocks();
        placeCharacters(characters);
        placeItems(items);
    }

    // ez a függvény rakja le az IceBlockokra az Itemeket
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
        aBlock.get(0).setNeighbour(Direction.UP, aBlock.get(0));
        aBlock.get(0).setNeighbour(Direction.DOWN, aBlock.get(0));
        aBlock.get(0).setNeighbour(Direction.LEFT, aBlock.get(0));
        aBlock.get(0).setNeighbour(Direction.RIGHT, aBlock.get(0));
        blocks.add(aBlock);
    }
    
    private void placeCharacters(ArrayList<Character> characters)
    {
        LOGGER.fine("Placing characters");

        // mindegyik játékost ugyan arra a blockra rakunk, az egyszerűség kedvéért
        for (Character character : characters)
        {
            blocks.get(0).get(0).accept(character);
            character.setIceBlock(blocks.get(0).get(0));
        }
    }

    // szomszédok beállítása
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

    // a jégmezp N*M méretű
    int N;
    int M;

    // blockok a jégmezőn
    private ArrayList<ArrayList<IceBlock>> blocks;
}
