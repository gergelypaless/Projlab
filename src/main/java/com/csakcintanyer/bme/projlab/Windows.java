package com.csakcintanyer.bme.projlab;

import javax.swing.*;

public class Windows
{
	public static Windows get()
	{
		if (instance == null)
			instance = new Windows();
		
		return instance;
	}
	private static Windows instance;
	
	private Windows()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				menuWindow = new MenuWindow();
				menuWindow.setVisible(true);
				
				gameWindow = new GameWindow();
				gameWindow.setVisible(false);
				
				controlsWindow = new ControlsWindow();
				controlsWindow.setVisible(false);
			}
		});
	}
	
	public MenuWindow menuWindow;
	public GameWindow gameWindow;
	public ControlsWindow controlsWindow;
	
}
