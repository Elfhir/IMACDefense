package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Heal;

public class MedicalTower extends Tower {
	
	protected int width = 1; // Largeur en tiles
	protected int height = 1; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "medicaltower.png";
	
	private static int price = 2000;
	private static int shootSpeed = 5;
	private static int shootPower = 4;
	private static int shootRange = 7;
	
	public MedicalTower() {
		super();
		this.setShooter(new Heal(shootPower));
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
