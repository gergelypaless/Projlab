package com.csakcintanyer.bme.projlab;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class IOLanguage
{
	// szerializált fájl betöltése
	public static void LoadFile(String filePath)
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
		ArrayList<Character> characters = new ArrayList<>();
		Bear bear = null;
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
	
	// játékállá szerializálása
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

