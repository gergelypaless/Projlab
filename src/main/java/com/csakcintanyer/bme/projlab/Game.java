package com.csakcintanyer.bme.projlab;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Game
{

    private Game(){ }


    public void init()
    {
        // TODO: init game

        map = new IceMap();

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
            while (blocks.get(y).get(x) instanceof EmptyBlock)
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
        while (blocks.get(y).get(x) instanceof EmptyBlock)
        {
            x = random.nextInt(map.N);
            y = random.nextInt(map.M);
        }
        bear.setIceBlock(blocks.get(y).get(x));
        blocks.get(y).get(x).getEntities().add(bear);

        deterministic = true;
        snowInXTurns = 1000;

        View.get().init(map.N, map.M);
    }

    // a jégmező, a játékosok és a medve beállítása determinisztikus módban
    public void init(IceMap iceMap, ArrayList<Character> characters, Bear bear, int snowInXTurns)
    {
        map = iceMap;
        this.characters = characters;
        this.bear = bear;
        deterministic = snowInXTurns > 0;

        System.out.println("Snow in every " + snowInXTurns + " turns");
        this.snowInXTurns = snowInXTurns;
        turns = 0;



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

            if (isLost)
            {
                System.out.println("Game over!"); //vesztettünk
            }
            if (isWin)
            {
                System.out.println("Victory!"); //nyertünk
            }
        } catch (InterruptedException e)
        {
            System.out.println("Ezt a program futása végén kéne lássuk csak");
            //e.printStackTrace();
        }
    }

    // következő kör
    public void nextRound(int whichPlayer) throws InterruptedException
    {
        currentlyMovingCharacter = characters.get(whichPlayer); //meghatározzuk, hogy melyik játékos jön
        if (currentlyMovingCharacter.isDrowning()) // ha ez a játékos még mindig vízben van (már 1 teljes kör óta)
        {
            lose(); //vége a játéknak
            return;
        }

        currentlyMovingCharacter.setEnergy(4); // 4 energia a kör elején

        System.out.println("Player " + whichPlayer + "'s turn");
        
        endTurnEvent.waitOne();
        endTurnEvent.reset();
        
        System.out.println("Your turn is over");
    }
    
    public void characterMove(Direction dir){
        if (currentlyMovingCharacter.move(dir))
        {
            System.out.println("OK, character moved");
            if (bear != null) // ha van medve
            {
                if (bear.getBlock() == currentlyMovingCharacter.getBlock()) // ha medve van a jégtáblán
                {
                    lose(); // vesztettünk
                    return; // end of turn
                }
            }
            
            if (currentlyMovingCharacter.isDrowning()) // ha fuldoklunk akkor vége a körünknek
            {
                System.out.println("You are drowning, your turn is over!");
                endTurnEvent.set();
                return; // end of turn
            }
        }
        else
        {
            System.out.println("You cannot move " + dir);
        }
    }
    
    public void useItem(int index){
        if (!currentlyMovingCharacter.getInventory().isEmpty()) // ha van item az inventoryban
        {
            if (currentlyMovingCharacter.useItem(index)) // hanyadik tárgyat
                System.out.println("OK, item used");
            else
                System.out.println("Item was not used");
        }
    }
    
    public void UserAction(KeyEvent keyEvent)
    {
        Direction dir;
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.out.println("Exiting...");
                isLost = true;
                return; // end of turn
            case KeyEvent.VK_F1:
                String path;
                try
                {
                    path = IOLanguage.reader.readLine();
                } catch (IOException e) { break; }
                IOLanguage.SaveToFile(path);
                System.out.println("OK, game saved");
                break;
            case KeyEvent.VK_A:
                dir = Direction.LEFT;
                characterMove(dir);
                break;
            case KeyEvent.VK_S:
                dir = Direction.DOWN;
                characterMove(dir);
                break;
            case KeyEvent.VK_D:
                dir = Direction.RIGHT;
                characterMove(dir);
                break;
            case KeyEvent.VK_W:
                dir = Direction.UP;
                characterMove(dir);
                break;
            case KeyEvent.VK_0:
                useItem(0);
                break;
            case KeyEvent.VK_1:
                useItem(1);
                break;
            case KeyEvent.VK_2:
                useItem(2);
                break;
            case KeyEvent.VK_3:
                useItem(3);
                break;
            case KeyEvent.VK_4:
                useItem(4);
                break;
            case KeyEvent.VK_5:
                useItem(5);
                break;
            case KeyEvent.VK_E:
                if (currentlyMovingCharacter.useAbility())
                    System.out.println("OK, ability used");
                else
                    System.out.println("Ability was not used");
                break;
            case KeyEvent.VK_P:
                if (currentlyMovingCharacter.pickUp())
                    System.out.println("OK, item picked up");
                else
                    System.out.println("Item was not picked up");
                break;
            case KeyEvent.VK_F2:
                IOLanguage.PrintCharacter(currentlyMovingCharacter);
                break;
            case KeyEvent.VK_F3:
                IOLanguage.PrintBlock(currentlyMovingCharacter.getBlock());
                break;
            case KeyEvent.VK_C:
                if (currentlyMovingCharacter.clear())
                    System.out.println("OK, iceblock cleared");
                else
                    System.out.println("Iceblock was not cleared");
                break;
            case KeyEvent.VK_ENTER:
                endTurnEvent.set();
                break;
            default:
        }
        
        if (currentlyMovingCharacter.getEnergy() == 0)
        {
            endTurnEvent.set();
        }
    }

    private boolean hasInput()
    {
        System.out.print(">>>> ");
        try
        {
            input = IOLanguage.reader.readLine();
        } catch (IOException e)
        {
            return false;
        }
        return input.equals("end");
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

        System.out.println("Bear moved! Position: ");
        IOLanguage.PrintBlock(bear.getBlock());
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

        System.out.println("Oh no, SNOWSTORM!!");

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
                iceBlock.changeAmountOfSnow(1);
            }
        }
    }

    //visszaadja a jégmezőt, amelyen a játék folyik
    public IceMap getIceMap()
    {
        return map;
    }

    public IceBlock getBearLocation()
    {
        return bear.getBlock();
    }

    private boolean isWin; // nyertünk-e?
    private boolean isLost; // vesztettünk-e?
    private int turns; // az aktuális kör száma

    // a jégmező
    private IceMap map;

    // karakterek akik játszanak
    private ArrayList<Character> characters = new ArrayList<>();

    // éppen soron lévő játékos
    private Character currentlyMovingCharacter;

    // A medve
    private Bear bear = null;

    private boolean deterministic; // determinisztikus a programunk?
    public int snowInXTurns; // minden hány körben van hóvihar

    private String input;

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




/*
  /**
         * parancsok feldolgozása
         * Addig nincs vége a játékos körének, amíg van energiája, nincs vége a játéknak vagy be nem fejezik a körét
         * az "end" parancsal
         
        while (currentlyMovingCharacter.getEnergy() > 0 && !(isLost || isWin) && !hasInput())
                {
                if (input.equals(""))
                continue; // ignore
        
                View.get().repaint();
        
                String[] elements = input.split(" ");
                switch (elements[0])
                {
                case "exit":
                System.out.println("Exiting...");
                isLost = true;
                return; // end of turn
                case "save":
                IOLanguage.SaveToFile(elements[1]);
                System.out.println("OK, game saved");
                break;
                case "move":
                if (currentlyMovingCharacter.move(IOLanguage.GetDirection(elements[1])))
                {
                System.out.println("OK, character moved");
                if (bear != null) // ha van medve
                {
                if (bear.getBlock() == currentlyMovingCharacter.getBlock()) // ha medve van a jégtáblán
                {
                lose(); // vesztettünk
                return; // end of turn
                }
                }
        
                if (currentlyMovingCharacter.isDrowning()) // ha fuldoklunk akkor vége a körünknek
                {
                System.out.println("You are drowning, your turn is over!");
                return; // end of turn
                }
                }
                else
                {
                System.out.println("You cannot move " + elements[1]);
                }
                break;
                case "use":
                if (elements[1].equals("item"))
                {
                if (!currentlyMovingCharacter.getInventory().isEmpty()) // ha van item az inventoryban
                {
                if (currentlyMovingCharacter.useItem(Integer.parseInt(elements[2]))) // hanyadik tárgyat
                System.out.println("OK, item used");
                else
                System.out.println("Item was not used");
                }
                else
                {
                System.out.println("Your inventory is empty");
                }
                }
                else if (elements[1].equals("ability"))
                {
                if (currentlyMovingCharacter.useAbility())
                System.out.println("OK, ability used");
                else
                System.out.println("Ability was not used");
                }
                break;
                case "pick_up":
                if (currentlyMovingCharacter.pickUp())
                System.out.println("OK, item picked up");
                else
                System.out.println("Item was not picked up");
                break;
                case "stat":
                IOLanguage.PrintCharacter(currentlyMovingCharacter);
                break;
                case "block":
                IOLanguage.PrintBlock(currentlyMovingCharacter.getBlock());
                break;
        
                case "clear":
                if (currentlyMovingCharacter.clear())
                System.out.println("OK, iceblock cleared");
                else
                System.out.println("Iceblock was not cleared");
                break;
default:
        throw new IllegalArgumentException("wrong command");
        }
        View.get().repaint();
        }
 */
