package towers.towertypes;

import java.awt.Point;
import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Hurt;

public class SubmachineGunTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "submachineguntower.png";

	private static int price = 3000;
	private int shootSpeed = 10;
	private int shootPower = 2;
	private int shootRange = 5;

	public int getShootPower() {
		return shootPower;
	}

	public int getShootRange() {
		return shootRange;
	}

	public SubmachineGunTower() {
		super();
		this.setShooter(new Hurt(this));
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
	public int getObjectWidth() {
		return width;
	}

	@Override
	public int getObjectHeight() {
		return height;
	}

	@Override
	public int getShootSpeed() {
		return shootSpeed;
	}
}
