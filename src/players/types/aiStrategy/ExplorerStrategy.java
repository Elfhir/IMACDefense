package players.types.aiStrategy;

public class ExplorerStrategy implements AIStrategy {
	
	/*
	 * L'Explorer Strategy consiste à tenter de conquérir les bases neutres d'abord
	 * et n'attaquer un ennemi que lorsqu'il s'attaque au joueur.
	 * Les tours sont construites pour protéger les bases.
	 */

	public ExplorerStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void constructTower() {
		/*
		 * Les tours sont construites près des bases amies et loin si possible des tours ennemies.
		 * 
		 * Tours préférées : FreezeTower et BombTower
		 * Probabilité de construction des tours :
		 * BombTower - 30%
		 * FreezeTower - 30%
		 * LaserTower - 20%
		 * MedicalTower - 20%
		 * SubmachineGunTower - 20%
		 * 
		 * Si pas assez d'argent pour construire la tour choisie, choisira une tour plus "côtée" et moins chère.
		 * Si pas assez d'argent encore, choisira une tour moins "côtée" et moins chère.
		 */

	}

	@Override
	public void moveAgents() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
