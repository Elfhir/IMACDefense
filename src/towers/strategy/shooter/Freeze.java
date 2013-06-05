package towers.strategy.shooter;

import map.Mapping;

public class Freeze implements ShooterInterface {

	public Freeze() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shoot(ShootableObject target, Mapping map) {
		// TODO Auto-generated method stub
		System.out.println("Freezing ennemie !");
	}

}
