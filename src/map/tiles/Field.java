package map.tiles;

public class Field extends Tile {
	
	private boolean destroyable = false;
	private boolean walkable = true;
	private boolean constructible = false;
	
	private int subImageX = 20;
	private int subImageY = 20;

	public Field() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getSubImageX ()
	{
		return this.subImageX;
	}
	
	@Override
	public int getSubImageY ()
	{
		return this.subImageY;
	}
}
