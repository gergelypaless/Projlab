package com.csakcintanyer.bme.projlab;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		/*
		if (args.length > 0)
			IOLanguage.LoadFile(args[0]);
		
		if (!IOLanguage.ReadFromConsole())
			return;
		*/
		
		Game game = Game.get();
		game.init();
		game.start();
	}

}


	
	