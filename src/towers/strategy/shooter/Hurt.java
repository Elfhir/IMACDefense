package towers.strategy.shooter;

import gameengine.MoveBulletAction;
import map.Mapping;

import towers.Tower;
import towers.strategy.shooter.shootType.GunBullet;

public class Hurt implements ShooterInterface {
	
	private Tower shootingtower;
	private GunBullet lastBullet;
	

	public Hurt(Tower shootingtower) {
		this.shootingtower = shootingtower;
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		
		if (shootingtower == null || shootingtower.getZone() == null || shootingtower.getZone().getOwner() == null)
			return;
		
		int power = shootingtower.getShootPower();
		int range = shootingtower.getShootRange();
		
		GunBullet bullet = new GunBullet(shootingtower.getCoordInTiles(), target, shootingtower.getZone().getOwner(), power, range, map);
		
		map.addGunBullet(bullet);
		
		lastBullet = bullet;
		
		/*if (target instanceof Tower)
		{
			if (((Tower) target).getLife() - this.power > 0)
			{
				System.out.println("Hurting ennemie tower !");
				Tower towerTarget = (Tower) target;
				towerTarget.setLife(towerTarget.getLife() - this.power);
				System.out.println("The ennemie tower life is now ");
				System.out.println(towerTarget.getLife());
			}
			else
			{
				System.out.println("Destructing ennemie now !");
				target.destruct(map);
			}
		}
		else
			System.out.println("Hurting ennemie !");*/
	}

	public GunBullet getLastBullet() {
		return lastBullet;
	}

}
