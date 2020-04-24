package com.csakcintanyer.bme.projlab;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class IceMap implements Serializable
{
    // Logger osztálypéldány: ennek a segítségével formázzuk a kimenetet
    private static final Logger LOGGER = Logger.getLogger( IceMap.class.getName() );
    
    public IceMap(ArrayList<ArrayList<IceBlock>> blocks)
    {
        LOGGER.finest("IceMap constructor");
    
        this.M = blocks.size();
        this.N = blocks.get(0).size();
        this.blocks = blocks;
        
        setNeighboursOnTheMap();
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
                
                if (y != 0)
                    blocks.get(y).get(x).setNeighbour(Direction.UP, blocks.get(y - 1).get(x));
                
                if (y != M - 1)
                    blocks.get(y).get(x).setNeighbour(Direction.DOWN, blocks.get(y + 1).get(x));
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
