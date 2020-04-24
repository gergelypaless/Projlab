package com.csakcintanyer.bme.projlab;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		setLogLevel(Level.INFO);

		if (args.length > 0)
		{
			TestWithFile(args[0]);
		}
		else
		{
			ReadFromConsole();
		}
		
		Game game = Game.get();

		// játék elindítása
		game.start();
	}
	
	private static void ReadFromConsole() throws IOException
	{
		int N;
		int M;
		ArrayList<Character> characters = new ArrayList<>();
		Bear bear = new Bear();
		ArrayList<ArrayList<IceBlock>> blocks = new ArrayList<>();
		IceMap iceMap = null;
		int snowInXTurns = -1;
		
		// temporary varibales
		int x;
		int y;
		IceBlock block;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input;
		
		while (!(input = reader.readLine()).equals("done"))
		{
			if (input.equals(""))
				continue;
			
			String[] elements = input.split(" ");
			switch (elements[0])
			{
				case "map":
					N = Integer.parseInt(elements[1]);
					M = Integer.parseInt(elements[2]);
					for (int j = 0; j < M; ++j)
					{
						blocks.add(new ArrayList<>());
						input = reader.readLine();
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
					break;
				case "load":
					break;
				case "save":
					break;
				case "character":
					Character character = null;
					if (elements[1].equals("Eskimo"))
					{
						character = new Eskimo(characters.size());
						characters.add(character);
					}
					if (elements[1].equals("Explorer"))
					{
						character = new Explorer(characters.size());
						characters.add(character);
					}
					
					if (character == null)
						throw new IllegalArgumentException("wrong character type");
					if (iceMap == null)
						throw new IllegalArgumentException("wrong icemap was not initialized");
					
					x = Integer.parseInt(elements[2]);
					y = Integer.parseInt(elements[3]);
					block = iceMap.getBlocks().get(y).get(x);
					character.setIceBlock(block);
					//characters.get(characters.size() - 1).setIceBlock(iceMap.getBlocks().get(Integer.parseInt(elements[3])).get(Integer.parseInt(elements[2])));
					break;
				case "item":
					x = Integer.parseInt(elements[2]);
					y = Integer.parseInt(elements[3]);
					block = iceMap.getBlocks().get(y).get(x);
					
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
					break;
				case "deterministic":
					snowInXTurns = Integer.parseInt(elements[1]);
					break;
				case "bear":
					x = Integer.parseInt(elements[1]);
					y = Integer.parseInt(elements[2]);
					block = iceMap.getBlocks().get(y).get(x);
					bear.setIceBlock(block);
					break;
				default:
					throw new IllegalArgumentException("wrong command");
			}
		}
		
		Game.get().init(iceMap, characters, bear, snowInXTurns);
	}
	
	private static void SerializeGame(String filePath)
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
			System.out.println("IOException is caught");
		}
	}
	
	private static void TestWithFile(String filePath)
	{
		IceMap iceMap = null;
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
		
		ArrayList<ArrayList<IceBlock>> blocks = iceMap.getBlocks();
		int N = blocks.size();
		int M = blocks.get(0).size();
		ArrayList<Character> characters = new ArrayList<>();
		Bear bear = null;
		for (int i = 0; i < N; ++i)
		{
			for (int j = 0; j < M; ++j)
			{
				ArrayList<Entity> entities = blocks.get(i).get(j).getEntities();
				for (int k = 0; k < entities.size(); ++k)
				{
					Entity entity = blocks.get(i).get(j).getEntities().get(k);
					if (!(entity instanceof Bear))
						characters.add((Character)entity);
					else
						bear = (Bear)entity;
				}
			}
		}
		Game.get().init(iceMap, characters, bear);
	}
	
	// logger inicializáló függvény
	private static void setLogLevel(Level level)
	{
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(level);
		for (Handler h : rootLogger.getHandlers())
		{
			h.setLevel(level);
			h.setFormatter(new SimpleFormatter() {
				private static final String format = "[%1$tF %1$tT] [%2$-7s] [%3$s]: %4$s %n";
				
				@Override
				public synchronized String format(LogRecord lr) {
					return String.format(format,
							new Date(lr.getMillis()),
							lr.getLevel().getLocalizedName(),
							lr.getSourceClassName() + "." + lr.getSourceMethodName(),
							lr.getMessage()
					);
				}
			});
		}
	}
}



	/*
			// N, M
			int N = (int) in.readObject();
			int M = (int) in.readObject();
			
			// blocks
			ArrayList<ArrayList<IceBlock>> blocks = new ArrayList<>();
			for (int i = 0; i < N; ++i)
			{
				blocks.add(new ArrayList<>());
				for (int j = 0; j < M; ++j)
				{
					blocks.get(i).add((IceBlock)in.readObject());
				}
			}
			
			// numOfCharacters
			int numOfCharacters = (int) in.readObject();
			// characters
			ArrayList<Character> characters = new ArrayList<>();
			for (int i = 0; i < numOfCharacters; ++i)
			{
				characters.add((Character) in.readObject());
			}
			
			// bear
			Bear bear = (Bear) in.readObject();
			
			// collectable items
			int numOfCollectableItems = (int) in.readObject();
			ArrayList<CollectableItem> collectableItems = new ArrayList<>();
			for (int i = 0; i < numOfCollectableItems; ++i)
			{
				collectableItems.add((CollectableItem)in.readObject());
			}*/
			
	
	