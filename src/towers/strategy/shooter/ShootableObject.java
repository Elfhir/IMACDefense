package towers.strategy.shooter;

import map.Mapping;

public interface ShootableObject extends AttackableObject {
	public void destruct(Mapping map);
	public boolean isDestructed();
	public int getLife();
	public void setLife(int life);
	public void setFrozen (boolean frozen);
	public boolean isFrozen ();
	public int getTotalFrozenTime();
	public void setTotalFrozenTime(int frozentime);
}
