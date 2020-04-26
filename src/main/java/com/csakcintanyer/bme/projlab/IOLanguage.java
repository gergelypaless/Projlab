package com.csakcintanyer.bme.projlab;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class IOLanguage
{
	private static int N;
	private static int M;
	private static ArrayList<Character> characters = new ArrayList<>();
	private static Bear bear = null;
	private static ArrayList<ArrayList<IceBlock>> blocks = new ArrayList<>();
	private static IceMap iceMap = null;
	private static int snowInXTurns = -1;
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Random rand = new Random();
	
	public static String directionToString(Direction d)
	{
		if (d == Direction.DOWN)
			return "Direction.DOWN";
		if (d == Direction.UP)
			return "Direction.DOWN";
		if (d == Direction.RIGHT)
			return "Direction.RIGHT";
		if (d == Direction.LEFT)
			return "Direction.LEFT";
		
		return "-----------";
	}
	
	public static boolean ReadFromConsole() throws IOException
	{
		System.out.print(">>>> ");
		String input;
		while (!(input = reader.readLine()).equals("done"))
		{
			if (input.equals(""))
				continue;
			
			String[] elements = input.split(" ");
			switch (elements[0])
			{
				case "map": 					CreateMap(elements); break;
				case "exit":
					System.out.println("Exiting...");
					return false;
				case "load":
					LoadFile(elements[1]);
					System.out.println("File loaded");
					break;
				case "save":
					SaveToFile(elements[1]);
					System.out.println("OK, game saved");
					break;
				case "character": 				CreateCharacter(elements); break;
				case "item": 					CreateItem(elements); break;
				case "deterministic":
					snowInXTurns = Integer.parseInt(elements[1]);
					if (snowInXTurns >= 0)
						System.out.println("OK, program is deterministic");
					else
						System.out.println("You set a negative number, program is not deterministic");
					break;
				case "bear": 					CreateBear(elements); break;
				default:
					throw new IllegalArgumentException("wrong command");
			}
		}
		System.out.println("OK, initialization ended");
		Game.get().init(iceMap, characters, bear, snowInXTurns);
		return true;
	}
	
	public static void CreateMap(String[] elements) throws IOException
	{
		N = Integer.parseInt(elements[1]);
		M = Integer.parseInt(elements[2]);
		for (int j = 0; j < M; ++j)
		{
			blocks.add(new ArrayList<>());
			String input = reader.readLine();
			String[] inputElements = input.split(" ");
			for (int i = 0; i < N; ++i)
			{
				String strBlock = inputElements[i];
				int amountOfSnow = Integer.parseInt(String.valueOf(strBlock.charAt(1)));
				int stability = Integer.parseInt(String.valueOf(strBlock.charAt(2)));
				if (strBlock.charAt(0) == 's')
					blocks.get(j).add(new StableBlock(amountOfSnow, stability));
				if (strBlock.charAt(0) == 'u')
					blocks.get(j).add(new UnstableBlock(amountOfSnow, stability));
				if (strBlock.charAt(0) == 'e')
					blocks.get(j).add(new EmptyBlock(amountOfSnow, stability));
			}
		}
		iceMap = new IceMap(blocks);
		
		System.out.println("OK, map created");
	}
	
	public static void CreateBear(String[] elements)
	{
		IceBlock block;
		if (elements.length == 1)
		{
			do
			{
				int x = rand.nextInt(iceMap.getBlocks().get(0).size());
				int y = rand.nextInt(iceMap.getBlocks().size());
				block = iceMap.getBlocks().get(y).get(x);
			} while (block.getEntities().size() > 0);
		}
		else
		{
			int x = Integer.parseInt(elements[1]);
			int y = Integer.parseInt(elements[2]);
			block = iceMap.getBlocks().get(y).get(x);
		}
		bear = new Bear();
		bear.setIceBlock(block);
		block.getEntities().add(bear);
		
		System.out.println("OK, bear created");
	}
	
	public static void CreateItem(String[] elements)
	{
		IceBlock block;
		if (elements.length == 2) // nincs pozíció specifikálva
		{
			do
			{
				int x = rand.nextInt(iceMap.getBlocks().get(0).size());
				int y = rand.nextInt(iceMap.getBlocks().size());
				block = iceMap.getBlocks().get(y).get(x);
			} while (block instanceof EmptyBlock || block.getItem() != null);
		}
		else
		{
			int x = Integer.parseInt(elements[2]);
			int y = Integer.parseInt(elements[3]);
			block = iceMap.getBlocks().get(y).get(x);
		}
		
		if (block.getItem() != null)
			throw new IllegalArgumentException("this block already has an item on it");
		
		if (elements[1].equals("bullet"))
			block.setItem(new Bullet(block));
		if (elements[1].equals("flare"))
			block.setItem(new Flare(block));
		if (elements[1].equals("food"))
			block.setItem(new Food(block));
		if (elements[1].equals("fragileshovel"))
			block.setItem(new FragileShovel(block));
		if (elements[1].equals("gun"))
			block.setItem(new Gun(block));
		if (elements[1].equals("rope"))
			block.setItem(new Rope(block));
		if (elements[1].equals("shovel"))
			block.setItem(new Shovel(block));
		if (elements[1].equals("suit"))
			block.setItem(new Suit(block));
		if (elements[1].equals("tent"))
			block.setItem(new Tent(block));
		
		System.out.println("OK, item created");
	}
	
	public static void CreateCharacter(String[] elements)
	{
		Character character = null;
		if (elements.length == 1) // nem volt karaktertípus specifikálva
		{
			if (rand.nextInt(2) == 1)
				character = new Eskimo(characters.size());
			else
				character = new Explorer(characters.size());
			
			characters.add(character);
		}
		else
		{
			if (elements[1].equals("eskimo"))
			{
				character = new Eskimo(characters.size());
				characters.add(character);
			}
			else if (elements[1].equals("explorer"))
			{
				character = new Explorer(characters.size());
				characters.add(character);
			}
			else throw new IllegalArgumentException("wrong character type");
		}
		
		if (iceMap == null)
			throw new IllegalArgumentException("icemap was not initialized");
		
		IceBlock block;
		if (elements.length <= 2) // nincs pozició specifikálva, csak típus volt
		{
			
			do
			{
				int x = rand.nextInt(iceMap.getBlocks().get(0).size());
				int y = rand.nextInt(iceMap.getBlocks().size());
				block = iceMap.getBlocks().get(y).get(x);
			} while (block instanceof EmptyBlock || block.getStability() == block.getEntities().size() || isBearOnIceBlock(block));
		}
		else
		{
			int x = Integer.parseInt(elements[2]);
			int y = Integer.parseInt(elements[3]);
			block = iceMap.getBlocks().get(y).get(x);
			//characters.get(characters.size() - 1).setIceBlock(iceMap.getBlocks().get(Integer.parseInt(elements[3])).get(Integer.parseInt(elements[2])));
		}
		character.setIceBlock(block);
		block.getEntities().add(character);
		System.out.println("OK, character created");
	}
	
	private static boolean isBearOnIceBlock(IceBlock block)
	{
		if (bear != null)
			return bear.getBlock() == block;
		return false;
	}
	
	public static Direction GetDirection() throws IOException
	{
		System.out.print("Direction: ");
		return GetDirection(reader.readLine());
	}
	
	public static Direction GetDirection(String input) throws IOException
	{
		// megkérdezzük, hogy melyik irányba kell használni a képességét
		
		// lekérjük a megfelelő IceBlock stability értékét
		if (input.equals("left"))
			return Direction.LEFT;
		if (input.equals("right"))
			return Direction.RIGHT;
		if (input.equals("up"))
			return Direction.UP;
		if (input.equals("down"))
			return Direction.DOWN;
		
		throw new IllegalArgumentException("wrong direction");
	}
	
	public static void PrintCharacter(Character currentlyMovingCharacter)
	{
		System.out.println("Type: " + currentlyMovingCharacter.toString());
		
		System.out.println("Energy: " + currentlyMovingCharacter.getEnergy());
		System.out.println("Health: " + currentlyMovingCharacter.getHealth());
		System.out.println("HasSuit: " + (currentlyMovingCharacter.hasSuit() ? "True" : "False"));
		System.out.println("HasFlare: " + (currentlyMovingCharacter.hasFlare() ? "True" : "False"));
		System.out.println("HasBullet: " + (currentlyMovingCharacter.hasBullet() ? "True" : "False"));
		System.out.println("ID: " + currentlyMovingCharacter.getID());
		System.out.println("Items:");
		for (UsableItem item : currentlyMovingCharacter.getInventory())
			System.out.println("\t" + item.toString());
	}
	
	public static void PrintBlock(IceBlock block)
	{
		System.out.println("Type: " + block.toString());
		
		System.out.println("AmountOfSnow: " + block.getSnow());
		System.out.println("Stability: " + block.getStability());
		System.out.println("HasIgloo: " + block.hasIgloo());
		System.out.println("HasTent: " + block.hasTent());
		if (block.getSnow() > 0)
			System.out.println("Item: unknown");
		else if (block.getSnow() == 0 && block.getItem() == null)
			System.out.println("Item: no item");
		else
			System.out.println("Item: \t" + block.getItem().toString());
		
		System.out.println("Entities:");
		for (Entity entity : block.getEntities())
		{
			System.out.println("\t" + entity.toString());
		}
		System.out.println("Neighbours:");
		if (block.getNeighbours().get(Direction.LEFT) != null)
			System.out.println("\t" + block.getNeighbours().get(Direction.LEFT).toString() + " - " + Direction.LEFT.toString());
		if (block.getNeighbours().get(Direction.RIGHT) != null)
			System.out.println("\t" + block.getNeighbours().get(Direction.RIGHT).toString() + " - " + Direction.RIGHT.toString());
		if (block.getNeighbours().get(Direction.UP) != null)
			System.out.println("\t" + block.getNeighbours().get(Direction.UP).toString() + " - " + Direction.UP.toString());
		if (block.getNeighbours().get(Direction.DOWN) != null)
			System.out.println("\t" + block.getNeighbours().get(Direction.DOWN).toString() + " - " + Direction.DOWN.toString());
	}
	
	public static void LoadFile(String filePath)
	{
		iceMap = null;
		try
		{
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(file);
			
			iceMap = (IceMap) in.readObject();
			in.close();
			file.close();
		}
		catch(IOException ex) { System.out.println("IOException is caught"); return; }
		catch(ClassNotFoundException ex) { System.out.println("ClassNotFoundException is caught"); return; }
		
		blocks = iceMap.getBlocks();
		characters = new ArrayList<>();
		bear = null;
		for (ArrayList<IceBlock> row : blocks)
		{
			for (IceBlock iceBlock : row)
			{
				ArrayList<Entity> entities = iceBlock.getEntities();
				for (int k = 0; k < entities.size(); ++k)
				{
					Entity entity = iceBlock.getEntities().get(k);
					if (!(entity instanceof Bear))
						characters.add((Character) entity);
					else
						bear = (Bear) entity;
				}
			}
		}
		Game.get().init(iceMap, characters, bear);
	}
	
	public static void SaveToFile(String filePath)
	{
		try
		{
			//Saving of object in a file
			FileOutputStream file = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			// Method for serialization of object
			out.writeObject(Game.get().getIceMap());
			
			out.close();
			file.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}

