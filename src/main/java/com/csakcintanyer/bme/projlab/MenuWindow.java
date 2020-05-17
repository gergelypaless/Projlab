package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuWindow extends JFrame
{
	public MenuWindow()
	{
		super("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setContentPane(new MenuView());
		pack();
		setLocationRelativeTo(null);
		
		MenuKeyEventListener listener = new MenuKeyEventListener(this);
		addKeyListener(listener);
	}
}

class MenuKeyEventListener implements KeyListener
{
	JFrame window;
	
	public MenuKeyEventListener(JFrame window)
	{
		this.window = window;
	}
	
	@Override
	public void keyTyped(KeyEvent keyEvent)
	{
	
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
	
	}
	
	@Override
	public void keyReleased(KeyEvent keyEvent)
	{
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
		{
			// open game window
			View.get().ShowGameWindow();
		}
	}
}

class MenuView extends JPanel
{
	public Dimension getPreferredSize()
	{
		return new Dimension(200, 200);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		
		Image image = View.get().bearIcon.getImage();
		g.drawImage(image, 0, 0, this);
	}
}
