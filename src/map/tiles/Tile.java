package map.tiles;

import java.awt.Point;

public class Tile {
	
	private static int width = 20;
	private static int height = 20;
	private Point coordInTiles = null;

	protected boolean destroyable = false;
	protected boolean walkable = true;
	protected boolean constructible = false;

	public Tile (Point point)
	{
		super ();
		this.coordInTiles = point;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public int getSubImageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSubImageY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isObstacle() {
		// TODO Auto-generated method stub
		return !this.walkable;
	}

	public Point getCoordsInMap() {
		// TODO Auto-generated method stub
		return this.coordInTiles;
	}

}
