package com.csakcintanyer.bme.projlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputWindow extends JFrame implements ActionListener
{

	//Text Fields
	JPanel inputBoxP= new JPanel();
	JTextField inputM;
	JTextField inputN;

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

		//Text Fields - pálya paramétereinek megadása

		inputBoxP.setSize(400, 50);

		inputM = new JTextField(2);
		inputM.setBackground(new Color(255, 255, 255, 255));
		inputM.setForeground(Color.black);
		inputM.setBorder(BorderFactory.createLineBorder(Color.black));

		inputN = new JTextField(2);
		inputN.setBackground(new Color(255, 255, 255, 255));
		inputN.setForeground(Color.black);
		inputN.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel textX = new JLabel("X");

		inputBoxP.add(inputM);
		inputBoxP.add(textX,BorderLayout.CENTER);
		inputBoxP.add(inputN);
		inputBoxP.setBackground(new Color(87, 126, 183, 255));

		//Button - OK gomb az elfogadáshoz
		JPanel readyButtonP = new JPanel();
		JButton readyButton = new JButton("OK");
		readyButton.setFocusPainted(false);
		readyButton.setContentAreaFilled(false);
		readyButton.setForeground(Color.white);
		readyButton.addActionListener(this);
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

	//Pálya inicializálás megadott paraméterekkel
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		//Text fields to int
		int M= Integer.parseInt(inputM.getText());
		int N= Integer.parseInt(inputN.getText());

		//megfelel a paraméter (3-15)?
		if(N >15 || N<3|| M>15 || M<3 )
			return;

		setVisible(false);
		Windows.get().menuWindow.setVisible(false);

		Game game = Game.get();
		game.init(N, M);

		MenuKeyEventListener listener = (MenuKeyEventListener)Windows.get().menuWindow.getKeyListeners()[0];
		listener.thread = new GameThread();
		listener.thread.start();
	}
}

class InputView extends JPanel
{

	public Dimension getPreferredSize()
	{
		return new Dimension(450, 50);
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		setBackground(new Color(87, 126, 183, 255));
	}
}
