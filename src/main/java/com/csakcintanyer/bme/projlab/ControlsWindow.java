package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;

public class ControlsWindow extends JFrame //Controls Ablak
{
	public ControlsWindow()
	{
		super("Controls");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
		setContentPane(new ControlsView());
		pack();
		setLocationRelativeTo(null);
	}
}

class ControlsView extends JPanel //Controls Panel
{
	public Dimension getPreferredSize()
	{
		return new Dimension(530, 484);
	} //A kép méretére állítjuk a panelt

	//Kirajzoljuk a Controls.png-t
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		
		Image image = View.get().controlsIcon.getImage();
		g.drawImage(image, 0, 0, this);
	}
	
}
