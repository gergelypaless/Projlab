package com.csakcintanyer.bme.projlab;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Game
{

    private Game(){ }

    // a jégmező, a játékosok és a medve beállítása nem-determinisztikus módban
    public void init(IceMap iceMap, ArrayList<Character> characters, Bear bear)
    {
        init(iceMap, characters, bear, -1);
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
            Random random = new Random();

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
                    if (random.nextInt(2) == 1) // 50%
                    {
                        snowStorm();
                    }
                }

                if (gameOver()) break;
                nextRound(turns % characters.size()); // a következő játékos köre jön
                if (gameOver()) break;

                if(bear != null) moveBear(); // ha van medve lép
                turns++;
            }

            if (isLost){
                System.out.println("Game over!"); //vesztettünk
            }
            if (isWin){
                System.out.println("Victory!"); //nyertünk
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // következő kör
    public void nextRound(int whichPlayer) throws IOException
    {
        currentlyMovingCharacter = characters.get(whichPlayer); //meghatározzuk, hogy melyik játékos jön
        if (currentlyMovingCharacter.isDrowning()) // ha ez a játékos még mindig vízben van (már 1 teljes kör óta)
        {
            lose(); //vége a játéknak
            return;
        }

        currentlyMovingCharacter.setEnergy(4); // 4 energia a kör elején

        System.out.println("Player " + whichPlayer + "'s turn");

        String input;
        /**
         * parancsok feldolgozása
         * Addig nincs vége a játékos körének, amíg van energiája, nincs vége a játéknak vagy be nem fejezik a körét
         * az "end" parancsal
         */
        while (currentlyMovingCharacter.getEnergy() > 0 && !(isLost || isWin) && !(input = reader.readLine()).equals("end"))
        {
            if (input.equals(""))
                continue;

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

                        if (bear.getBlock() == currentlyMovingCharacter.getBlock()) // ha medve van a jégtálán
                        {
                            lose(); // vesztettünk
                            return; // end of turn
                        }

                        if (currentlyMovingCharacter.isDrowning())
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
                        if (currentlyMovingCharacter.useItem(Integer.parseInt(elements[2]))) // hanyadik tárgyat
                            System.out.println("OK, item used");
                        else
                            System.out.println("Item was not used");
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
        }
        System.out.println("Your turn is over");
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

    private boolean isWin; // nyertünk-e?
    private boolean isLost; // vesztettünk-e?
    private int turns; // az aktuális kör száma

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // a jégmező
    private IceMap map;

    // karakterek akik játszanak
    private ArrayList<Character> characters = new ArrayList<>();

    // éppen soron lévő játékos
    private Character currentlyMovingCharacter;

    // A medve
    private Bear bear = null;

    private boolean deterministic;
    public int snowInXTurns;

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