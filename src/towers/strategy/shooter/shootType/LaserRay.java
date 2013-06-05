package towers.strategy.shooter.shootType;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import agents.Agent;

import players.Player;
import towers.Tower;
import towers.strategy.shooter.ShootableObject;

import map.Mapping;

public class LaserRay extends Projectile {

	public LaserRay(Point coordInTiles, ShootableObject target, Player owner,
			int force, int range, Mapping map) {
		super(coordInTiles, target, owner, force, range, map);
	}
	
	@Override
	public void changeTarget(Mapping map)
	{
		if (this.coordInTiles == this.initialPoint)
			return;
		
		Line2D.Double ray = new Line2D.Double(initialPoint, coordInTiles);

		Hashtable<Point, Tower> mapTowers = map.getTowers();
		Set<Point> set = mapTowers.keySet();
		Iterator<Point> it = set.iterator();

		// On vérifie si la cible se trouve sur la ligne du rayon laser.
		while (it.hasNext())
		{
			Point currentPoint = it.next();
			Tower currentTower = mapTowers.get(currentPoint);
			if (currentTower != null && ray.contains(currentPoint))
			{
				this.setTarget(currentTower);
				System.out.println("why");
				return;
			}
		}
		
		Iterator<Agent> it2 = map.getAgents().iterator();
		while (it.hasNext())
		{
			Agent newAgentTarget = it2.next();
			if (newAgentTarget.getCoordInTiles().equals(this.getcoordInTiles()) || ray.contains(newAgentTarget.getCoordInTiles()))
			{
				this.setTarget(newAgentTarget);
				System.out.println("wei");
				return;
			}
		}
	}
}
