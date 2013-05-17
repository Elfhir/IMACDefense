package towers.towertypes;


import java.util.LinkedList;

import towers.Tower;
import towers.strategy.shooter.Freeze;


public class FreezeTower extends Tower {

	public FreezeTower(int price, int shootSpeed, int shootPower,
			int shootRange, LinkedList<Object> position2d) {
		super(price, shootSpeed, shootPower, shootRange, position2d);
		// TODO Auto-generated constructor stub
		this.shooter = new Freeze ();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
