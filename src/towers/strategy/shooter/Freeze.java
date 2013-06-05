package towers.strategy.shooter;

import towers.strategy.shooter.shootType.Projectile;
import map.Mapping;

public class Freeze implements ShooterInterface {

	public Freeze() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		// TODO Auto-generated method stub
		System.out.println("Freezing ennemie !");
	}

	@Override
	public Projectile getLastBullet() {
		// TODO Auto-generated method stub
		return null;
	}

}
