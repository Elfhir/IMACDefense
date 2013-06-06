package towers.strategy.shooter;

import map.Mapping;
import towers.Tower;
import towers.strategy.shooter.shootType.FreezeBullet;

public class Freeze implements ShooterInterface {
	
	private Tower shootingtower;
	private FreezeBullet lastbullet;

	public Freeze(Tower shootingtower) {
		this.shootingtower = shootingtower;
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		if (shootingtower == null || shootingtower.getZone() == null || shootingtower.getZone().getOwner() == null)
			return;
		
		int power = shootingtower.getShootPower();
		int range = shootingtower.getShootRange();
		
		FreezeBullet bullet = new FreezeBullet(shootingtower.getCoordInTiles(), target, shootingtower.getZone().getOwner(), power, range, map);
		
		map.addBullet(bullet);
		
		this.lastbullet = bullet;
	}

	@Override
	public FreezeBullet getLastBullet() {
		return this.lastbullet;
	}

}
