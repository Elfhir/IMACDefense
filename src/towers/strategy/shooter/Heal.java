package towers.strategy.shooter;

public class Heal implements ShooterInterface {
	
	private int power = 0;

	public Heal(int power) {
		// TODO Auto-generated constructor stub
		this.power = power;
	}

	@Override
	public void shoot(Object target) {
		// TODO Auto-generated method stub
		System.out.println("Healing friend !");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
