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
	private int inventoryX, inventoryY;
	
	private GameWindow gameWindow;
	private ControlsWindow controlsWindow;
	private MenuWindow menuWindow;
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
	public ImageIcon foodIcon = new ImageIcon("Assets/Items/food.png");
	
	public ImageIcon eskimoIcon = new ImageIcon("Assets/Characters/Eskimo1.png");
	public ImageIcon eskimoInWaterIcon = new ImageIcon("Assets/Characters/Eskimo1InWater.png");
	public ImageIcon explorerIcon = new ImageIcon("Assets/Characters/Explorer1.png");
	public ImageIcon explorerInWaterIcon = new ImageIcon("Assets/Characters/Explorer1InWater.png");

	public ImageIcon drowningIcon = new ImageIcon("Assets/Drowning.png");

	public ImageIcon bearIcon = new ImageIcon("Assets/Bear.png");
	public ImageIcon bearInWaterIcon = new ImageIcon("Assets/BearInWater.png");
	
	public ImageIcon iglooIcon = new ImageIcon("Assets/Igloo.png");
	public ImageIcon tentOnBlockIcon = new ImageIcon("Assets/TentOnBlock.png");
	
	public ImageIcon littleArrow = new ImageIcon("Assets/LittleArrow.png");
	
	public ImageIcon health1Icon = new ImageIcon("Assets/Health1.png");
	public ImageIcon health2Icon = new ImageIcon("Assets/Health2.png");
	public ImageIcon health3Icon = new ImageIcon("Assets/Health3.png");
	public ImageIcon health4Icon = new ImageIcon("Assets/Health4.png");
	public ImageIcon health5Icon = new ImageIcon("Assets/Health5.png");
	public ImageIcon hearthIcon = new ImageIcon("Assets/HearthIcon.png");
	
	public ImageIcon energyIcon = new ImageIcon("Assets/Energy.png");
	
	public ImageIcon bulletInInventoryIcon = new ImageIcon("Assets/BulletInInventory.png");
	public ImageIcon flareInInventoryIcon = new ImageIcon("Assets/FlareInInventory.png");
	public ImageIcon suitInInventoryIcon = new ImageIcon("Assets/SuitInInventory.png");
	
	public ImageIcon zeroIcon = new ImageIcon("Assets/SuitInInventory.png");
	public ImageIcon oneIcon = new ImageIcon("Assets/FlareInInventory.png");
	public ImageIcon twoIcon = new ImageIcon("Assets/BulletInInventory.png");
	public ImageIcon threeIcon = new ImageIcon("Assets/Energy.png");
	public ImageIcon infiniteIcon = new ImageIcon("Assets/TentOnBlock.png");
	public ImageIcon unknownIcon = new ImageIcon("Assets/Bear.png");
	
	public ImageIcon controlsIcon = new ImageIcon("Assets/Bear.png");
	
	public ImageIcon pressIForControlsIcon = new ImageIcon("Assets/HelpText.png");
	
	
	public void init(int x, int y)
	{
		N = 20 * 2 + x * 55 - 5 + 132; //szélesség
		M = 100 + y * 55 - 5 + 20 * 2; //magasság
		
		inventoryX = N - layoutIcon.getIconWidth();
		inventoryY = 0;
		
		
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
	
	public void ShowControlsWindow()
	{
		controlsWindow.setVisible(true);
	}
	
	public void ShowMenuWindow()
	{
		menuWindow.setVisible(true);
	}
	
	public void ShowGameWindow()
	{
		gameWindow.setVisible(true);
		menuWindow.setVisible(false);
	}
	
	public void repaint()
	{
		gameWindow.getContentPane().repaint();
	}
	
	public void update(Graphics g)
	{
		this.g = g;
		drawBackground();
		drawIceMap();
		drawInventory();
		drawBlockProperties();
		
		// press I text
		g.drawImage(pressIForControlsIcon.getImage(), inventoryX + 1, inventoryY + 275, null);
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
	
	private void drawEnergyCells(int energy, int x, int y)
	{
		if (energy == 0)
			return;
		
		g.drawImage(energyIcon.getImage(), x, y, null);
		drawEnergyCells(energy - 1, x + 15, y);
	}
	
	public void drawInventory()
	{
		Image image = layoutIcon.getImage();
		g.drawImage(image, inventoryX, inventoryY, null);
		
		Character currentlyMovingCharacter = Game.get().getCurrentlyMovingCharacter();
		
		// character icon
		if (currentlyMovingCharacter instanceof Eskimo)
		{
			image = eskimoIcon.getImage();
		}
		else if (currentlyMovingCharacter instanceof Explorer)
		{
			image = explorerIcon.getImage();
		}
		g.drawImage(image, inventoryX + 20, inventoryY + 13, null);
		
		// health
		int health = currentlyMovingCharacter.getHealth();
		switch (health)
		{
			case 0: image = null; break;
			case 1: image = health1Icon.getImage(); break;
			case 2: image = health2Icon.getImage(); break;
			case 3: image = health3Icon.getImage();	break;
			case 4: image = health4Icon.getImage(); break;
			case 5: image = health5Icon.getImage(); break;
			default: throw new IllegalArgumentException("Nem lehet ennyi élet");
		}
		g.drawImage(image, inventoryX + 53, inventoryY + 30, null);
		
		// hearth
		image = hearthIcon.getImage();
		g.drawImage(image, inventoryX + 46, inventoryY + 27, null);
		
		// energy
		drawEnergyCells(currentlyMovingCharacter.getEnergy(), inventoryX + 64, inventoryY + 12);
		
		// not usable items
		if (currentlyMovingCharacter.hasBullet())
		{
			image = bulletInInventoryIcon.getImage();
			g.drawImage(image, inventoryX + 14, inventoryY + 64, null);
		}
		if (currentlyMovingCharacter.hasFlare())
		{
			image = flareInInventoryIcon.getImage();
			g.drawImage(image, inventoryX + 50, inventoryY + 64, null);
		}
		if (currentlyMovingCharacter.hasSuit())
		{
			image = suitInInventoryIcon.getImage();
			g.drawImage(image, inventoryX + 86, inventoryY + 64, null);
		}
		
		// items in inventory
		for (int i = 0; i < currentlyMovingCharacter.getInventory().size(); ++i)
		{
			CollectableItem collectableItem = (CollectableItem)currentlyMovingCharacter.getInventory().get(i);
			collectableItem.draw(inventoryX + 16 + (i % 3) * 36, inventoryY + 126 + (i / 3) * 36);
		}
		if (!Game.get().iceMapSelected)
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(1));
			g2d.drawRect(inventoryX + 14 + (Game.get().getSelectedItem() % 3) * 36, inventoryY + 124 + (Game.get().getSelectedItem() / 3) * 36, 33, 33);
		}
	}
	
	public void drawSelection(int x, int y)
	{
		if (Game.get().iceMapSelected)
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(2));
			g2d.drawRect(x - 3, y - 3, 55, 55);
		}
	}
	
	public void drawBlockProperties()
	{
		int xPos = 92;
		int yPos = 220;
		
		IceBlock selected = Game.get().getSelectedIceBlock();
		
		Image image;
		if (!selected.isChecked())
		{
			image = unknownIcon.getImage();
			g.drawImage(image, inventoryX + xPos, inventoryY + yPos, null);
			return;
		}
		
		int stability = selected.getStability();
		switch (stability)
		{
			case 0: image = zeroIcon.getImage(); break;
			case 1: image = oneIcon.getImage(); break;
			case 2: image = twoIcon.getImage(); break;
			case 3: image = threeIcon.getImage(); break;
			case -1: image = infiniteIcon.getImage(); break;
			default: throw new IllegalArgumentException("Nem lehet ilyen stability");
		}
		g.drawImage(image, inventoryX + xPos, inventoryY + yPos, null);
	}
}
