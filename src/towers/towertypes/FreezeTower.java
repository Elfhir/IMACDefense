package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Freeze;

public class FreezeTower extends Tower {
	
	protected int width = 2; // Largeur en tiles
	protected int height = 2; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "freezetower.png";
	
	private static int price = 2000;
	private int shootSpeed = 3;
	private int shootPower = 1;
	private int shootRange = 4;

	public FreezeTower() {
		super();
		this.setShooter(new Freeze(this));
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
