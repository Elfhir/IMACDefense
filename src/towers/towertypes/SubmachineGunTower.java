package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Hurt;

public class SubmachineGunTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "submachinegun.png";

	private static int price = 3000;
	private static int shootSpeed = 10;
	private static int shootPower = 2;
	private static int shootRange = 5;

	public SubmachineGunTower() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getPrice() {
		return price;
	}
	
	@Override
	public String getImageName() {
		return imageName;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}
