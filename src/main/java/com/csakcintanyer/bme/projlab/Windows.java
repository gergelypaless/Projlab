package com.csakcintanyer.bme.projlab;

import javax.swing.*;

public class Windows //Ablakokat kezelő class
{
	public static Windows get()
	{
		if (instance == null)
			instance = new Windows();
		
		return instance;
	}
	private static Windows instance; //singleton
	
	private Windows()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				menuWindow = new MenuWindow(); //Menü ablaka
				menuWindow.setVisible(true);
				
				gameWindow = new GameWindow(); //Játék ablaka
				gameWindow.setVisible(false);
				
				controlsWindow = new ControlsWindow(); //Irányításokat mutató ablak
				controlsWindow.setVisible(false);
				
				inputWindow = new InputWindow(); //Pályakészítő ablak
				inputWindow.setVisible(false);
			}
		});
	}
	
	public MenuWindow menuWindow;
	public GameWindow gameWindow;
	public ControlsWindow controlsWindow;
	public InputWindow inputWindow;
	
}
