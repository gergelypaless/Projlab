package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;

public class View
{
	static View instance; //Mivel a View osztály singleton osztály
	static View get()
	{
		if (instance == null)
			instance = new View();

		return instance;
	}
	private View() {}

	public int N, M; //Eltároljuk az ablak konkrét szélességét és magasságát (pixelszámban)
	private int inventoryX, inventoryY; //Ebben azt tároljuk, hogy milyen koordinátákra van kirajzolva a jobboldali panel

	private Graphics g; //A Graphics osztály segítségével végezzük el a kirajzolásokat

	//Háttér, és jobboldali panel Ikonja
	public ImageIcon backgroundIcon = new ImageIcon("Assets/Background.png");
	public ImageIcon layoutIcon = new ImageIcon("Assets/Layout/Layout.png");

	//Menü képei
	public ImageIcon menuIceBlockIcon = new ImageIcon("Assets/Menu/menuIceBlock.png");
	public ImageIcon newgameIcon = new ImageIcon("Assets/Menu/newgame.png");
	public ImageIcon loadgameIcon = new ImageIcon("Assets/Menu/loadgame.png");
	public ImageIcon exitIcon = new ImageIcon("Assets/Menu/exit.png");
	public ImageIcon newgameIconColored = new ImageIcon("Assets/Menu/newgameColored.png");
	public ImageIcon loadgameIconColored = new ImageIcon("Assets/Menu/loadgameColored.png");
	public ImageIcon exitIconColored = new ImageIcon("Assets/Menu/exitColored.png");
	public ImageIcon menuBearIcon = new ImageIcon("Assets/Menu/menuBear.png");
	public ImageIcon titleIcon = new ImageIcon("Assets/Menu/iceventure.png");

	//3 IceBlock típus képei (Havas, Hó nélküli, Üres)
	public ImageIcon snowyIceBlockIcon = new ImageIcon("Assets/IceBlocks/SnowyIceBlock.png");
	public ImageIcon icyIceBlockIcon = new ImageIcon("Assets/IceBlocks/IcyIceBlock.png");
	public ImageIcon emptyIceBlockIcon = new ImageIcon("Assets/IceBlocks/EmptyIceBlock.png");

	//Item-ek képe
	public ImageIcon bulletIcon = new ImageIcon("Assets/Items/bullet.png");
	public ImageIcon flareIcon = new ImageIcon("Assets/Items/flare.png");
	public ImageIcon gunIcon = new ImageIcon("Assets/Items/gun.png");
	public ImageIcon tentIcon = new ImageIcon("Assets/Items/tent.png");
	public ImageIcon fragileShovelIcon = new ImageIcon("Assets/Items/fshovel.png");
	public ImageIcon shovelIcon = new ImageIcon("Assets/Items/shovel.png");
	public ImageIcon ropeIcon = new ImageIcon("Assets/Items/rope.png");
	public ImageIcon suitIcon = new ImageIcon("Assets/Items/suit.png");
	public ImageIcon foodIcon = new ImageIcon("Assets/Items/food.png");

	//Karakterek képe (Vízben és nem vízben)
	public ImageIcon eskimoIcon = new ImageIcon("Assets/Characters/Eskimo1.png");
	public ImageIcon eskimoInWaterIcon = new ImageIcon("Assets/Characters/Eskimo1InWater.png");
	public ImageIcon explorerIcon = new ImageIcon("Assets/Characters/Explorer1.png");
	public ImageIcon explorerInWaterIcon = new ImageIcon("Assets/Characters/Explorer1InWater.png");

	//Ha egy karaktert ki kell menteni, egy piros felkiáltójelet rajzolunk felé. Ez itt a felkiltó jel képe
	public ImageIcon drowningIcon = new ImageIcon("Assets/Drowning.png");

	//Medve képe (Vízben és nem vízben)
	public ImageIcon bearIcon = new ImageIcon("Assets/Bear.png");
	public ImageIcon bearInWaterIcon = new ImageIcon("Assets/BearInWater.png");

	//Igloo és Sátor képe a jégtáblán
	public ImageIcon iglooIcon = new ImageIcon("Assets/Igloo.png");
	public ImageIcon tentOnBlockIcon = new ImageIcon("Assets/TentOnBlock.png");

	//Éppen irányított játékos felett megjelenő nyilacska képe
	public ImageIcon littleArrow = new ImageIcon("Assets/LittleArrow.png");

	//Életeket mutató képek (Külön életszinteknek külön képe van)
	public ImageIcon health1Icon = new ImageIcon("Assets/Health1.png");
	public ImageIcon health2Icon = new ImageIcon("Assets/Health2.png");
	public ImageIcon health3Icon = new ImageIcon("Assets/Health3.png");
	public ImageIcon health4Icon = new ImageIcon("Assets/Health4.png");
	public ImageIcon health5Icon = new ImageIcon("Assets/Health5.png");
	public ImageIcon hearthIcon = new ImageIcon("Assets/HearthIcon.png");

	//Ahány energiánk van, annyi ilyen kis zöld négyzetet (azaz ezt a képet) rajzolunk ki.
	public ImageIcon energyIcon = new ImageIcon("Assets/Energy.png");

	//A baloldali panelen megjelenő képek azokról az Item-ekről amik nem használhatóak expliciten (nem UsableItem)
	public ImageIcon bulletInInventoryIcon = new ImageIcon("Assets/BulletInInventory.png");
	public ImageIcon flareInInventoryIcon = new ImageIcon("Assets/FlareInInventory.png");
	public ImageIcon suitInInventoryIcon = new ImageIcon("Assets/SuitInInventory.png");

	//A kijelölt jégtábla stabilitását kiíró képek (0-3, vagy végtelen, vagy ismeretlen)
	public ImageIcon zeroIcon = new ImageIcon("Assets/Layout/ZeroIcon.png");
	public ImageIcon oneIcon = new ImageIcon("Assets/Layout/OneIcon.png");
	public ImageIcon twoIcon = new ImageIcon("Assets/Layout/TwoIcon.png");
	public ImageIcon threeIcon = new ImageIcon("Assets/Layout/ThreeIcon.png");
	public ImageIcon infiniteIcon = new ImageIcon("Assets/Layout/InfiniteIcon.png");
	public ImageIcon unknownIcon = new ImageIcon("Assets/Layout/UnknownIcon.png");

	//Az irányítást bemutató kép
	public ImageIcon controlsIcon = new ImageIcon("Assets/Controls.png");

	//Segédszöveg a jobboldali panel alján
	public ImageIcon pressIForControlsIcon = new ImageIcon("Assets/HelpText.png");

	//A játék végén megjelenő szövegek kép (Nyertünk/Vesztettünk)
	public ImageIcon LoseIcon = new ImageIcon("Assets/GameOver.png");
	public ImageIcon WinIcon = new ImageIcon("Assets/WIN.png");

	//Az Ablak méreteit kiszámoljuk attól függően, hogy mekkora pályát szeretnénk
	public void init(int x, int y)
	{
		N = 20 * 2 + x * 55 - 5 + 132; //szélesség
		M = 100 + y * 55 - 5 + 20 * 2; //magasság

		inventoryX = N - layoutIcon.getIconWidth();
		inventoryY = 0;

		Windows.get().gameWindow.init();
		Windows.get().gameWindow.setVisible(true);
	}

	//Újrarajzoljuk a pályát
	public void repaint()
	{
		Windows.get().gameWindow.getContentPane().repaint();
	}

	//Újrarajzoljuk a pályát
	public void update(Graphics g)
	{
		this.g = g;
		drawBackground();
		drawIceMap();
		drawInventory();
		drawBlockProperties();

		if(Game.get().isLost()) drawLose();
		else if(Game.get().isWin()) drawWin();

		// press I text
		g.drawImage(pressIForControlsIcon.getImage(), inventoryX + 1, inventoryY + 275, null);
	}

	//Háttér kirajzolása
	private void drawBackground()
	{
		Image image = backgroundIcon.getImage();
		g.drawImage(image, 0, 0, null);
	}

	//Pálya kirajzolása
	public void drawIceMap()
	{
		Game.get().getIceMap().draw(0, 0);
	}

	//Győzelem felirat kirajzolása
	public void drawWin() {
		Image image = WinIcon.getImage();
		g.drawImage(image, ((N-132)/2)-72, 50, null);
	}

	//Vereség felirat kirajzolása
	public void drawLose() {
		Image image = LoseIcon.getImage();
		g.drawImage(image, ((N-132)/2)-92, 50, null);
	}
	
	// altalanos draw fuggveny. Az atadott kepet x,y koordinatara ezzel rajzoljuk ki.
	public void draw(ImageIcon icon, int x, int y)
	{
		Image image = icon.getImage();
		g.drawImage(image, x, y, null);
	}

	//Energia szint kirajzolása
	private void drawEnergyCells(int energy, int x, int y)
	{
		if (energy == 0)
			return;

		g.drawImage(energyIcon.getImage(), x, y, null);
		drawEnergyCells(energy - 1, x + 15, y);
	}

	//Az éppen irányított karakter jellemzőinek kirajzolása a jobboldali panelen
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

	//Éppen kijelölt jégtábla köré piros keretet rajzoló függvény
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

	//Az éppen kijelölt jégtábla stabilitásáról tárolt információ kirajzolása
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
