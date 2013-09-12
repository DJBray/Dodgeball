package main;

import java.util.Random;

public class Enemy extends Moveable{
	private int speed;
	private Random r;

	public Enemy(String url, int width, int height){
		super(url, width, height);
		r = new Random();
		speed = (int)(r.nextDouble()*4 + 1);
		this.setX(40);
		this.setY(40);
		this.setDirection(Moveable.UP);
	}

	public Enemy(String url,int width, int height, int x, int y, int direction){
		super(url, width, height, x, y, direction);
		r = new Random();
		speed = (int)(r.nextDouble()*4 + 1);
	}
	
	public void move(){
		if(getDirection() == Moveable.RIGHT)
			setX(getX() + speed);
		else if(getDirection() == Moveable.LEFT)
			setX(getX() - speed);
		else if(getDirection() == Moveable.UP)
			setY(getY() - speed);
		else if(getDirection() == Moveable.DOWN)
			setY(getY() + speed);
		
		if(getX() <= 0)
			setDirection(Moveable.RIGHT);
		else if(getY() <= 0)
			setDirection(Moveable.DOWN);
		else if(getY() + getHeight() >= Board.HEIGHT)
			setDirection(Moveable.UP);
		else if(getX() + getWidth() >= Board.WIDTH)
			setDirection(Moveable.LEFT);
	}
}
