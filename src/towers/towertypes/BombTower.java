package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Bomb;

public class BombTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "bombtower.png";
	
	private static int price = 5000;
	private static int shootSpeed = 4;
	private static int shootPower = 0;
	private static int shootRange = 5;

	public BombTower() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPrice() {
		return price;
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
