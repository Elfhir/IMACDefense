package map.tiles;

import java.io.File;
import java.util.ArrayList;

import map.Zone;

public class Tile {
	
	private static int width = 20;
	private static int height = 20;
	private TileType type = TileType.Field;
	private int x = 0;
	private int y = 0;

	protected boolean destroyable = false;
	protected boolean walkable = true;
	protected boolean constructible = false;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tile ()
	{
		super ();
	}
	
	public Tile (TileType type)
	{
		super ();
		this.type = type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public TileType getType() {
		return type;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getImageName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSubImageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSubImageY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
