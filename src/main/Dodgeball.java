package main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Dodgeball extends JFrame{
	private static final long serialVersionUID = -3307339575804436527L;
	Board board;
	
	public Dodgeball(){
		super("Dodgeball");
		board = new Board();
		
		setMinimumSize(new Dimension(Board.WIDTH + 5, Board.HEIGHT + 30));
		setLayout(new BorderLayout());
		add(board);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		setLocationRelativeTo(null);
		this.setResizable(false);
		
		setVisible(true);
	}

	public static void main(String[] args){
		new Dodgeball();
	}
}
