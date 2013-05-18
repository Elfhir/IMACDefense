package map.tiles;

import java.io.File;

public class Field extends Tile {
	
	private boolean destroyable = false;
	private boolean walkable = true;
	private boolean constructible = false;
	
	private String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "HardVaccum_Tileset" + File.separator + "Terrain Tiles" + File.separator + "Sand.bmp";
	private int subImageX = 40;
	private int subImageY = 41;

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

	@Override
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
