package towers.towertypes;
import java.util.Vector;

import towers.*;
import towers.strategy.shooter.Heal;




public class MedicalTower extends Tower {

	public MedicalTower(int price, int shootSpeed, int shootPower,
			int shootRange, Vector<Object> position2d) {
		super(price, shootSpeed, shootPower, shootRange, position2d);
		// TODO Auto-generated constructor stub
		this.shooter = new Heal();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
