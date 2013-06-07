package players.types.aiStrategy;

import java.util.Random;

import players.types.ArtificialIntelligencePlayer;
import towers.Tower;
import towers.towertypes.BombTower;
import towers.towertypes.FreezeTower;
import towers.towertypes.LaserTower;
import towers.towertypes.MedicalTower;
import towers.towertypes.SubmachineGunTower;

public class OffensiveStrategy extends AIStrategy {
	
	/*
	 * L'Offensive Strategy consiste � s'attaquer en priorit� aux ennemis - les r�duire en miette !
	 * Et ensuite seulement s'attaquer aux bases neutres.
	 * Les tours sont construites pour attaquer les ennemis.
	 */
	
	/*
	 * Les tours sont construites pr�s des bases et des tours ennemies.
	 * (Sauf Medical Towers, mais les tours de ce type sont moins utilis�es)
	 * 
	 * Tours pr�f�r�es : LaserTower & SubMachineGunTower
	 * Probabilit� de construction des tours :
	 * BombTower - 20%
	 * FreezeTower - 10%
	 * LaserTower - 30%
	 * MedicalTower - 10%
	 * SubmachineGunTower - 30%
	 * 
	 * Si pas assez d'argent pour construire la tour choisie, choisira une tour plus "c�t�e" et moins ch�re.
	 * Si pas assez d'argent encore, choisira une tour moins "c�t�e" et moins ch�re.
	 */
	
	public OffensiveStrategy(ArtificialIntelligencePlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	private enum TowerProbability implements TowerProbabilityInterface
	{
		pBombTower(BombTower.class, 20),
		pFreezeTower(FreezeTower.class, 10),
		pLaserTower(LaserTower.class, 30),
		pMedicalTower(MedicalTower.class, 10),
		pSubMachineGunTower(SubmachineGunTower.class, 30);
		
		private Class<? extends Tower> towerclass;
		private static int totalCummulatedProbability;
		private int cummulatedProbability;
		
		private TowerProbability (Class<? extends Tower> towerclass, int probability)
		{
			this.towerclass = towerclass;
			this.increaseTotalCummulatedProbability(probability);
			this.cummulatedProbability = this.getTotalCummulatedProbability();
		}

		public Class<? extends Tower> getTowerclass() {
			return towerclass;
		}

		public void increaseTotalCummulatedProbability(int totalCummulatedProbability) {
			TowerProbability.totalCummulatedProbability += totalCummulatedProbability;
		}
		
		public int getTotalCummulatedProbability() {
			return totalCummulatedProbability;
		}

		public static TowerProbability getRandomTowerProbability ()
		{
			/*
			 * G�n�ration d'un nombre al�atoire entre 0 et TotalCummulatedProbability
			 */
			
			Random r = new Random();
			int randomvalue = r.nextInt(TowerProbability.totalCummulatedProbability);
			
			/*
			 * On choisit la towerprobability correspondant � ce nombre al�atoire.
			 */
			
			int lastCummulatedProbability = 0;
			for (TowerProbability towerprobability : TowerProbability.values())
			{
				if (lastCummulatedProbability <= randomvalue && towerprobability.cummulatedProbability >= randomvalue)
					return towerprobability;
				lastCummulatedProbability = towerprobability.cummulatedProbability;
			}
			return null;
		}
	};

	public TowerProbabilityInterface getRandomTowerProbability()
	{
		return TowerProbability.getRandomTowerProbability();
	}

}
