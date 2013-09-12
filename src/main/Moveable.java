package main;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Moveable {
	
	public static final int TOP_SPEED = 4;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int STILL = -1;
	
	private int x, y, width, height;
	private String url;
	private Image image;
	private int direction;

	public Moveable(String url, int width, int height){
		this.url = url;
		this.width = width;
		this.height = height;
		x = 40;
		y = 40;
		direction = STILL;
		ImageIcon ii = new ImageIcon(url);
		image = ii.getImage();
	}

	public Moveable(String url,int width, int height, int x, int y, int direction){
		this.url = url;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.direction = direction;
		ImageIcon ii = new ImageIcon(url);
		image = ii.getImage();
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public boolean contains(Moveable obj){
		return (x + width > obj.getX() && x < obj.getX() + obj.getWidth() && 
				y + height > obj.getY() && y < obj.getY() + obj.getHeight());
	}
	
	public void move(){
		//Defined in other classes
	}
}
