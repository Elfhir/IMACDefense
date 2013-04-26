package towers.towertypes;

import java.util.Vector;

import towers.Tower;
import towers.strategy.shooter.Bomb;




public class BombTower extends Tower {

	public BombTower(int price, int shootSpeed, int shootPower, int shootRange,
			Vector<Object> position2d) {
		super(price, shootSpeed, shootPower, shootRange, position2d);
		// TODO Auto-generated constructor stub
		this.shooter = new Bomb ();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
