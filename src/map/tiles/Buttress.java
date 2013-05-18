package map.tiles;

import java.io.File;

import map.Zone;

public class Buttress extends Tile {
	
	private boolean destroyable = false;
	private boolean walkable = false;
	private boolean constructible = true;
	
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "HardVaccum_Tileset" + File.separator + "Terrain Tiles" + File.separator + "Sand.bmp";
	private int subImageX = 80;
	private int subImageY = 41;
	
	private Zone zone = null;

	public Buttress(Zone zone) {
		// TODO Auto-generated constructor stub
		this.zone = zone;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

	@Override
	public String getImageName() {
		return imageName;
	}
}
