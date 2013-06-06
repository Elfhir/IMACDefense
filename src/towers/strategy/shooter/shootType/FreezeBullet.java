package towers.strategy.shooter.shootType;

import java.awt.Point;

import map.Mapping;
import players.Player;
import towers.strategy.shooter.ShootableObject;

public class FreezeBullet extends Projectile {

	public FreezeBullet(Point coordInTiles, ShootableObject target,
			Player owner, int force, int range, Mapping map) {
		super(coordInTiles, target, owner, force, range, map);
	}
	
	@Override
	public void attack() {
		if (this.target instanceof ShootableObject)
		{
			((ShootableObject)this.target).setFrozen (true);
			((ShootableObject)this.target).setTotalFrozenTime(this.force*50);
		}
	}

}
