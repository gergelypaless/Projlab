package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;

public class View
{
	static View instance;
	static View get()
	{
		if (instance == null)
			instance = new View();
		
		return instance;
	}
	private View() {}
	
	
	public int N, M; // stores view space width and height
	
	private MainWindow mainWindow;
	private Graphics g;
	
	public ImageIcon backgroundIcon = new ImageIcon("Assets/Background.png");
	public ImageIcon layoutIcon = new ImageIcon("Assets/Layout.png");
	
	public ImageIcon snowyIceBlockIcon = new ImageIcon("Assets/IceBlocks/SnowyIceBlock.png");
	public ImageIcon icyIceBlockIcon = new ImageIcon("Assets/IceBlocks/IcyIceBlock.png");
	public ImageIcon emptyIceBlockIcon = new ImageIcon("Assets/IceBlocks/EmptyIceBlock.png");
	
	public ImageIcon bulletIcon = new ImageIcon("Assets/Items/bullet.png");
	public ImageIcon flareIcon = new ImageIcon("Assets/Items/flare.png");
	public ImageIcon gunIcon = new ImageIcon("Assets/Items/gun.png");
	public ImageIcon tentIcon = new ImageIcon("Assets/Items/tent.png");
	public ImageIcon fragileShovelIcon = new ImageIcon("Assets/Items/fshovel.png");
	public ImageIcon shovelIcon = new ImageIcon("Assets/Items/shovel.png");
	public ImageIcon ropeIcon = new ImageIcon("Assets/Items/rope.png");
	public ImageIcon suitIcon = new ImageIcon("Assets/Items/suit.png");
	public ImageIcon foodIcon = new ImageIcon("Assets/Background.png");
	
	public ImageIcon eskimoIcon = new ImageIcon("Assets/Characters/Eskimo1.png");
	public ImageIcon explorerIcon = new ImageIcon("Assets/Characters/Explorer1.png");
	
	public ImageIcon bearIcon = new ImageIcon("Assets/Bear.png");
	
	public ImageIcon iglooIcon = new ImageIcon("Assets/Igloo.png");
	public ImageIcon tentOnBlockIcon = new ImageIcon("Assets/TentOnBlock.png");
	
	public void init(int x, int y)
	{
		N = 20 * 2 + x * 55 - 5 + 132; //szélesség
		M = 100 + y * 55 - 5 + 20 * 2; //magasság
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				mainWindow = new MainWindow();
				mainWindow.setVisible(true);
			}
		});
	}
	
	public void repaint()
	{
		mainWindow.getContentPane().repaint();
	}
	
	public void update(Graphics g)
	{
		this.g = g;
		drawBackground();
		drawIceMap();
		DrawInventory();
		DrawBlockProperties();
	}
	
	private void drawBackground()
	{
		Image image = backgroundIcon.getImage();
		g.drawImage(image, 0, 0, null);
	}
	
	public void drawIceMap()
	{
		Game.get().getIceMap().draw(0, 0);
	}
	
	public void draw(ImageIcon icon, int x, int y)
	{
		Image image = icon.getImage();
		g.drawImage(image, x, y, null);
	}
	
	public void DrawInventory()
	{
		Image image = layoutIcon.getImage();
		g.drawImage(image, N - layoutIcon.getIconWidth(), 0, null);
	}
	
	public void DrawBlockProperties()
	{
	
	}
}
