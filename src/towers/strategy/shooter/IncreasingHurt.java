package towers.strategy.shooter;

import map.Mapping;
import towers.Tower;
import towers.strategy.shooter.shootType.LaserRay;

public class IncreasingHurt implements ShooterInterface {
	
	private Tower shootingtower;
	private LaserRay lastlaser;
	
	public IncreasingHurt(Tower shootingtower) {
		this.shootingtower = shootingtower;
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		
		if (lastlaser != null && (lastlaser.getTarget() == null || !lastlaser.getTarget().equals(target)))
		{
			lastlaser.setTarget(target);
			lastlaser.destruct(map);
			lastlaser = null;
		}
		
		if (lastlaser == null)
		{
			if (shootingtower == null || shootingtower.getZone() == null || shootingtower.getZone().getOwner() == null)
				return;
			
			int power = shootingtower.getShootPower();
			int range = shootingtower.getShootRange();
			
			LaserRay laserray = new LaserRay(shootingtower.getCoordInTiles(), target, shootingtower.getOwner(), power, range, map);
			
			map.addBullet(laserray);
			
			lastlaser = laserray;
		}
		
		// Gestion de la puissance
		/*if (target.equals(lastTarget))
			this.currentPower ++;
		else
			this.currentPower = this.initialPower;
		
		if (target instanceof Tower)
		{
			Tower towerTarget = (Tower) target;
			towerTarget.setLife (towerTarget.getLife() - this.currentPower);
			System.out.println("Hurting Tower Ennemie. Current Power : ");
			System.out.println(this.currentPower);
		}*/
	}

	@Override
	public LaserRay getLastBullet() {
		return lastlaser;
	}
}
