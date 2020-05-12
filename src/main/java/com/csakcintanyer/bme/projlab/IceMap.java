package com.csakcintanyer.bme.projlab;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class IceMap implements Serializable, Drawable
{
    
    public IceMap()
    {
        N = 10;
        M = 7;
        gunCreated = false;
        blocks = new ArrayList<>();
        
        for (int j = 0; j < M; ++j)
        {
            blocks.add(new ArrayList<>());
            for (int i = 0; i < N; ++i)
            {
                int which = Game.get().random.nextInt(10);
                if (which % 5 == 0)
                {
                    blocks.get(j).add(new EmptyBlock(Game.get().random.nextInt(3)));
                }
                else if (which % 3 == 0)
                {
                    IceBlock block = new UnstableBlock(Game.get().random.nextInt(3),1 + Game.get().random.nextInt(3));
                    blocks.get(j).add(block);
                    CollectableItem item = createItem(block);
                    block.setItem(item);
                }
                else
                {
                    IceBlock block = new StableBlock(Game.get().random.nextInt(3));
                    blocks.get(j).add(block);
                    CollectableItem item = createItem(block);
                    block.setItem(item);
                }
            }
        }
        setNeighboursOnTheMap();
    }
    
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
    
    public void draw(int x, int y)
    {
        for (int j = 0; j < M; ++j)
        {
            for (int i = 0; i < N; ++i)
            {
                x = 20 + i * 50 + i * 5;
                y = 120 + j * 50 + j * 5;
                blocks.get(j).get(i).draw(x, y);
            }
        }
    }
    
    private CollectableItem createItem(IceBlock iceBlock)
    {
        CollectableItem item = null;
        switch (Game.get().random.nextInt(10))
        {
            case 0: item = new Bullet(iceBlock); break;
            case 1: item = new Flare(iceBlock); break;
            case 2: item = new FragileShovel(iceBlock); break;
            case 3: if (!gunCreated){ gunCreated = true; item = new Gun(iceBlock); break; }
            case 4: item = new Rope(iceBlock); break;
            case 5: item = new Shovel(iceBlock); break;
            case 6: item = new Suit(iceBlock); break;
            case 7: item = new Tent(iceBlock); break;
            case 8: //food
            default:
        }
        return item;
    }
    
    // iceblock lekérdezése
    public ArrayList<ArrayList<IceBlock>> getBlocks()
    {
        return blocks;
    }
    
    // a jégmezo N*M méretű
    int N; // szélesség
    int M; // magasság
    boolean gunCreated;
    
    // blockok a jégmezőn
    private ArrayList<ArrayList<IceBlock>> blocks;
}
