package gameengine;

import java.awt.Point;
import java.awt.event.ActionEvent;

import map.tiles.Tile;
import towers.strategy.shooter.ShootableObject;
import towers.strategy.shooter.shootType.MovableBullet;
import towers.strategy.shooter.shootType.Projectile;
import window.GraphicalInterface;

public class MoveBulletAction extends Action {
	
	MovableBullet bullet = null;
	int frozencounter = 0;
	
	public MoveBulletAction(MovableBullet bullet, GraphicalInterface frame, int timer) {
		super(frame, timer);
		this.bullet = bullet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (bullet instanceof ShootableObject && ((ShootableObject)bullet).isFrozen())
		{
			frozencounter++;
			if (frozencounter >= ((ShootableObject)bullet).getTotalFrozenTime())
			{
				((ShootableObject)bullet).setFrozen(false);
				frozencounter = 0;
			}
			return;
		}
		
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
    	
    	if (bullet instanceof Projectile)
    	{
    		((Projectile)bullet).changeTarget(map);
    	}
    	
    	// Si le bullet est arrivé à destination
		if (bullet.arrivedAtDestination())
		{
			if (bullet.getTarget() != null)
			{
				/*
				 *  Si c'est un agent, la cible est une base
				 *  -> Soit elle va être attaquée
				 *  -> Soit elle va recevoir des agents amis
				 */
				bullet.attack ();
				// Si c'est un agent : On repeint la base-cible, pour que le nombre de cette base se mette à jour.
				// Sinon : on repeint tout simplement, au cas où quelque chose aurait été détruit.
				
				if (bullet.getTarget() instanceof ShootableObject && ((ShootableObject) bullet.getTarget()).getLife() <= 0)
				{
					((ShootableObject) bullet.getTarget()).destruct(map);
				}
				
				pan.repaint();
			}
			// On enlève le bullet de la map : on n'en n'a plus besoin.
			bullet.destruct(map);
			// Le bullet est mis à null.
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
