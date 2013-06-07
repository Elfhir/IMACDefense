package towers.towertypes;

import towers.Tower;
import towers.strategy.shooter.Heal;

public class MedicalTower extends Tower {
	
	protected int width = 1; // Largeur en tiles
	protected int height = 1; // Hauteur en tiles

	private static int price = 2000;
	private int shootSpeed = 5;
	private int shootPower = 1;
	private int shootRange = 7;
	
	public MedicalTower() {
		super();
		this.setShooter(new Heal(this));
	}

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
	public int getShootPower() {
		return shootPower;
	}

	@Override
	public int getShootRange() {
		return shootRange;
	}

	@Override
	public int getShootSpeed() {
		return shootSpeed;
	}
}
