package towers.strategy.shooter.shootType;

import map.Mapping;

public interface AttackingObject {
	public void destruct (Mapping map);
	public int getPower ();
}
