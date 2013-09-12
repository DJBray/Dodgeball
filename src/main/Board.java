package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	private static final long serialVersionUID = 972743376110236695L;
	public static final int HEIGHT = 700;
	public static final int WIDTH = 1000;
	
	public static final String PLAYER = "player.png";
	public static final int p_width = 40;
	public static final String ENEMY = "enemy.png";
	public static final int e_width = 20;
	public static final String APPLE = "apple.png";
	public static final int a_width = 30;
	
	public static final String HIGHSCORES_PATH = "highscores.dat";
	public static final int NUM_SCORES = 5;
	
	private ArrayList<Moveable> enemies;
	private Moveable player;
	private Moveable apple;
	
	private Random rand;
	private int score;
	
	private boolean gameover;
	
	private Timer t;
	private Audioapp a;
	
	public Board(){
		super();
		rand = new Random();
		setupp();
		addKeyListener(new TAdapter());
		setDoubleBuffered(true);
		setFocusable(true);
		a = new Audioapp("SCS_mvt5_d#21.mid");
		a.play();
	}
	
	public void setupp(){
		setBackground(Color.BLUE);
		t = new Timer(5, this);
		gameover = false;
		score = 0;
		enemies = new ArrayList<Moveable>();
		enemies.add(new Enemy(ENEMY, e_width, e_width));
		player = new Player(PLAYER, p_width, p_width, 
				(int)(rand.nextDouble() * (WIDTH - p_width)), 
				(int)(rand.nextDouble() * (HEIGHT - p_width)), 
				Moveable.STILL);
		apple = new Moveable(APPLE, a_width, a_width,
				(int)(rand.nextDouble() * (WIDTH - a_width)), 
				(int)(rand.nextDouble() * (HEIGHT - a_width)), 
				Moveable.STILL);
		t.start();
	}
 
	public void paint(Graphics g){
		super.paint(g);
		if(gameover){
			gameover(g);
		}
		else{
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(apple.getX(), apple.getY(), a_width, a_width);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(player.getX(), player.getY(), p_width, p_width);
			g2d.setColor(Color.BLACK);
			for(Moveable e : enemies){
				g2d.fillRect(e.getX(), e.getY(), e_width, e_width);
			}

			String s = "Score: " + score;
			Font small = new Font("Helvetica", Font.BOLD, 32);
			g2d.setFont(small);
			g2d.drawString(s, 10, 30);

			Toolkit.getDefaultToolkit().sync();
		}
		g.dispose(); //is this even necessary?
	}
	
	private void gameover(Graphics g){
		this.setBackground(Color.BLACK);
		String a = "Game";
		String o = "Over";
		Font f = new Font("Times New Roman", Font.BOLD, 32);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(a, (WIDTH / 2) - 50, 180);
		g.drawString(o, (WIDTH / 2) - 40, HEIGHT - 190);
	}
	
	private void checkHSExists(){
		File f = new File(HIGHSCORES_PATH);
		if(!f.exists()){
			for(int i = 0; i < Board.NUM_SCORES; i++){
				HighScore.write(new HighScore(), i, Board.HIGHSCORES_PATH);
			}
		}
	}
	
	//TODO: Doesn't work correctly, needs to push down scores, not replace highest (Queue)
	private void handleScores(){
		checkHSExists();
		
		String scores = "High Scores:\n\n";
		boolean done = false;
		LinkedList<HighScore> hsArr = new LinkedList<HighScore>();

		for(int i = 0; i < NUM_SCORES; i++){
			HighScore hs = HighScore.read(i, HIGHSCORES_PATH);
			if(score > hs.getScore() && !done){
				HighScore newscore = new HighScore();
				newscore.setScore(score);
				while(!done){
					done = false;
					try{
						String nn = JOptionPane.showInputDialog(null, "New High Score! Enter three initials: ");
						newscore.setName(nn.toCharArray());
						done = true;
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "You're name must be 3 characters.");
					}
				}
				score = 0;
				hsArr.add(newscore);
			}
			hsArr.add(hs);
		}
			
		for(int i = 0; i<NUM_SCORES; i++){
			HighScore hs = hsArr.removeFirst();
			scores += hs.getScore() + " " + hs.getNameString() + "\n";
			HighScore.write(hs, i, Board.HIGHSCORES_PATH);
		}
		scores += "\n\nPlay Again?";
		
		
		if(JOptionPane.showConfirmDialog(null, scores, "High Scores", JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION){
			setupp();
		}
		else{
			a.stop();
			System.exit(0);
		}
	}
	
	private void movePieces(){
		player.move();
		for(Moveable e: enemies){
			e.move();
		}
	}
	
	private void checkCollision(){
		for(Moveable e : enemies){
			if(player.contains(e)){
				gameover = true;
			}
		}
	}
	
	private void checkGotApple(){
		if(player.contains(apple)){
			apple = new Moveable(APPLE, a_width, a_width,
					(int)(rand.nextDouble() * (WIDTH - a_width)), 
					(int)(rand.nextDouble() * (HEIGHT - a_width)), 
							Moveable.STILL);
			score++;
			enemies.add(new Enemy(ENEMY, e_width, e_width, 
					(int)(rand.nextDouble() * (WIDTH - e_width)), 
					(int)(rand.nextDouble() * (HEIGHT - e_width)), 
					(int)(rand.nextDouble() * 4 + 1)));
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(gameover){
			handleScores();
			t.stop();
		}
		else{
			movePieces();
			repaint();
			checkCollision();
			checkGotApple();
		}
	}
	
	public class TAdapter extends KeyAdapter{
		private boolean key[] = {false, false, false,false};
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				player.setDirection(Moveable.LEFT);
				key[0] = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				player.setDirection(Moveable.RIGHT);
				key[1] = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP){
				player.setDirection(Moveable.UP);
				key[2] = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				player.setDirection(Moveable.DOWN);
				key[3] = true;
			}
		}
		
		public void keyReleased(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				key[0] = false;
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				key[1] = false;
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP){
				key[2] = false;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				key[3] = false;
			}
			if(!key[0] && !key[1] && !key[2] && !key[3])
				player.setDirection(Moveable.STILL);
		}
	}
}
