package com.csakcintanyer.bme.projlab;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Game
{

    private Game(){ }

    public void init(IceMap iceMap, ArrayList<Character> characters, Bear bear)
    {
        init(iceMap, characters, bear, -1);
    }
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

            nextRound(turns % characters.size());
            if(bear != null) moveBear();
            turns++;
            while (!gameOver())
            {
                if (deterministic && turns % snowInXTurns == 0)
                {
                    snowStorm();
                }
                else if (!deterministic)
                {
                    if (random.nextInt(2) == 1) // 50%
                    {
                        snowStorm();
                    }
                }

                if (gameOver()) break;
                nextRound(turns % characters.size());
                if (gameOver()) break;

                if(bear != null) moveBear();
                turns++;
            }

            System.out.println("Game over!");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // következő kör
    // returns true if the round was successful
    public void nextRound(int whichPlayer) throws IOException
    {

        currentlyMovingCharacter = characters.get(whichPlayer);
        if (currentlyMovingCharacter.isDrowning())
        {
            lose();
            return;
        }

        currentlyMovingCharacter.setEnergy(4);

        System.out.println("Player " + whichPlayer + "'s turn");

        String input;
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
                    return;
                case "save":
                    IOLanguage.SaveToFile(elements[1]);
                    System.out.println("OK, game saved");
                    break;
                case "move":
                    if (currentlyMovingCharacter.move(IOLanguage.GetDirection(elements[1])))
                    {
                        System.out.println("OK, character moved");

                        if (bear.getBlock() == currentlyMovingCharacter.getBlock())
                        {
                            lose();
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
                        if (currentlyMovingCharacter.useItem(Integer.parseInt(elements[2])))
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

    private void moveBear()
    {
        Random rand = new Random();
        boolean moved = false;
        int randNum;
        do {
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

    private boolean gameOver()
    {
        return (isLost || isWin);
    }

    // Hóvihar van
    private void snowStorm()
    {

        System.out.println("Oh no, SNOWSTORM!!");

        // végigmegyünk a karaktereken, megnézük hogy iglooban vannak-e, ha nem akkor egy élet minusz
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

    public IceMap getIceMap()
    {
        return map;
    }
    
    public int getNumOfCharacters()
    {
        return characters.size();
    }

    private boolean isWin; // nyertünk-e?
    private boolean isLost; // vesztettünk-e?
    private int turns;

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // a jégmező
    private IceMap map;

    // karakterek akik játszanak
    private ArrayList<Character> characters = new ArrayList<>();

    // éppen soron lévő játékos
    private Character currentlyMovingCharacter;

    // bear
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