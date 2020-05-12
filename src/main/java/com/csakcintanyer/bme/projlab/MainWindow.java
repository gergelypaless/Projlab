package com.csakcintanyer.bme.projlab;

//import javafx.scene.input.KeyCode;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
	public MainWindow()
	{
		super("MainWindow");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setContentPane(new ContentPane());
		pack();
		setLocationRelativeTo(null);
		
		KeyEventListener listener = new KeyEventListener(this);
		addKeyListener(listener);
	}
}


class KeyEventListener implements KeyListener
{
	JFrame window;
	
	public KeyEventListener(JFrame window)
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
		Game.get().UserAction(keyEvent);
		window.repaint();
	}
}

class ContentPane extends JPanel
{
	public Dimension getPreferredSize()
	{
		return new Dimension(View.get().N, View.get().M);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		
		Image windowImage = createImage(getWidth(), getHeight());
		Graphics windowGraphics = windowImage.getGraphics();
		paintToImage(windowGraphics);
		g.drawImage(windowImage, 0, 0, this);
	}
	
	private void paintToImage(Graphics g)
	{
		View.get().update(g);
	}
}


