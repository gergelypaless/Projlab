package com.csakcintanyer.bme.projlab;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game
{
    private Game(){ }

    public void init(int N, int M) //Megadott méretek alapján készítjük el a játékot
    {
        isLost = false;
        isWin = false;
        turns = 0;
        iceMapSelected = true;
        selectedItemInInventory = 0;
        
        map = new IceMap(N, M);

        characters = new ArrayList<>();
        characters.add(new Eskimo(characters.size()));
        characters.add(new Eskimo(characters.size()));
        characters.add(new Explorer(characters.size()));
        characters.add(new Explorer(characters.size()));

        ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
        for (Character character : characters)
        {
            int x = random.nextInt(map.N);
            int y = random.nextInt(map.M);
            while (blocks.get(y).get(x).blockIsFull())
            {
                x = random.nextInt(map.N);
                y = random.nextInt(map.M);
            }
            character.setIceBlock(blocks.get(y).get(x));
            blocks.get(y).get(x).getEntities().add(character);
        }

        bear = new Bear();
        int x = random.nextInt(map.N);
        int y = random.nextInt(map.M);
        while (blocks.get(y).get(x).blockIsFull() || blocks.get(y).get(x).getEntities().size() > 0)
        {
            x = random.nextInt(map.N);
            y = random.nextInt(map.M);
        }
        bear.setIceBlock(blocks.get(y).get(x));
        blocks.get(y).get(x).getEntities().add(bear);

        deterministic = true;
        snowInXTurns = 100;

        View.get().init(map.N, map.M);
        View.get().repaint();
    }

    // a jégmező, a játékosok és a medve beállítása determinisztikus módban
    public void init(IceMap iceMap, ArrayList<Character> characters, Bear bear)
    {
        isLost = false;
        isWin = false;
        turns = 0;
        iceMapSelected = true;
        selectedItemInInventory = 0;
        
        map = iceMap;
        this.characters = characters;
        this.bear = bear;
        
        deterministic = true;
        snowInXTurns = 10;
    
        currentlyMovingCharacter = characters.get(0);
        resetSelectedIceBlock();
    
        View.get().init(map.N, map.M);
        View.get().repaint();
    }

    // játék kezdése
    public void start()
    {
        try
        {
            nextRound(0); //az első játékos játszik 1 kört
            if(bear != null) moveBear(); // medve lép, ha van
            turns++;
            while (!gameOver()) //amíg nincs vége a játéknak
            {
                /*
                 * Ha determinisztkus módban vagyunk, akkor a beállítot érték szerint annyi körönként hóvihar
                 * */
                if (deterministic && turns % snowInXTurns == 0)
                {
                    snowStorm();
                }
                else if (!deterministic) //nem-determinisztikus módban
                {
                    if (random.nextInt(10) <= 1) // 20%
                    {
                        snowStorm();
                    }
                }

                if (gameOver()) break;
                View.get().repaint();
                nextRound(turns % characters.size()); // a következő játékos köre jön
                View.get().repaint();
                if (gameOver()) break;

                if(bear != null) moveBear(); // ha van medve lép
                turns++;
            }

            View.get().repaint();
        } catch (InterruptedException e)
        {
        }
    }

    // következő kör
    private void nextRound(int whichPlayer) throws InterruptedException
    {
        // TODO: Ha a következőnek érkező karakter is beborul unstable blocknál, instant game-over van. Ezt meg kéne szüntetni.
        currentlyMovingCharacter = characters.get(whichPlayer); //meghatározzuk, hogy melyik játékos jön
        if (currentlyMovingCharacter.isDrowning()) // ha ez a játékos még mindig vízben van (már 1 teljes kör óta)
        {
            lose(); //vége a játéknak
            return;
        }

        currentlyMovingCharacter.setEnergy(4); // 4 energia a kör elején
        resetSelectedIceBlock();
        
        endTurnEvent.waitOne();
        endTurnEvent.reset();
    }

    //Karakter lép
    private void characterMove(Direction dir)
    {
        if (currentlyMovingCharacter.move(dir))
        {
            
            if (bear != null) // ha van medve
            {
                if (bear.getBlock() == currentlyMovingCharacter.getBlock()) // ha medve van a jégtáblán
                {
                    lose(); // vesztettünk
                    endTurn();
                    return; // end of turn
                }
            }
            
            if (currentlyMovingCharacter.isDrowning()) // ha fuldoklunk akkor vége a körünknek
            {
                endTurnEvent.set();
            }
        }
    }

    //Item használata
    private void useItem()
    {
        if (!currentlyMovingCharacter.getInventory().isEmpty()) // ha van item az inventoryban
        {
            if (selectedItemInInventory >= currentlyMovingCharacter.getInventory().size())
                return;
            
            currentlyMovingCharacter.useItem(selectedItemInInventory);
        }
    }

    //Megváltoztatjuk az éppen kijelölt jégmezőt
    private void ChangeSelectedIceBlock(Direction dir)
    {
        HashMap<Direction, IceBlock> neighbours = selectedIceBlock.getNeighbours();
        IceBlock iceBlock = neighbours.get(dir);
        if (iceBlock != null)
            selectedIceBlock = iceBlock;
    }

    //Megváltoztatjuk az éppen kijelölt item-et
    private void ChangeSelectedItemInInventory(Direction dir)
    {
        if (dir == Direction.DOWN)
        {
            selectedItemInInventory += 3;
        }
        else if (dir == Direction.LEFT)
        {
            selectedItemInInventory -= 1;
        }
        else if (dir == Direction.RIGHT)
        {
            selectedItemInInventory += 1;
        }
        else if (dir == Direction.UP)
        {
            selectedItemInInventory -= 3;
        }

        //6 helyünk van összesen, ezért csak ezek között lépegethetünk
        if (selectedItemInInventory < 0)
            selectedItemInInventory = 0;
        else if (selectedItemInInventory > 5)
            selectedItemInInventory = 5;
        
    }

    //A bejövő gombokat kezelő függvény
    public void UserAction(KeyEvent keyEvent)
    {
        if (gameOver())
            return;
        
        Direction dir;
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_A: //Balra lépünk
                dir = Direction.LEFT;
                characterMove(dir);
                break;
            case KeyEvent.VK_S: //Lefelé lépünk
                dir = Direction.DOWN;
                characterMove(dir);
                break;
            case KeyEvent.VK_D: //Jobbra lépünk
                dir = Direction.RIGHT;
                characterMove(dir);
                break;
            case KeyEvent.VK_W: //Felfelé lépünk
                dir = Direction.UP;
                characterMove(dir);
                break;
            case KeyEvent.VK_ENTER: //Enterrel használjuk a kijelölt Item-et
                if (!iceMapSelected)
                    useItem();
                break;
            case KeyEvent.VK_E: currentlyMovingCharacter.useAbility(); break; //Képesség használata
            case KeyEvent.VK_P: currentlyMovingCharacter.pickUp(); break; //Item felvétele
            case KeyEvent.VK_C: currentlyMovingCharacter.clear(); break; //1 hó eltakarítása kézzel
            case KeyEvent.VK_Q: //Kör befejezése
                endTurnEvent.set();
                break;
            case KeyEvent.VK_UP: //kijelölő feljebb léptetése
                if (iceMapSelected) ChangeSelectedIceBlock(Direction.UP);
                else ChangeSelectedItemInInventory(Direction.UP);
                break;
            case KeyEvent.VK_DOWN: //kijelölő lejjebb léptetése
                if (iceMapSelected) ChangeSelectedIceBlock(Direction.DOWN);
                else ChangeSelectedItemInInventory(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT: //kijelölő balra léptetése
                if (iceMapSelected) ChangeSelectedIceBlock(Direction.LEFT);
                else ChangeSelectedItemInInventory(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT: //kijelölő jobbra léptetése
                if (iceMapSelected) ChangeSelectedIceBlock(Direction.RIGHT);
                else ChangeSelectedItemInInventory(Direction.RIGHT);
                break;
            case KeyEvent.VK_CONTROL: //kijelölő átváltása (Invertory és pálya között)
                iceMapSelected = !iceMapSelected;
                break;
            case KeyEvent.VK_I: //Irányításokat bemutató ablak megnyitása
                Windows.get().controlsWindow.setVisible(true);
                break;
            case KeyEvent.VK_B: //Játék mentése
                IOLanguage.SaveToFile("save.txt");
            default:
        }
        
        if (currentlyMovingCharacter.getEnergy() == 0)
        {
            endTurnEvent.set();
        }
    }

    // a medvét mozgatása
    private void moveBear()
    {
        Random rand = new Random();
        boolean moved = false;
        int randNum;
        do { // addig mozgatjuk, amíg nem talál egy random irány amerre van jégtábla
            randNum = rand.nextInt(4);
            if (randNum == 0)
                moved = bear.move(Direction.LEFT);
            if (randNum == 1)
                moved = bear.move(Direction.RIGHT);
            if (randNum == 2)
                moved = bear.move(Direction.UP);
            if (randNum == 3)
                moved = bear.move(Direction.DOWN);
        } while (!moved);
    }

    // ezt a függvényt kell meghívni, ha a győzelem feltétele teljesült
    public void win()
    {
        isWin = true;
    }

    // ezt a függvényt kell meghívni, ha a vereség feltétele teljesült
    public void lose()
    {
        isLost = true;
    }

    // megadja, hogy a játékmenetnek vége van-e
    private boolean gameOver()
    {
        return (isLost || isWin);
    }

    // visszaadja a játékosok számát
    public int getNumOfCharacters()
    {
        return characters.size();
    }

    // Hóvihar
    private void snowStorm()
    {
        // végigmegyünk a karaktereken, megnézük hogy igluban vannak-e, ha nem akkor egy élet minusz
        for (Character c : characters)
        {
            if (!(c.isInIgloo() || c.isInTent()))
                c.changeHealth(-1);
        }

        // az IceBlockok hórétegének nővelése
        ArrayList<ArrayList<IceBlock>> blocks = map.getBlocks();
        for (ArrayList<IceBlock> block : blocks)
        {
            for (IceBlock iceBlock : block)
            {
                if (random.nextInt(2) == 0)
                    iceBlock.changeAmountOfSnow(1 + random.nextInt(2));
                
                iceBlock.removeTent();
            }
        }
    }

    //visszaadja a jégmezőt, amelyen a játék folyik
    public IceMap getIceMap()
    {
        return map;
    }

    //Éppen lépő karakter
    public Character getCurrentlyMovingCharacter()
    {
        return currentlyMovingCharacter;
    }

    //Éppen kijelölt jégtábla
    public IceBlock getSelectedIceBlock()
    {
        return selectedIceBlock;
    }

    //Éppen kijelölt jégtábla az lesz, amin az éppen lépő karakter áll
    public void resetSelectedIceBlock()
    {
        selectedIceBlock = currentlyMovingCharacter.getBlock();
        iceMapSelected = true;
    }

    //Éppen kijelölt Item
    public int getSelectedItem()
    {
        return selectedItemInInventory;
    }

    //Kör vége
    public void endTurn()
    {
        endTurnEvent.set();
    }

    //Vesztettünk-e
    public boolean isLost() { return isLost; }

    //Nyertünk-e
    public boolean isWin() { return isWin; }
    
    private boolean isWin; // nyertünk-e?
    private boolean isLost; // vesztettünk-e?
    private int turns; // az aktuális kör száma

    // a jégmező
    private IceMap map;

    // karakterek akik játszanak
    private ArrayList<Character> characters = new ArrayList<>();

    // éppen soron lévő játékos
    private Character currentlyMovingCharacter;
    private IceBlock selectedIceBlock;
    public boolean iceMapSelected = true;
    private int selectedItemInInventory = 0;

    // A medve
    private Bear bear = null;

    private boolean deterministic; // determinisztikus a programunk?
    public int snowInXTurns; // minden hány körben van hóvihar
    
    public Random random = new Random();
    private AutoResetEvent endTurnEvent = new AutoResetEvent(false);

    // a Game osztály a Singleton tervezési formát követi, hisz a program futása során csak egy, a játékot vezérlő
    // osztálypéldány létezhet
    public static Game get()
    {
        if (instance == null)
            instance = new Game();

        return instance;
    }
    private static Game instance;
}
