package towers.strategy.shooter;

import map.Mapping;
import towers.Tower;
import towers.strategy.shooter.shootType.MedicalBullet;

public class Heal implements ShooterInterface {
	
	private Tower shootingtower;
	private MedicalBullet lastBullet;

	public Heal(Tower shootingtower) {
		// TODO Auto-generated constructor stub
		this.shootingtower = shootingtower;
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		if (shootingtower == null || shootingtower.getZone() == null || shootingtower.getZone().getOwner() == null)
			return;
		
		int power = shootingtower.getShootPower();
		int range = shootingtower.getShootRange();
		
		MedicalBullet bullet = new MedicalBullet(shootingtower.getCoordInTiles(), target, shootingtower.getZone().getOwner(), power, range, map);
		
		map.addBullet(bullet);
		
		lastBullet = bullet;
	}
	
	@Override
	public MedicalBullet getLastBullet() {
		return lastBullet;
	}

}
