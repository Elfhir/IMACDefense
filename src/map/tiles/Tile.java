package map.tiles;

import map.Zone;

public class Tile {
	
	private TileType type = TileType.Field;
	private Zone zone = null;

	public Tile ()
	{
		super ();
	}
	
	public Tile (TileType type)
	{
		super ();
		this.type = type;
	}
	
	public Tile (TileType type, Zone zone)
	{
		super ();
		this.type = type;
		this.zone = zone;
	}
	
	public Zone getZone() {
		return zone;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public TileType getType() {
		return type;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}