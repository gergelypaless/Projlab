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
		//                                                 + 16 ??                                            + 36??
		setSize(20 * 2 + View.get().N * 55 - 5 + 132 + 16, 100 + View.get().M * 55 - 5 + 20 * 2 + 36);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setContentPane(new ContentPane());
		setLocationRelativeTo(null);
		
		AEventListener listener = new AEventListener(this);
		addKeyListener(listener);
	}
}


class AEventListener implements KeyListener
{
	JFrame window;
	
	public AEventListener(JFrame window)
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
		if (keyEvent.getKeyCode() == KeyEvent.VK_A)
		{
			window.repaint();
		}
	}
}

class ContentPane extends JPanel
{
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


