package towers.strategy.shooter;

import map.Mapping;

public class Heal implements ShooterInterface {
	
	private int power = 0;

	public Heal(int power) {
		// TODO Auto-generated constructor stub
		this.power = power;
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		// TODO Auto-generated method stub
		System.out.println("Healing friend !");
	}

}
