package towers.towertypes;

import java.io.File;

import towers.Tower;
import towers.strategy.shooter.Heal;

public class MedicalTower extends Tower {
	
	protected int width = 1; // Largeur en tiles
	protected int height = 1; // Hauteur en tiles

	protected String imageName = System.getProperty("user.dir") + File.separator + "img" + File.separator + "buildings" + File.separator + "medical.png";
	
	private static int price = 2000;
	private static int shootSpeed = 5;
	private static int shootPower = 4;
	private static int shootRange = 7;

	public MedicalTower(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.shooter = new Heal(MedicalTower.shootPower);
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
