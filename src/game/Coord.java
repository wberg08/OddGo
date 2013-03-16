package game;

public class Coord {
	
	private int x;
	private int y;
	
	public Coord (int x, int y) {
		
		this.setX(x);
		this.setY(y);
		
	}
	
	@Override
	public boolean equals (Object o) {
		
		if (o instanceof Coord) {
			
			Coord c = (Coord)o;			
			return x==c.getX() && y==c.getY();
			
		}
	
	return false;
	
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

}
