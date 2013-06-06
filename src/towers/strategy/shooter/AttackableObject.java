package towers.strategy.shooter;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import players.Player;

import towers.strategy.shooter.shootType.AttackingObject;

public interface AttackableObject {
	
	// Attaquable par tirs ou par agents.
	public Point getCoordInTiles ();
	public Player getOwner ();
	public void beAttacked (AttackingObject object);
	public boolean isInHitBox (Point point);
	public void setHitBox ();
	public Rectangle2D.Double getHitBox();
}
