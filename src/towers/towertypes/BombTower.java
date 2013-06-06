package towers.towertypes;

import java.awt.Point;
import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Bomb;

public class BombTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "bombtower.png";
	
	private static int price = 5000;
	private int shootSpeed = 4;
	private int shootPower = 0;
	private int shootRange = 5;

	public BombTower() {
		super();
		this.setShooter(new Bomb());
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

	@Override
	public int getShootSpeed() {
		return shootSpeed;
	}

	@Override
	public int getShootPower() {
		return shootPower;
	}

	@Override
	public int getShootRange() {
		return shootRange;
	}
}
