package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;

public class InputWindow extends JFrame
{
	public InputWindow()
	{
		// init
		super("Input");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);


		setContentPane(new InputView());
		pack();
		setLocationRelativeTo(null);
		setBackground(new Color(87, 126, 183, 255));


		//Text Fields
		JPanel inputBoxP= new JPanel();
		inputBoxP.setSize(400, 50);

		JTextField inputM = new JTextField(2);
		inputM.setBackground(new Color(255, 255, 255, 255));
		inputM.setForeground(Color.white);
		inputM.setBorder(BorderFactory.createLineBorder(Color.black));

		JTextField inputN = new JTextField(2);
		inputN.setBackground(new Color(255, 255, 255, 255));
		inputN.setForeground(Color.white);
		inputN.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel textX = new JLabel("X");

		inputBoxP.add(inputM);
		inputBoxP.add(textX,BorderLayout.CENTER);
		inputBoxP.add(inputN);
		inputBoxP.setBackground(new Color(87, 126, 183, 255));

		//Button
		JPanel readyButtonP = new JPanel();
		JButton readyButton = new JButton("OK");
		readyButton.setFocusPainted(false);
		readyButton.setContentAreaFilled(false);
		readyButton.setForeground(Color.white);
		readyButtonP.add(readyButton);
		readyButtonP.setBackground(new Color(87, 126, 183, 255));

		//Text
		JPanel textP = new JPanel();
		JLabel text = new JLabel("Please enter the map size (3-15): ");
		text.setForeground(Color.white);
		textP.setBackground(new Color(87, 126, 183, 255));
		textP.add(text, BorderLayout.CENTER);

		this.add(textP, BorderLayout.NORTH);
		this.add(inputBoxP,BorderLayout.CENTER);
		this.add(readyButtonP, BorderLayout.EAST);
	}

}

class InputView extends JPanel
{

	public Dimension getPreferredSize()
	{
		return new Dimension(400, 50);
	}


	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		setBackground(new Color(87, 126, 183, 255));
	}
}
