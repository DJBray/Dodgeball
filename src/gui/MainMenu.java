package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.Board;

public class MainMenu extends JFrame{
	public MenuPanel menuPanel;
	
	public MainMenu(){
		super("Dodgeball");
		
		menuPanel = new MenuPanel();
		
		setMinimumSize(new Dimension(Board.WIDTH + 5, Board.HEIGHT + 30));
		setLayout(new BorderLayout());
		add(menuPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		setLocationRelativeTo(null);
		this.setResizable(false);
		
		setVisible(true);
	}
}
