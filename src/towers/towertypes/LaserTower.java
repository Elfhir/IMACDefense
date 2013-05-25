package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.IncreasingHurt;

public class LaserTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "lasertower.png";

	private static int price = 5000;
	private static int shootSpeed = 3;
	private static int shootPower = 8;
	private static int shootRange = 6;
	
	public LaserTower() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public int getObjectWidth() {
		return width;
	}
	
	@Override
	public int getObjectHeight() {
		return height;
	}

	@Override
	public String getImageName() {
		return imageName;
	}
}
