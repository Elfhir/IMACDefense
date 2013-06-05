package towers.strategy.shooter;

import towers.strategy.shooter.shootType.Projectile;
import map.Mapping;

public interface ShooterInterface {
	public void shoot (ShootableObject target, Mapping map);
	public Projectile getLastBullet ();
}
