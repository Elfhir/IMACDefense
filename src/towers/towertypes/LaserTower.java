package towers.towertypes;

import java.util.Vector;

import towers.Tower;
import towers.strategy.shooter.Hurt;




public class LaserTower extends Tower {
	
	private Object lastCible;
	private int currentPower;

	public LaserTower(int price, int shootSpeed, int shootPower,
			int shootRange, Vector<Object> position2d) {
		super(price, shootSpeed, shootPower, shootRange, position2d);
		// TODO Auto-generated constructor stub
		this.shooter = new Hurt();
	}

	public Object getLastCible() {
		return lastCible;
	}

	public void setLastCible(Object lastCible) {
		this.lastCible = lastCible;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public void setCurrentPower(int currentPower) {
		this.currentPower = currentPower;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
