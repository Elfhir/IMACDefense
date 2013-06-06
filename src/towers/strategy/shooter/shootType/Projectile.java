package towers.strategy.shooter.shootType;

import java.awt.Point;
import java.util.Iterator;

import agents.Agent;

import map.Mapping;
import players.Player;
import towers.Tower;
import towers.strategy.shooter.ShootableObject;

public class Projectile extends MovableBullet {
	
	protected int range;
	protected Point initialPoint;

	public Projectile(Point coordInTiles, ShootableObject target, Player owner,
			int force, int range, Mapping map) {
		super(coordInTiles, target, owner, force, map);
		this.initialPoint = this.coordInTiles;
		this.range = range;
		this.finalCoordInTiles = new Point ((int)coordInTiles.getX () + range, (int)coordInTiles.getY() + range);
	}
	
	@Override
	public void move (Mapping map)
	{
		super.move(map);
		if (outOfRange(map))
		{
			this.finalCoordInTiles = null;
			this.target = null;
		}
	}
	
	// Calcule si le bullet a atteint la portée maximale de la tour OU s'il est sorti de la map.
	public boolean outOfRange (Mapping map)
	{		
		if (direction == Direction.right || direction == Direction.bottomright || direction == Direction.topright)
		{
			if (this.coordInTiles.getX() + 1 >= map.getWidth())
				return true;
			if (this.coordInTiles.getX() + 1 >= this.initialPoint.getX() + range)
				return true;
		}
		if (direction == Direction.left || direction == Direction.bottomleft || direction == Direction.topleft)
		{
			if (this.coordInTiles.getX() - 1 <= 0)
				return true;
			if (this.coordInTiles.getX() - 1 <= this.initialPoint.getX() - range)
				return true;
		}
		if (direction == Direction.bottom || direction == Direction.bottomright || direction == Direction.bottomleft)
		{
			if (this.coordInTiles.getY() + 1 >= map.getHeight())
				return true;
			if (this.coordInTiles.getY() + 1 >= this.initialPoint.getY() + range)
				return true;
		}
		if (direction == Direction.top || direction == Direction.topright || direction == Direction.topleft)
		{
			if (this.coordInTiles.getY() - 1 <= 0)
				return true;
			if (this.coordInTiles.getY() - 1 <= this.initialPoint.getY() - range)
				return true;
		}
		return false;
	}
	
	// Si une autre cible traverse le chemin de la balle, alors celle-ci devient la nouvelle cible.
	public void changeTarget (Mapping map)
	{
		if (this.coordInTiles == this.initialPoint)
			return;
		
		Tower newTowerTarget = map.getTowers().get(this.getcoordInTiles());
		if (newTowerTarget != null)
		{
			this.setTarget(newTowerTarget);
			return;
		}
		
		Iterator<Agent> it = map.getAgents().iterator();
		while (it.hasNext())
		{
			Agent newAgentTarget = it.next();
			if (newAgentTarget.getHitBox().intersects(this.hitbox))
			{
				this.setTarget(newAgentTarget);
				return;
			}
		}
	}

	public Point getInitialPoint() {
		return initialPoint;
	}
	
	public void attack ()
	{
		target.beAttacked(this);
	}
	
	@Override
	public void destruct (Mapping map)
	{
		map.removeBullet(this);
	}
}
