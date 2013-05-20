package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Freeze;

public class FreezeTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "freezetower.png";
	
	private static int price = 2000;
	private static int shootSpeed = 3;
	private static int shootPower = 0;
	private static int shootRange = 4;

	public FreezeTower() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public String getImageName() {
		return imageName;
	}
}
