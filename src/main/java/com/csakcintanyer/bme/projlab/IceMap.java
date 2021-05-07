package com.csakcintanyer.bme.projlab;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class IceMap implements Serializable, Drawable
{
    public IceMap(int N, int M)
    {
        this.N = N;
        this.M = M;
        gunCreated = false;
        blocks = new ArrayList<>();
        
        Random random = Game.get().random;
        for (int j = 0; j < M; ++j)
        {
            blocks.add(new ArrayList<>());
            for (int i = 0; i < N; ++i)
            {
                int which = random.nextInt(10);
                if (which % 5 == 0)
                {
                    blocks.get(j).add(new EmptyBlock(random.nextInt(3)));
                }
                else if (which % 3 == 0)
                {
                    IceBlock block = new StableBlock(random.nextInt(3));
                    blocks.get(j).add(block);
                    CollectableItem item = createItem(block);
                    block.setItem(item);
                }
                else
                {
                    IceBlock block = new UnstableBlock(random.nextInt(3),1 + random.nextInt(3));
                    blocks.get(j).add(block);
                    CollectableItem item = createItem(block);
                    block.setItem(item);
                }
            }
        }
        setNeighboursOnTheMap();
    
        IceBlock block;
        while ((block = blocks.get(random.nextInt(M)).get(random.nextInt(N))).getStability() == 0) ;
        block.setItem(new Gun());
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
    
    // icemap kirajzolása
    public void draw(int x, int y)
    {
        for (int j = 0; j < M; ++j)
        {
            for (int i = 0; i < N; ++i)
            {
                // ablak koordinátákká alakítás
                x = 20 + i * 50 + i * 5;
                y = 120 + j * 50 + j * 5;
                blocks.get(j).get(i).draw(x, y); // block kirajzolása
            }
        }
    }
    
    // creating and returning an item
    private CollectableItem createItem(IceBlock iceBlock)
    {
        CollectableItem item = null;
        switch (Game.get().random.nextInt(10))
        {
            case 0: item = new Bullet(); break;
            case 1: item = new Flare(); break;
            case 2: item = new FragileShovel(); break;
            case 4: item = new Rope(iceBlock); break;
            case 5: item = new Shovel(); break;
            case 6: item = new Suit(); break;
            case 7: item = new Tent(); break;
            case 8: item = new Food(); break;
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
