package main;

public class Player extends Moveable{
	public static final int SPEED = 2;
	
	public Player(String url, int width, int height){
		super(url, width, height);
		this.setX(60);
		this.setY(60);
	}

	public Player(String url,int width, int height, int x, int y, int direction){
		super(url, width, height, x, y, direction);
	}
	
	public void move(){
		if(getDirection() == Moveable.RIGHT && getX() + getWidth() < Board.WIDTH)
			setX(getX() + 2);
		else if(getDirection() == Moveable.LEFT && getX() > 0)
			setX(getX() - 2);
		else if(getDirection() == Moveable.UP && getY() > 0)
			setY(getY() - 2);
		else if(getDirection() == Moveable.DOWN && getY() + getHeight() < Board.HEIGHT)
			setY(getY() + 2);
	}
}
