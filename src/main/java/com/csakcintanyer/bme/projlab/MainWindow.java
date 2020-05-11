package com.csakcintanyer.bme.projlab;

import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame
{
	public MainWindow()
	{
		super("MainWindow");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		AEventListener listener = new AEventListener(this);
		addKeyListener(listener);
	}
	
	public void paint(Graphics g)
	{
		System.out.println("paint");
		View.get().update();
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
		if (keyEvent.getKeyCode() == KeyCode.A.getCode())
		{
			System.out.println("repaint");
			window.repaint();
		}
	}
}

