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

	public LaserTower(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.shooter = new IncreasingHurt(LaserTower.shootPower);
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
