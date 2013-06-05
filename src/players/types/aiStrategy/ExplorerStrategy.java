package players.types.aiStrategy;

public class ExplorerStrategy implements AIStrategy {
	
	/*
	 * L'Explorer Strategy consiste � tenter de conqu�rir les bases neutres d'abord
	 * et n'attaquer un ennemi que lorsqu'il s'attaque au joueur.
	 * Les tours sont construites pour prot�ger les bases.
	 */

	public ExplorerStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void constructTower() {
		/*
		 * Les tours sont construites pr�s des bases amies et loin si possible des tours ennemies.
		 * 
		 * Tours pr�f�r�es : FreezeTower et BombTower
		 * Probabilit� de construction des tours :
		 * BombTower - 30%
		 * FreezeTower - 30%
		 * LaserTower - 20%
		 * MedicalTower - 20%
		 * SubmachineGunTower - 20%
		 * 
		 * Si pas assez d'argent pour construire la tour choisie, choisira une tour plus "c�t�e" et moins ch�re.
		 * Si pas assez d'argent encore, choisira une tour moins "c�t�e" et moins ch�re.
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
