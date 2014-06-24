package Map;

public class Position {
	private int x;
	private int y;
	
	public Position() {
		x = -1;
		y = -1;
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isValid(int width, int height) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			return true;
		}
		return false;
	}
	
	public int dist(Position pos) {
		return x - pos.x + y - pos.y;
	}
	
	public String toString() {
		return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
	}
}
