package gameengine;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import map.Mapping;
import towers.Tower;
import window.GraphicalInterface;
import towers.strategy.shooter.Hurt;
import towers.strategy.shooter.IncreasingHurt;

public class TowerShootAction extends Action {
	
	Mapping map;
	Tower tower;

	public TowerShootAction(Mapping map, GraphicalInterface frame, int timer, Tower tower) {
		super(frame, timer);
		// TODO Auto-generated constructor stub
		this.map = map;
		this.tower = tower;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			tower.calculateBestTarget (map);
			
			if (tower.getTarget() != null)
			{
				tower.getShooter().shoot(tower.getTarget(), map);
				MoveBulletAction mba = new MoveBulletAction(tower.getShooter().getLastBullet(), this.frame, 100);
				mba.run();
			}
			
			if (tower.getTarget() != null && tower.getTarget().isDestructed())
			{
				tower.setTarget(null);
			}
	}
	
	/*public static void main (String[] args)
	{
		Mapping map = new Mapping ("map1.xml");
		SubmachineGunTower tower1 = new SubmachineGunTower();
		BombTower tower2 = new BombTower();
		
		tower1.setCoordInTiles(new Point(0,0));
		tower2.setCoordInTiles(new Point(10,10));
		
		tower1.setZone(new Zone (new Player(0, PlayerColor.red)));
		tower2.setZone(new Zone (new Player(1, PlayerColor.blue)));
		
		map.setTower(tower1, 0, 0);
		map.setTower(tower2, 10, 10);
		
		TowerShootAction tsa = new TowerShootAction(tower1, tower2, map, new GraphicalInterface(), 1);
		tsa.run();
	}*/

}
