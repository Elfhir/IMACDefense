package players.types.aiStrategy;

public class OffensiveStrategy implements AIStrategy {
	
	/*
	 * L'Offensive Strategy consiste � s'attaquer en priorit� aux ennemis - les r�duire en miette !
	 * Et ensuite seulement s'attaquer aux bases neutres.
	 * Les tours sont construites pour attaquer les ennemis.
	 */

	public OffensiveStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void constructTower() {
		/*
		 * Les tours sont construites pr�s des bases et des tours ennemies.
		 * (Sauf Medical Towers, mais les tours de ce type sont moins utilis�es)
		 * 
		 * Tours pr�f�r�es : LaserTower & SubMachineGunTower
		 * Probabilit� de construction des tours :
		 * BombTower - 25%
		 * FreezeTower - 15%
		 * LaserTower - 30%
		 * MedicalTower - 15%
		 * SubmachineGunTower - 30%
		 * 
		 * Si pas assez d'argent pour construire la tour choisie, choisira une tour plus "c�t�e" et moins ch�re.
		 * Si pas assez d'argent encore, choisira une tour moins "c�t�e" et moins ch�re.
		 */

	}

	@Override
	public void moveAgents() {
		/*
		 * D�placer les agents en priorit� vers les ennemis.
		 * Si la situation est d�favorable cependant (pas assez d'agents),
		 * essayera de s'approprier des bases neutres si possible.
		 */

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
