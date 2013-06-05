package towers.strategy.shooter;

import map.Mapping;
import towers.Tower;

public class IncreasingHurt implements ShooterInterface {
	
	private Object lastTarget;
	private int initialPower = 0;
	private int currentPower = 0;

	public IncreasingHurt(int power) {
		// TODO Auto-generated constructor stub
		this.initialPower = power;
		this.currentPower = power;
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		// TODO Auto-generated method stub
		
		// Gestion de la puissance
		if (target.equals(lastTarget))
			this.currentPower ++;
		else
			this.currentPower = this.initialPower;
		
		if (target instanceof Tower)
		{
			Tower towerTarget = (Tower) target;
			towerTarget.setLife (towerTarget.getLife() - this.currentPower);
			System.out.println("Hurting Tower Ennemie. Current Power : ");
			System.out.println(this.currentPower);
		}
	}

}
