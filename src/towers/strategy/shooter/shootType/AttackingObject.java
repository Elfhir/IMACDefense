package towers.strategy.shooter.shootType;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import map.Mapping;

public interface AttackingObject {
	public void destruct (Mapping map);
	public int getPower ();
	public void setHitBox ();
	public boolean isInHitBox (Point point);
	public Rectangle2D.Double getHitBox ();
}
