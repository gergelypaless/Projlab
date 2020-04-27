package com.csakcintanyer.bme.projlab;
import java.io.Serializable;
import java.util.ArrayList;

public class IceMap implements Serializable
{
    
    public IceMap(ArrayList<ArrayList<IceBlock>> blocks)
    {
    
        this.M = blocks.size();
        this.N = blocks.get(0).size();
        this.blocks = blocks;
        
        setNeighboursOnTheMap(); //beállítjuk minden jégtáblára, hogy melyek szomsédosak vele
    }

    // szomszédok beállítása
    public void setNeighboursOnTheMap()
    {
        
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
    
    // iceblock lekérdezése
    public ArrayList<ArrayList<IceBlock>> getBlocks()
    {
        return blocks;
    }

    // a jégmezp N*M méretű
    int N;
    int M;

    // blockok a jégmezőn
    private ArrayList<ArrayList<IceBlock>> blocks;
}
