package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.chrono.ThaiBuddhistEra;

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
	JFrame window; //MenuWindow
	public Thread thread; //GameThread


	//menü irányítása
	public void MenuAction(KeyEvent keyEvent){
		Container view = window.getContentPane();
		MenuView menuView = (MenuView)view;

		switch (keyEvent.getKeyCode()){

			//Up/W - menüpont felfele
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				if(menuView.getCurrentMenuPoint()==View.get().newgameIconColored.getImage())
				{
					menuView.setCurrentMenuPoint(View.get().exitIconColored.getImage());
					menuView.setCurrentMenuPointx(350);
					menuView.setCurrentMenuPointy(600);
				}
				else if(menuView.getCurrentMenuPoint()==View.get().loadgameIconColored.getImage())
				{
					menuView.setCurrentMenuPoint(View.get().newgameIconColored.getImage());
					menuView.setCurrentMenuPointx(285);
					menuView.setCurrentMenuPointy(380);
				}
				else if(menuView.getCurrentMenuPoint()==View.get().exitIconColored.getImage())
				{
					menuView.setCurrentMenuPoint(View.get().loadgameIconColored.getImage());
					menuView.setCurrentMenuPointx(265);
					menuView.setCurrentMenuPointy(500);
				}
				break;

			//Down/S - menüpont lefele
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				if(menuView.getCurrentMenuPoint()==View.get().newgameIconColored.getImage())
				{
					menuView.setCurrentMenuPoint(View.get().loadgameIconColored.getImage());
					menuView.setCurrentMenuPointx(265);
					menuView.setCurrentMenuPointy(500);
				}
				else if(menuView.getCurrentMenuPoint()==View.get().loadgameIconColored.getImage())
				{
					menuView.setCurrentMenuPoint(View.get().exitIconColored.getImage());
					menuView.setCurrentMenuPointx(350);
					menuView.setCurrentMenuPointy(600);
				}
				else if(menuView.getCurrentMenuPoint()==View.get().exitIconColored.getImage())
				{
					menuView.setCurrentMenuPoint(View.get().newgameIconColored.getImage());
					menuView.setCurrentMenuPointx(285);
					menuView.setCurrentMenuPointy(380);
				}
				break;

			//Enter - menüpont kiválasztása
			case KeyEvent.VK_ENTER:
				//Startgame - új játék indítása
				if(menuView.getCurrentMenuPoint()==View.get().newgameIconColored.getImage())
				{
					Windows.get().inputWindow.setVisible(true);
				}

				//Loadgame - pálya betöltés és játék indítása
				if (menuView.getCurrentMenuPoint()==View.get().loadgameIconColored.getImage())
				{
					window.setVisible(false);
					IOLanguage.LoadFile("save.txt");
					thread = new GameThread();
					thread.start();
				}

				//Exit - program bezárása
				if (menuView.getCurrentMenuPoint()==View.get().exitIconColored.getImage())
				{
					System.exit(0);
				}
				break;
		}
	}

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
		MenuAction(keyEvent);
		window.repaint();
	}
}

// játékmenetet futtató szál
class GameThread extends Thread
{
	public GameThread() {}
	
	public void run()
	{
		View.get().repaint();
		Game.get().start();
	}
}

//Menü kirajzolása
class MenuView extends JPanel
{

	//Kiválasztott menüpont nyilvántartása
	private Image currentMenuPoint = View.get().newgameIconColored.getImage();
	private int currentMenuPointx = 285;
	private int currentMenuPointy= 380;

	public void setCurrentMenuPointx(int currentMenuPointx) {
		this.currentMenuPointx = currentMenuPointx;
	}

	public void setCurrentMenuPointy(int currentMenuPointy) {
		this.currentMenuPointy = currentMenuPointy;
	}

	public Image getCurrentMenuPoint() {
		return currentMenuPoint;
	}

	public void setCurrentMenuPoint(Image image) {
		 currentMenuPoint=image;
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(860, 960);
	}

	public void draw(Image image,int x,int y, Graphics g){
		g.drawImage(image, x, y, this);
	}

	//Menü kirajzolása
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		draw(View.get().backgroundIcon.getImage(),0,0, g);
		draw(View.get().titleIcon.getImage(),72,175,g);
		draw(View.get().menuIceBlockIcon.getImage(),230,330,g);
		draw(View.get().newgameIcon.getImage(),285,380,g);
		draw(View.get().loadgameIcon.getImage(),265,500,g);
		draw(View.get().exitIcon.getImage(),350,600,g);
		draw(View.get().menuBearIcon.getImage(),61,790,g);
		draw(getCurrentMenuPoint(),currentMenuPointx,currentMenuPointy,g);
	}
}
