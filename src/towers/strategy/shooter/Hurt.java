package towers.strategy.shooter;

import towers.Tower;

public class Hurt implements ShooterInterface {
	
	private int power = 0;

	public Hurt(int power) {
		// TODO Auto-generated constructor stub
		this.power = power;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shoot(Object target) {
		// TODO Auto-generated method stub
		if (target instanceof Tower)
		{
			System.out.println("Hurting ennemie tower !");
			Tower towerTarget = (Tower) target;
			towerTarget.setLife(towerTarget.getLife() - this.power);
			System.out.println("The ennemie tower life is now ");
			System.out.println(towerTarget.getLife());
		}
		else
			System.out.println("Hurting ennemie !");
	}

}
