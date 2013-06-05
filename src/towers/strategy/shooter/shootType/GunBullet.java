package towers.strategy.shooter.shootType;

import java.awt.Point;

import map.Mapping;
import players.Player;
import towers.strategy.shooter.ShootableObject;

public class GunBullet extends Projectile {
	
	public GunBullet(Point coordInTiles, ShootableObject target, Player owner,
			int force, int range, Mapping map) {
		super(coordInTiles, target, owner, force, range, map);
	}
}
