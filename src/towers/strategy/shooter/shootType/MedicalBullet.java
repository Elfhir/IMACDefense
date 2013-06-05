package towers.strategy.shooter.shootType;

import java.awt.Point;

import map.Mapping;
import players.Player;
import towers.strategy.shooter.AttackableObject;
import towers.strategy.shooter.ShootableObject;

public class MedicalBullet extends Projectile {

	public MedicalBullet(Point coordInTiles, ShootableObject target,
			Player owner, int force, int range, Mapping map) {
		super(coordInTiles, target, owner, force, range, map);
	}

	@Override
	public void setTarget(AttackableObject target) {
		if (target.getOwner().equals(this.getOwnerPlayer()))
		{
			this.target = target;
			this.finalCoordInTiles = target.getCoordInTiles();
		}
	}
	
	@Override
	public void attack() {
		if (target instanceof ShootableObject)
			((ShootableObject)target).setLife (((ShootableObject)target).getLife()+this.force);
	}

}
