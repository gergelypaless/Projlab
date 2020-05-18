package com.csakcintanyer.bme.projlab;

import java.awt.event.*;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame
{
	public GameWindow()
	{
		super("IceVenture");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
		GameKeyEventListener listener = new GameKeyEventListener(this);
		addKeyListener(listener);
		
		WindowCloseListener windowCloseListener = new WindowCloseListener();
		addWindowListener(windowCloseListener);
	}
	
	public void init()
	{
		setContentPane(new GameView());
		pack();
		setLocationRelativeTo(null);
	}
}

class WindowCloseListener implements ActionListener, WindowListener
{
	public void actionPerformed(ActionEvent actionEvent) {}
	public void windowOpened(WindowEvent windowEvent) {	}
	
	public void windowClosing(WindowEvent windowEvent)
	{
		Windows.get().menuWindow.setVisible(true);
		
		// ending game
		Game game = Game.get();
		game.lose();
		game.endTurn();
		
		MenuKeyEventListener listener = (MenuKeyEventListener)Windows.get().menuWindow.getKeyListeners()[0];
		try
		{
			listener.thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void windowClosed(WindowEvent windowEvent) {	}
	public void windowIconified(WindowEvent windowEvent) { }
	public void windowDeiconified(WindowEvent windowEvent) { }
	public void windowActivated(WindowEvent windowEvent) { }
	public void windowDeactivated(WindowEvent windowEvent) { }
}

class GameKeyEventListener implements KeyListener
{
	JFrame window;
	
	public GameKeyEventListener(JFrame window)
	{
		this.window = window;
	}
	
	public void keyTyped(KeyEvent keyEvent)	{ }
	public void keyPressed(KeyEvent keyEvent) {	}
	
	public void keyReleased(KeyEvent keyEvent)
	{
		Game.get().UserAction(keyEvent);
		window.repaint();
	}
}

class GameView extends JPanel
{
	public GameView()
	{
		setFocusTraversalKeysEnabled(false);
	}
	
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


