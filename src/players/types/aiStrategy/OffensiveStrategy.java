package players.types.aiStrategy;

public class OffensiveStrategy implements AIStrategy {
	
	/*
	 * L'Offensive Strategy consiste à s'attaquer en priorité aux ennemis - les réduire en miette !
	 * Et ensuite seulement s'attaquer aux bases neutres.
	 * Les tours sont construites pour attaquer les ennemis.
	 */

	public OffensiveStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void constructTower() {
		/*
		 * Les tours sont construites près des bases et des tours ennemies.
		 * (Sauf Medical Towers, mais les tours de ce type sont moins utilisées)
		 * 
		 * Tours préférées : LaserTower & SubMachineGunTower
		 * Probabilité de construction des tours :
		 * BombTower - 25%
		 * FreezeTower - 15%
		 * LaserTower - 30%
		 * MedicalTower - 15%
		 * SubmachineGunTower - 30%
		 * 
		 * Si pas assez d'argent pour construire la tour choisie, choisira une tour plus "côtée" et moins chère.
		 * Si pas assez d'argent encore, choisira une tour moins "côtée" et moins chère.
		 */

	}

	@Override
	public void moveAgents() {
		/*
		 * Déplacer les agents en priorité vers les ennemis.
		 * Si la situation est défavorable cependant (pas assez d'agents),
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
