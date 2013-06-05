package gameengine;

import java.awt.Point;
import java.awt.event.ActionEvent;

import basis.Base;

import agents.Agent;

import towers.strategy.shooter.ShootableObject;
import towers.strategy.shooter.shootType.GunBullet;
import towers.strategy.shooter.shootType.MovableBullet;
import window.GraphicalInterface;

import map.tiles.Tile;

public class MoveBulletAction extends Action {
	
	MovableBullet bullet = null;
	
	public MoveBulletAction(MovableBullet bullet, GraphicalInterface frame, int timer) {
		super(frame, timer);
		this.bullet = bullet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (bullet == null)
		{
			bullet = null;
			this.timer.stop();
			return;
		}
		
		if (bullet.getForce() <= 0)
		{
			bullet.destruct(map);
			bullet = null;
			this.timer.stop();
			return;
		}
		
		Point point = bullet.getPosition2d();
    	int tileheight = Tile.getHeight();
    	int tilewidth = Tile.getWidth();
    	
    	pan.repaint ((int)point.getX(),(int)(point.getY()-tileheight/2), tilewidth, tileheight*2);

    	bullet.move(map);
    	
    	if (bullet instanceof GunBullet)
    	{
    		((GunBullet)bullet).changeTarget(map);
    	}
    	
    	// Si le bullet est arriv� � destination
		if (bullet.arrivedAtDestination())
		{
			
			if (bullet.getTarget() != null)
			{
				/*
				 *  Si c'est un agent, la cible est une base
				 *  -> Soit elle va �tre attaqu�e
				 *  -> Soit elle va recevoir des agents amis
				 */
				bullet.attack ();
				// Si c'est un agent : On repeint la base-cible, pour que le nombre de cette base se mette � jour.
				// Sinon : on repeint tout simplement, au cas o� quelque chose aurait �t� d�truit.
				
				if (bullet.getTarget() instanceof ShootableObject && ((ShootableObject) bullet.getTarget()).getLife() <= 0)
				{
					((ShootableObject) bullet.getTarget()).destruct(map);
				}
				
				pan.repaint();
			}
			// On enl�ve le bullet de la map : on n'en n'a plus besoin.
			bullet.destruct(map);
			// Le bullet est mis � null.
			bullet = null;
			// On stoppe le timer.
			this.timer.stop();
			// On sort de la fonction.
			return;
		}
		
    	if (!bullet.getPosition2d().equals(point))
    	{
    		pan.repaint();
    		//pan.repaint ((int)bullet.getPosition2d().getX(),(int)bullet.getPosition2d().getY()-MovableBullet.getHeight()/2, MovableBullet.getWidth(), MovableBullet.getHeight()*2);
    	}
	}
}