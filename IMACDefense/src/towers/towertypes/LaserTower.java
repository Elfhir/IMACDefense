package towers.towertypes;

import java.util.LinkedList;

import towers.Tower;
import towers.strategy.shooter.IncreasingHurt;

public class LaserTower extends Tower {

	public LaserTower(int price, int shootSpeed, int shootPower,
			int shootRange, LinkedList<Object> position2d) {
		super(price, shootSpeed, shootPower, shootRange, position2d);
		// TODO Auto-generated constructor stub
		this.shooter = new IncreasingHurt(this.shootPower);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
