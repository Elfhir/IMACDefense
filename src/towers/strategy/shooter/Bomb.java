package towers.strategy.shooter;

import towers.strategy.shooter.shootType.Projectile;
import map.Mapping;

public class Bomb implements ShooterInterface {

	public Bomb() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		// TODO Auto-generated method stub
		System.out.println("Putting a bomb now !");
	}

	@Override
	public Projectile getLastBullet() {
		// TODO Auto-generated method stub
		return null;
	}

}
