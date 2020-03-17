package project;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class IceMap
{
    private static final Logger LOGGER = Logger.getLogger( IceMap.class.getName() );
    
    public IceMap(int N, int M, int numOfPlayers)
    {
        LOGGER.finest("IceMap constructor");
        
        this.N = N;
        this.M = M;
        
        blocks = new ArrayList<>();
        for (int i = 0; i < N; ++i)
        {
            blocks.add(new ArrayList<>());
        }
        
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < M; ++j)
            {
                IceBlock block = null;
                
                Random random = new Random();
                if ((i * M + j) % 5 == 0)
                {
                    block = new UnstableBlock(random.nextInt(6), 1 + random.nextInt(4));
                }
                else if ((i * M + j) % 8 == 0)
                {
                    block = new EmptyBlock(random.nextInt(2), 0);
                }
                else
                {
                    block = new StableBlock(random.nextInt(6), numOfPlayers);
                }
                
                blocks.get(i).add(block);
            }
        }
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
